package com.example.kekec.model.service;

import com.example.kekec.model.jpa.AdditionalSpending;

import java.util.List;

public interface SpendingService {

    AdditionalSpending createSpending(String description, Double price, Boolean isPaid);
    AdditionalSpending paySpending(Long spendingId);
    AdditionalSpending findById(Long spendingId);
    List<AdditionalSpending> findAll();
}
