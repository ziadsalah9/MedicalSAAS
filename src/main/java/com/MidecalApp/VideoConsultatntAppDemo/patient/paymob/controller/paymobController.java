package com.MidecalApp.VideoConsultatntAppDemo.patient.paymob.controller;

import com.MidecalApp.VideoConsultatntAppDemo.patient.paymob.dto.paymentReq;
import com.MidecalApp.VideoConsultatntAppDemo.patient.paymob.service.paymobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class paymobController {

    private final paymobService paymobService;

//    @PostMapping("/charge-wallet")
//
//    public ResponseEntity<?> chargeWallet(@RequestParam Double amount, @RequestParam Long patientId , @RequestParam String phoneNumber){
//
//        String url = paymobService.getWalletPaymentUrl(amount,patientId,phoneNumber);
//
//        return ResponseEntity.ok(Map.of("iframe_url", url));
//
//    }
@PostMapping("/charge-wallet")

public ResponseEntity<?> chargeWallet(@RequestBody paymentReq paymentRequest){

    String url = paymobService.getWalletPaymentUrl(paymentRequest.amount(),paymentRequest.patientId(),paymentRequest.phoneNumber());

    return ResponseEntity.ok(Map.of("iframe_url", url));

}
}
