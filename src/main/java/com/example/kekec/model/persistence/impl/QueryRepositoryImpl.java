package com.example.kekec.model.persistence.impl;

import com.example.kekec.model.jpa.Candidate;
import com.example.kekec.model.jpa.DrivingLesson;
import com.example.kekec.model.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class QueryRepositoryImpl implements QueryRepository {
    private DrivingLessonRepository drivingLessonRepository;
    private InstructorRepository instructorRepository;
    private ContactInfoRepository contactInfoRepository;


    @Autowired
    public QueryRepositoryImpl(DrivingLessonRepository drivingLessonRepository,
                               InstructorRepository instructorRepository, ContactInfoRepository contactInfoRepository) {
        this.drivingLessonRepository = drivingLessonRepository;
        this.instructorRepository = instructorRepository;
        this.contactInfoRepository = contactInfoRepository;
    }

    @Override
    public List<DrivingLesson> getDrivingLessonsForCandidate(Long candidateId) {
        return drivingLessonRepository.findAll((Root<DrivingLesson> drivingLesson, CriteriaQuery<?> cq,CriteriaBuilder cb) -> {
                return cb.equal(drivingLesson.join("candidate").get("id"), candidateId);
        });
    }

    @Override
    public List<DrivingLesson> getDrivingLessonsForInstructor(Long instructorId) {
        return drivingLessonRepository.findAll((Root<DrivingLesson> drivingLesson, CriteriaQuery<?> cq,CriteriaBuilder cb) -> {
            return cb.equal(drivingLesson.join("instructor").get("id"), instructorId);
        });
    }
}
