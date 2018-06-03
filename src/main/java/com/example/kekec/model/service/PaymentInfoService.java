package com.example.kekec.model.service;

import com.example.kekec.model.jpa.AdditionalSpending;
import com.example.kekec.model.jpa.PaymentInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentInfoService {

    PaymentInfo createPaymentInfo(Double totalSum, Integer numberOfInstallments, LocalDateTime startingDate);
    PaymentInfo addInstallment(Long paymentInfoId, Long installmentId);
    PaymentInfo addSpending(Long paymentInfoId,Long additionalSpendingId);
    PaymentInfo updatePaymentInfo(Double totalSum, Integer numberOfInstallments, Long paymentInfoId);
    PaymentInfo findById(Long paymentInfoId);
    List<PaymentInfo> findAll();

    Double inDebt(Long paymentInfoId);
}
