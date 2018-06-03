package com.example.kekec.model.service.impl;

import com.example.kekec.model.jpa.AdditionalSpending;
import com.example.kekec.model.persistence.AdditionalSpendingRepository;
import com.example.kekec.model.service.SpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SpendingServiceImpl implements SpendingService {

    private final AdditionalSpendingRepository additionalSpendingRepository;

    @Autowired
    public SpendingServiceImpl(AdditionalSpendingRepository additionalSpendingRepository) {
        this.additionalSpendingRepository = additionalSpendingRepository;
    }

    @Override
    public AdditionalSpending createSpending(String description, Double price, Boolean isPaid) {
        AdditionalSpending additionalSpending = new AdditionalSpending();

        additionalSpending.description = description;
        additionalSpending.price = price;
        additionalSpending.isPaid = isPaid;

        return additionalSpendingRepository.save(additionalSpending);
    }

    @Override
    public AdditionalSpending paySpending(Long spendingId) {
        AdditionalSpending additionalSpending = findById(spendingId);
        additionalSpending.isPaid = true;
        additionalSpending.datePaid = LocalDateTime.now();
        return additionalSpendingRepository.save(additionalSpending);
    }

    @Override
    public AdditionalSpending findById(Long spendingId) {
        return additionalSpendingRepository.findOne(spendingId);
    }

    @Override
    public List<AdditionalSpending> findAll() {
        return (List<AdditionalSpending>) additionalSpendingRepository.findAll();
    }

}
