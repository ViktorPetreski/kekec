package com.example.kekec.model.service;

import com.example.kekec.model.jpa.Candidate;
import com.example.kekec.model.jpa.DrivingLesson;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

public interface QueryService {
    List<Candidate> searchCandidate(String query);

    List<DrivingLesson> getDrivingLessonsForCandidate(Long candidateId);

    List<DrivingLesson> getDrivingLessonsForInstructor(Long instructorId);

    Map<String, List<DrivingLesson>> getDrivingLessonsForInstructorForMonth(Long instructorId, String date);

    List<DrivingLesson> getDrivingLessonsForInstructorForCategory(Long instructorId, String category);

    YearMonth parseDate(String date);

}
