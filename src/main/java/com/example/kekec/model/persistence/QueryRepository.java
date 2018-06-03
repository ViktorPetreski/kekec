package com.example.kekec.model.persistence;

import com.example.kekec.model.jpa.Candidate;
import com.example.kekec.model.jpa.DrivingLesson;

import java.util.List;

public interface QueryRepository {

    List<DrivingLesson> getDrivingLessonsForCandidate(Long candidateId);

    List<DrivingLesson> getDrivingLessonsForInstructor(Long instructorId);


}
