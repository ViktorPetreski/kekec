package com.example.kekec.model.service.impl;

import com.example.kekec.model.jpa.ContactInfo;
import com.example.kekec.model.persistence.ContactInfoRepository;
import com.example.kekec.model.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Viktor on 10-Apr-17.
 */
@Service
public class ContactInfoServiceImpl implements ContactInfoService {
    private final ContactInfoRepository contactInfoRepository;

    @Autowired
    public ContactInfoServiceImpl(ContactInfoRepository contactInfoRepository)
    {
        this.contactInfoRepository= contactInfoRepository;
    }

    @Override
    public ContactInfo createContactInfo(String firstName, String lastName, String phone) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.firstName = firstName;
        contactInfo.lastName = lastName;
        contactInfo.phone = phone;
        return contactInfoRepository.save(contactInfo);
    }

    @Override
    public ContactInfo updateContactInfo(Long id, String firstName, String lastName, String phone) {
        ContactInfo contactInfo = contactInfoRepository.findOne(id);
        contactInfo.firstName = firstName;
        contactInfo.lastName = lastName;
        contactInfo.phone = phone;
        return contactInfoRepository.save(contactInfo);
    }


    @Override
    public List<ContactInfo> getAll() {
        return (List<ContactInfo>) contactInfoRepository.findAll();
    }

    @Override
    public ContactInfo getById(Long contactInfoId) {
        return contactInfoRepository.findOne(contactInfoId);
    }
}
