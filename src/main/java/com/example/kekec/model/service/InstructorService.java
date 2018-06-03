package com.example.kekec.model.service;

import com.example.kekec.model.jpa.ContactInfo;
import com.example.kekec.model.jpa.Instructor;

import java.util.List;

public interface InstructorService {
    Instructor createInstructor(String firstName, String lastName, String phone);

    Instructor updateInstructor(Long instructorId, String firstName, String lastName, String phone);
    
    List<Instructor> getAll();
    
    Instructor getById(Long instructorId);
}
