package com.example.kekec.model.service.impl;

import com.example.kekec.model.jpa.Instructor;
import com.example.kekec.model.persistence.InstructorRepository;
import com.example.kekec.model.service.ContactInfoService;
import com.example.kekec.model.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {


    private InstructorRepository instructorRepository;
    private ContactInfoService contactInfoService;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository, ContactInfoService contactInfoService) {
        this.instructorRepository = instructorRepository;
        this.contactInfoService = contactInfoService;
    }

    @Override
    public Instructor createInstructor(String firstName, String lastName, String phone) {
        Instructor instructor = new Instructor();
        instructor.contactInfo = contactInfoService.createContactInfo(firstName, lastName, phone);
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor updateInstructor(Long instructorId, String firstName, String lastName, String phone) {
        Instructor instructor = getById(instructorId);
        instructor.contactInfo = contactInfoService.updateContactInfo(instructor.contactInfo.id, firstName, lastName, phone);
        return instructorRepository.save(instructor);
    }

    @Override
    public List<Instructor> getAll() {
        return (List<Instructor>) instructorRepository.findAll();
    }

    @Override
    public Instructor getById(Long instructorId) {
        return instructorRepository.findOne(instructorId);
    }
}
