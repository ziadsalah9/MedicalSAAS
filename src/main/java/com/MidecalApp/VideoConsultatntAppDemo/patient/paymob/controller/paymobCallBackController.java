package com.MidecalApp.VideoConsultatntAppDemo.patient.paymob.controller;

import com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.service.walletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments/callback")
@RequiredArgsConstructor
public class paymobCallBackController {

    private final walletService  walletService;
//    @PostMapping
//    public ResponseEntity<Void> handlePaymobCallback(@RequestBody Map<String, Object> payload) {
//
//
//try {
//
//
//    System.out.println(" Webhook received from Paymob!");
//    Map<String, Object> obj = (Map<String, Object>) payload.get("obj");
//    boolean success = (boolean) obj.get("success");
//
//    if (success) {
//        System.out.println("Success!");
//        String amountCents = obj.get("amount_cents").toString();
//        Double realAmount = Double.parseDouble(amountCents) / 100;
//        Map<String, Object> order = (Map<String, Object>) obj.get("order");
//        String paymobOrderId = order.get("id").toString();
//
//        Map<String, Object> paymentKeyClaims = (Map<String, Object>) obj.get("payment_key_claims");
//        Map<String, Object> billingData = (Map<String, Object>) paymentKeyClaims.get("billing_data");
//        String patientIdStr = billingData.get("extra_description").toString();
//        Long patientId = Long.parseLong(patientIdStr);
//        System.out.println("✅ عملية ناجحة! مبلغ: " + realAmount + " لطلب رقم: " + paymobOrderId);
//
//
//        //deposit for patient
//        // walletService.depositWallet(paymobOrderId, realAmount);
//
//        walletService.Deposit(patientId, realAmount);
//    } else {
//        System.out.println(" العملية فشلت أو المريض كنسل.");
//    }
//
//
//   return ResponseEntity.ok().build();
//}catch (Exception e) {
//    System.err.println("⚠️ Error processing callback: " + e.getMessage());
//    return ResponseEntity.ok().build();
//}
//
//
//
//
//    }

    @PostMapping
    public ResponseEntity<Void> handlePaymobCallback(@RequestBody Map<String, Object> payload) {
        System.out.println("📬 Webhook Received!");
        try {
            Map<String, Object> obj = (Map<String, Object>) payload.get("obj");
            if (obj == null) return ResponseEntity.ok().build();

            boolean success = Boolean.parseBoolean(obj.get("success").toString());
            System.out.println("💳 Success Status: " + success);

            if (success) {
                Double amount = Double.parseDouble(obj.get("amount_cents").toString()) / 100;
                Map<String, Object> paymentKeyClaims = (Map<String, Object>) obj.get("payment_key_claims");
                Map<String, Object> billingData = (Map<String, Object>) paymentKeyClaims.get("billing_data");

                // تأكد إن الـ extra_description فيه قيمة
//                String extra = (String) billingData.get("extra_description");
                String extra = String.valueOf(billingData.get("extra_description"));
                System.out.println("----->"+extra);
                System.out.println("🆔 Target Patient ID: " + extra);

                if (extra != null && !extra.isBlank()) {
                    Long patientId = Long.parseLong(extra);
                    // بننادي الـ Service هنا
                    walletService.Deposit(patientId, amount);
                    System.out.println("💰 Deposit Done in DB!");
                }
            }

            // الرد ده لازم يتبعت لـ Paymob فوراً
            return ResponseEntity.ok().build();

        } catch (Exception e) {
            System.err.println("⚠️ Callback Error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok().build();
        }
    }





    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String showSuccessPageToUser() {
        System.out.println("🌐 [Redirect] المريض رجع للمتصفح الآن.");

        return """
                <html>
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>نجاح الدفع</title>
                    <style>
                        body { font-family: sans-serif; display: flex; align-items: center; justify-content: center; height: 100vh; margin: 0; background: #f4f7f6; }
                        .card { background: white; padding: 40px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); text-align: center; max-width: 400px; }
                        h1 { color: #2ecc71; margin-bottom: 10px; }
                        p { color: #666; font-size: 16px; line-height: 1.5; }
                        .check-icon { font-size: 50px; color: #2ecc71; margin-bottom: 20px; }
                    </style>
                </head>
                <body>
                    <div class="card">
                        <div class="check-icon">✔</div>
                        <h1>تمت العملية بنجاح!</h1>
                        <p>تم شحن رصيد محفظتك بنجاح. يمكنك الآن العودة للتطبيق ومتابعة استشاراتك الطبية.</p>
                        <p style="font-size: 12px; color: #aaa; margin-top: 20px;">يمكنك إغلاق هذه الصفحة الآن.</p>
                    </div>
                </body>
                </html>
                """;
    }
}



