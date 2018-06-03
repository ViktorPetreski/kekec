package com.example.kekec.model.service.impl;

import com.example.kekec.model.jpa.Installment;
import com.example.kekec.model.persistence.InstallmentRepository;
import com.example.kekec.model.service.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InstallmentServiceImpl implements InstallmentService{

    private final InstallmentRepository installmentRepository;

    @Autowired
    public InstallmentServiceImpl(InstallmentRepository installmentRepository){
        this.installmentRepository = installmentRepository;
    }

    @Override
    public Installment createInstallment(Double price, Boolean isPaid, LocalDateTime dueDate, LocalDateTime startingDate) {
        Installment installment = new Installment();
        installment.dueDate = dueDate;
        installment.isPaid = isPaid;
        installment.price = price;
        installment.startingDate = startingDate;

        return installmentRepository.save(installment);
    }

    @Override
    public Installment payInstallment(Long installmentId) {
        Installment installment = installmentRepository.findOne(installmentId);
        installment.isPaid = true;
        installment.datePaid = LocalDateTime.now();
        return installmentRepository.save(installment);
    }

    @Override
    public Installment updateInstallment(Long installmentId, Double price, LocalDateTime dueDate, LocalDateTime startDate) {
        Installment installment = installmentRepository.findOne(installmentId);
        installment.price = price;
        installment.dueDate = dueDate;
        installment.startingDate = startDate;
        return installmentRepository.save(installment);
    }

    @Override
    public Installment getById(Long id) {
        return installmentRepository.findOne(id);
    }


}
