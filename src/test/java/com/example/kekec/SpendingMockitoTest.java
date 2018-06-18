package com.example.kekec;

import com.example.kekec.model.jpa.AdditionalSpending;
import com.example.kekec.model.service.ContactInfoService;
import com.example.kekec.model.service.SpendingService;
import com.example.kekec.model.service.impl.SpendingServiceMockImpl;
import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.concurrent.ArrayBlockingQueue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpendingMockitoTest {

    SpendingService spendingService;
    SpendingServiceMockImpl spendingServiceMockImpl;
    AdditionalSpending additionalSpending;
    AdditionalSpending paidSpending;

    @Before
    public void setUp(){
        this.spendingServiceMockImpl = new SpendingServiceMockImpl();
        spendingService = mock(SpendingService.class);
        spendingServiceMockImpl.setSpendingService(spendingService);

        additionalSpending = new AdditionalSpending();
        additionalSpending.isPaid = false;
        additionalSpending.price = 1000.0;
        additionalSpending.description = "Dopolnitelen cas";

        paidSpending = additionalSpending;
        paidSpending.isPaid = true;

    }

    @Test
    public void createSpendingTest(){
        when(spendingService.createSpending("Dopolnitelen cas",1000.0,false))
            .thenReturn(additionalSpending);

        assertEquals(spendingServiceMockImpl.createSpending("Dopolnitelen cas",1000.0,false),
                additionalSpending);
    }

    @Test
    public void paySpendingTest(){
        when(spendingService.paySpending(155L)).thenReturn(paidSpending);

        assertEquals(spendingServiceMockImpl.paySpending(155L),paidSpending);

        additionalSpending = paidSpending;
    }

    @Test
    public void findByIdTest(){
        when(spendingService.findById(115L)).thenReturn(additionalSpending);
        assertEquals(spendingServiceMockImpl.findById(115L),additionalSpending);
    }

    @Test
    public void findAllTest(){
        when(spendingService.findAll()).thenReturn(new ArrayList<AdditionalSpending>
                (Arrays.asList(new AdditionalSpending[]{additionalSpending})));

        assertEquals(spendingServiceMockImpl.findAll(),new ArrayList<AdditionalSpending>
                (Arrays.asList(new AdditionalSpending[]{additionalSpending})));
    }

}
