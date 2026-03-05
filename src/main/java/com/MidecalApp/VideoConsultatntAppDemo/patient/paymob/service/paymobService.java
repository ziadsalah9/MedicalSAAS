package com.MidecalApp.VideoConsultatntAppDemo.patient.paymob.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class paymobService {

    @Value("${paymob.apiKey}")
    private String apiKey;
    @Value("${paymob.integrationId}")
    private String MobilewalletId;

    private final RestTemplate restTemplate = new RestTemplate();




    // i want to send post request to String url = "https://accept.paymob.com/api/ecommerce/orders"; to register order and get "id"
    //but first should authenticate and git token from /api/auth/token to connect with pay mob and get "token"
    //after getting token and use it and getting id , use token again and id to post String url = "https://accept.paymob.com/api/acceptance/payment_keys"; to get "paymentkey"
    //now i have paymentkey(is token also) i use it to post in String url = "https://accept.paymob.com/api/acceptance/payments/pay"; and  "get url" which front use to user pay

    private String authenticate(){
        System.out.println("-----------------------------------------------------");
        System.out.println("api-key -->" +apiKey);
        System.out.println("integration-Id-->"+MobilewalletId);
        System.out.println("------------------------------------------------------");
        String url = "https://accept.paymob.com/api/auth/tokens";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String,String> map = new HashMap<>();
        map.put("api_key", apiKey);
       // map.put("mobileWalletId",MobilewalletId);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);
        var response = restTemplate.postForEntity(url,request,Map.class);
        return (String) response.getBody().get("token");


    }

    //register order and return its id ;
    private long registerOrder(String token , Double amount){


        String url = "https://accept.paymob.com/api/ecommerce/orders";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String,Object> map = new HashMap<>();

        map.put("auth_token", token);
        map.put( "delivery_needed", "false");
        map.put( "amount_cents", (int)(amount * 100));
        map.put("currency","EGP");
        map.put("items", List.of());
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
        var response = restTemplate.postForEntity(url,request,Map.class);
        return Long.valueOf(response.getBody().get("id").toString());    }


    private String getPaymentKey(String token, Long order_id ,Double amount,Long PatientId ){
        String url = "https://accept.paymob.com/api/acceptance/payment_keys";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String patientid = String.valueOf(PatientId);
        Map<String, String> billingData = new HashMap<>();
        billingData.put("first_name", "Patient");
        billingData.put("last_name", String.valueOf(order_id));
        billingData.put("email", "test@test.com");
        billingData.put("phone_number", "01010101010");
        billingData.put("city", "Cairo");
        billingData.put("country", "EG");
        billingData.put("street", "NA");
        billingData.put("building", "NA");
        billingData.put("floor", "NA");
        billingData.put("apartment", "NA");
        billingData.put("extra_description", String.valueOf(PatientId));

        Map<String,Object> map = Map.of(
                "auth_token", token,
                "amount_cents", (int)(amount * 100),
                "expiration", 3600,
                "order_id", order_id,
                    "billing_data",billingData,
//                "billing_data", Map.of("first_name", "Patient",
//                        "last_name", String.valueOf(order_id),
//                        "email", "test@test.com",
//                        "phone_number", "01010101010",
//                        "city", "Cairo",
//                        "country", "EG",
//                        //
//                        "street", "NA",
//                        "building", "NA",
//                        "floor", "NA",
//                        "apartment", "NA",
//                        "extra_description", patientid// وأيضاً هنا لزيادة التأكيد
//                ),
                "currency", "EGP",
                "integration_id", Integer.parseInt( MobilewalletId)
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(map, headers);
        var response = restTemplate.postForEntity(url, request, Map.class);
        return (String) response.getBody().get("token");
    }

    private String getWalletUrl(String token , String phoneNumber ){
        String url = "https://accept.paymob.com/api/acceptance/payments/pay?token="+token;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "source", Map.of(
                        "identifier", phoneNumber,
                        "subtype", "WALLET"
                ),
                "payment_token", token
        );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        var response = restTemplate.postForEntity(url, request, Map.class);
        System.out.println("Paymob Wallet Response: " + response);
        //return (String) ((Map) response.getBody().get("redirection_url")).get("url");
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("redirect_url")) {
            String redirectUrl = (String) responseBody.get("redirect_url");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                return redirectUrl;
            }
        }
        // لو لسه فيه مشكلة، جرب المفتاح البديل اللي بيظهر في بعض الحالات
        if (responseBody != null && responseBody.containsKey("iframe_redirection_url")) {
            return (String) responseBody.get("iframe_redirection_url");
        }

        throw new RuntimeException("لم يتم العثور على رابط تحويل من Paymob");

    }

    public String getWalletPaymentUrl(Double amount,Long patientId,String phoneNumber ){

        String authenticate = authenticate();
        long id =  registerOrder(authenticate,amount);

        String paymentKey =  getPaymentKey(authenticate,id,amount,patientId);
        String url = getWalletUrl(paymentKey,phoneNumber);
        return url;
    }
}
