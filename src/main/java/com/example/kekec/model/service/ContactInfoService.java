package com.example.kekec.model.service;


import com.example.kekec.model.jpa.ContactInfo;

import java.util.List;

/**
 * Created by Viktor on 10-Apr-17.
 */
public interface ContactInfoService {
    ContactInfo createContactInfo(String firstName, String lastName, String phone);
    ContactInfo updateContactInfo(Long id, String firstName, String lastName, String phone);
    List<ContactInfo> getAll();
    ContactInfo getById(Long contactInfoId);
}
