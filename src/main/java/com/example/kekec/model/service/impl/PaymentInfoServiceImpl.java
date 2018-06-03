package com.example.kekec.model.service.impl;

import com.example.kekec.model.jpa.AdditionalSpending;
import com.example.kekec.model.jpa.Installment;
import com.example.kekec.model.jpa.PaymentInfo;
import com.example.kekec.model.persistence.AdditionalSpendingRepository;
import com.example.kekec.model.persistence.InstallmentRepository;
import com.example.kekec.model.persistence.PaymentInfoRepository;
import com.example.kekec.model.service.InstallmentService;
import com.example.kekec.model.service.PaymentInfoService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    private final PaymentInfoRepository paymentInfoRepository;
    private final InstallmentRepository installmentRepository;
    private final AdditionalSpendingRepository additionalSpendingRepository;
    private final InstallmentService installmentService;


    @Autowired
    public PaymentInfoServiceImpl(PaymentInfoRepository paymentInfoRepository, InstallmentRepository installmentRepository,
                                  AdditionalSpendingRepository additionalSpendingRepository, InstallmentService installmentService) {
        this.additionalSpendingRepository = additionalSpendingRepository;
        this.installmentRepository = installmentRepository;
        this.paymentInfoRepository = paymentInfoRepository;
        this.installmentService = installmentService;
    }

    @Override
    public PaymentInfo createPaymentInfo(Double totalSum, Integer numberOfInstallments, LocalDateTime startingDate) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.installments = new ArrayList<>();
        paymentInfo.numberOfInstallments = numberOfInstallments;
        paymentInfo.totalSum = totalSum;
        paymentInfo.additionalSpendings = new ArrayList<>();
        paymentInfo = paymentInfoRepository.save(paymentInfo);
        createInstallments(paymentInfo, paymentInfo.totalSum, startingDate);
        return paymentInfo;
    }

    @Override
    public PaymentInfo addInstallment(Long paymentInfoId, Long installmentId) {
        PaymentInfo paymentInfo = findById(paymentInfoId);
        Installment installment = installmentRepository.findOne(installmentId);
        paymentInfo.installments.add(installment);
        return paymentInfoRepository.save(paymentInfo);
    }

    @Override
    public PaymentInfo addSpending(Long paymentInfoId, Long additionalSpendingId) {
        PaymentInfo paymentInfo = findById(paymentInfoId);
        AdditionalSpending additionalSpending = additionalSpendingRepository.findOne(additionalSpendingId);
        paymentInfo.additionalSpendings.add(additionalSpending);
        return paymentInfoRepository.save(paymentInfo);
    }

    @Override
    public PaymentInfo updatePaymentInfo(Double totalSum, Integer numberOfInstallments, Long paymentInfoId) {
        PaymentInfo paymentInfo = findById(paymentInfoId);
        Double oldTotalSum = paymentInfo.totalSum;
        Integer oldNumberOfInstallments = paymentInfo.numberOfInstallments;

        if(!oldNumberOfInstallments.equals(numberOfInstallments) || !oldTotalSum.equals(totalSum)) {
            /*Double paidSum = 0d;
            double paidInstallments = 0;

            for (Installment i : paymentInfo.installments) {
                if (i.isPaid) {
                    paidSum += i.price;
                    paidInstallments++;
                }
            }

            double newTotalSum = totalSum - paidSum;
            double newNoOfInstallments = numberOfInstallments - paidInstallments;
            double difference = numberOfInstallments - oldNumberOfInstallments;

            if (difference > 0) {
                Installment lastInstallment = paymentInfo.installments.get(paymentInfo.installments.size() - 1);

                for (int i = 0; i < numberOfInstallments; i++) {
                    if (i < oldNumberOfInstallments) {
                        Installment installment = paymentInfo.installments.get(i);
                        if (!installment.isPaid) {
                            installmentService.updateInstallment(installment.id, newTotalSum / newNoOfInstallments, installment.dueDate, installment.startingDate);
                        }
                    } else {
                        Installment newInstallment = installmentService.createInstallment(newTotalSum / newNoOfInstallments, false, lastInstallment.dueDate.plusMonths(i), LocalDateTime.now());
                        addInstallment(paymentInfoId, newInstallment.id);
                    }
                }
            }*/
            double difference = numberOfInstallments - oldNumberOfInstallments;
            Installment lastInstallment = paymentInfo.installments.get(paymentInfo.installments.size() - 1);

            for(int i = 0; i < difference; i++){
                Installment newInstallment = installmentService.createInstallment(0d, false, lastInstallment.dueDate.plusMonths(i+1), LocalDateTime.now());
                addInstallment(paymentInfoId, newInstallment.id);
            }
            paymentInfo.numberOfInstallments = numberOfInstallments;
            paymentInfo.totalSum = totalSum;

        }

        return paymentInfoRepository.save(paymentInfo);
    }

    @Override
    public PaymentInfo findById(Long paymentInfoId) {
        return paymentInfoRepository.findOne(paymentInfoId);
    }

    @Override
    public List<PaymentInfo> findAll() {
        return (List<PaymentInfo>) paymentInfoRepository.findAll();
    }

    @Override
    public Double inDebt(Long paymentInfoId) {
        Double totalDebt = 0d;
        PaymentInfo paymentInfo = findById(paymentInfoId);

        totalDebt += paymentInfo.installments.stream().filter(installment -> installment.isPaid).mapToDouble(i -> i.price).sum();
        //totalDebt += paymentInfo.additionalSpendings.stream().filter(additionalSpending -> additionalSpending.isPaid).mapToDouble(as -> as.price).sum();
        totalDebt -= paymentInfo.additionalSpendings.stream().filter(additionalSpending -> !additionalSpending.isPaid).mapToDouble(as -> as.price).sum();

        Double endResult = paymentInfo.totalSum - totalDebt;

        if(endResult < 0) throw new NullPointerException("Cannot have negative debt");

        return endResult;
    }


    private void createInstallments(PaymentInfo paymentInfo, Double totalSum, LocalDateTime startingDate) {
        for (int i = 0; i < paymentInfo.numberOfInstallments; i++) {
            LocalDateTime dueDate = startingDate.plusMonths(i);
            Installment installment = installmentService.createInstallment(0d, false, dueDate, startingDate);
            addInstallment(paymentInfo.id, installment.id);
        }
    }
}
