package com.example.kekec.model.service.impl;

import com.example.kekec.model.jpa.AdditionalSpending;
import com.example.kekec.model.jpa.ContactInfo;
import com.example.kekec.model.service.ContactInfoService;
import com.example.kekec.model.service.SpendingService;

import java.util.List;

public class SpendingServiceMockImpl {
    private SpendingService spendingService;

    public void setSpendingService(SpendingService spendingService){
        this.spendingService = spendingService;
    }

    public AdditionalSpending createSpending(String description, Double price, Boolean isPaid){
        return this.spendingService.createSpending(description,price,isPaid);
    }

    public AdditionalSpending paySpending(Long spendingId){
        return this.spendingService.paySpending(spendingId);
    }

    public AdditionalSpending findById(Long spendingId){
        return this.spendingService.findById(spendingId);
    }

    public List<AdditionalSpending> findAll(){
        return this.spendingService.findAll();
    }

}
