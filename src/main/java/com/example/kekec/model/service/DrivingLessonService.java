package com.example.kekec.model.service;

import com.example.kekec.model.enums.LessonType;
import com.example.kekec.model.jpa.Candidate;
import com.example.kekec.model.jpa.DrivingLesson;
import com.example.kekec.model.jpa.Instructor;

import java.time.LocalDateTime;
import java.util.List;

public interface DrivingLessonService {

    DrivingLesson addDrivingLesson(Long candidateId, Long instructorId, String date, Integer type);
    
    DrivingLesson updateDrivingLesson(Long drivingLessonId, LessonType type);
    
    DrivingLesson getById(Long drivingLessonId);
    
    List<DrivingLesson> getAll();

    void removeDrivingLesson(Long drivingLessonId);

}
