package com.example.kekec.model.service.impl;

import com.example.kekec.model.jpa.ContactInfo;
import com.example.kekec.model.service.ContactInfoService;

import java.util.List;

public class ContactInfoMockImpl {
    private ContactInfoService contactInfoService;

    public void setContactInfoService(ContactInfoService contactInfoService){
        this.contactInfoService = contactInfoService;
    }

    public ContactInfo createContactInfo(String firstName, String lastName, String phone){
        return contactInfoService.createContactInfo(firstName,lastName,phone);
    }

    public ContactInfo updateContactInfo(Long id, String firstName, String lastName, String phone){
        return contactInfoService.updateContactInfo(id,firstName,lastName,phone);
    }

    public List<ContactInfo> getAll(){
        return contactInfoService.getAll();
    }

    public ContactInfo getById(Long contactInfoId){
        return contactInfoService.getById(contactInfoId);
    }
}
