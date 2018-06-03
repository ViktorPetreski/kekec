package com.example.kekec.model.service;

import com.example.kekec.model.jpa.Installment;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

public interface InstallmentService {

    Installment createInstallment(Double price, Boolean isPaid, LocalDateTime dueDate, LocalDateTime startingDate);
    Installment payInstallment(Long installmentId);
    Installment updateInstallment(Long installmentId, Double price, LocalDateTime dueDate, LocalDateTime startDate);

    Installment getById(Long id);
}
