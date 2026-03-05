package com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.controller;

import com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.WalletTransaction;
import com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.repository.walletTransactionRepository;
import com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.service.walletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {


    private final walletService Service;
    @GetMapping("/{id}")
    public WalletTransaction getWalletTransaction(@RequestParam Long id){
        WalletTransaction walletTransaction = Service.getWalletTransactionById(id);
        return walletTransaction;
    }
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(
            @RequestParam Long patientId,
            @RequestParam Double amount) {

        Service.Deposit(patientId, amount);
        return ResponseEntity.ok("Wallet charged successfully");
    }
}
