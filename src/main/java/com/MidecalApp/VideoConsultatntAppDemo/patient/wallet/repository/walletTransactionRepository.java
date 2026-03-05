package com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.repository;

import com.MidecalApp.VideoConsultatntAppDemo.patient.wallet.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface walletTransactionRepository extends JpaRepository<WalletTransaction,Long> {
}
