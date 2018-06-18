package com.example.kekec;

import com.example.kekec.model.jpa.ContactInfo;
import com.example.kekec.model.service.ContactInfoService;
import com.example.kekec.model.service.impl.ContactInfoMockImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ContactInfoMockitoTest {

    ContactInfoMockImpl contactInfoMockImpl;
    ContactInfoService contactInfoService;
    ContactInfo contactInfo;
    ContactInfo updatedContactInfo;

    @Before
    public void setUp() {
        contactInfoMockImpl = new ContactInfoMockImpl();
        contactInfoService = mock(ContactInfoService.class);
        contactInfoMockImpl.setContactInfoService(contactInfoService);

        contactInfo = new ContactInfo();
        contactInfo.firstName = "Ana";
        contactInfo.lastName = "Trajkovska";
        contactInfo.phone = "078123123";

        updatedContactInfo = new ContactInfo();
        updatedContactInfo.firstName = "Ana";
        updatedContactInfo.lastName = "Trajkovska";
        updatedContactInfo.phone = "078111222";
    }

    @Test
    public void createContactInfoTest(){
        when(contactInfoService.createContactInfo("Ana","Trajkovska","078123123"))
                .thenReturn(contactInfo);

        assertEquals(contactInfoMockImpl.
                        createContactInfo("Ana","Trajkovska","078123123"),
                contactInfo);

    }

    @Test
    public void updateContactInfoTest(){
        when(contactInfoService.updateContactInfo(112L,"Ana","Trajkovska","078111222"))
                .thenReturn(updatedContactInfo);

        assertEquals(contactInfoMockImpl.
                updateContactInfo(112L,"Ana","Trajkovska","078111222"),
                updatedContactInfo);

        contactInfo = updatedContactInfo;
    }

    @Test
    public void getAllTest(){
        when(contactInfoService.getAll()).thenReturn(new ArrayList<ContactInfo>(Arrays.asList(contactInfo)));
        assertEquals(contactInfoMockImpl.getAll(),new ArrayList<ContactInfo>(Arrays.asList(contactInfo)));
    }

    @Test
    public void getByIdTest(){
        when(contactInfoService.getById(112L)).thenReturn(contactInfo);
        assertEquals(contactInfoMockImpl.getById(112L),contactInfo);
    }



}
