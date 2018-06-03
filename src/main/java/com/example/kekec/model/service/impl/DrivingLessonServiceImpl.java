package com.example.kekec.model.service.impl;

import com.example.kekec.model.enums.LessonType;
import com.example.kekec.model.jpa.Candidate;
import com.example.kekec.model.jpa.DrivingLesson;
import com.example.kekec.model.persistence.CandidateRepository;
import com.example.kekec.model.persistence.DrivingLessonRepository;
import com.example.kekec.model.persistence.InstructorRepository;
import com.example.kekec.model.service.DrivingLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DrivingLessonServiceImpl implements DrivingLessonService {

    private DrivingLessonRepository drivingLessonRepository;
    private InstructorRepository instructorRepository;
    private CandidateRepository candidateRepository;


    @Autowired
    public DrivingLessonServiceImpl(DrivingLessonRepository drivingLessonRepository, InstructorRepository instructorRepository, CandidateRepository candidateRepository) {
        this.drivingLessonRepository = drivingLessonRepository;
        this.instructorRepository = instructorRepository;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public DrivingLesson addDrivingLesson(Long candidateId, Long instructorId, String date, Integer type) {
        DrivingLesson drivingLesson = new DrivingLesson();
        drivingLesson.instructor = instructorRepository.findOne(instructorId);
        drivingLesson.candidate = candidateRepository.findOne(candidateId);
        drivingLesson.dateTaken = stringToDate(date);
        drivingLesson.type = parseLessonType(type, drivingLesson.candidate);
        return drivingLessonRepository.save(drivingLesson);
    }

    @Override
    public DrivingLesson updateDrivingLesson(Long drivingLessonId, LessonType type) {
        DrivingLesson drivingLesson = getById(drivingLessonId);
        drivingLesson.type = type;
        return drivingLessonRepository.save(drivingLesson);
    }

    @Override
    public DrivingLesson getById(Long drivingLessonId) {
        return drivingLessonRepository.findOne(drivingLessonId);
    }

    @Override
    public List<DrivingLesson> getAll() {
        return (List<DrivingLesson>) drivingLessonRepository.findAll();
    }

    @Override
    public void removeDrivingLesson(Long drivingLessonId) {
        drivingLessonRepository.delete(drivingLessonId);
    }

    private LocalDateTime stringToDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy HH:mm");
        return LocalDateTime.parse(date, formatter);
    }

    private LessonType parseLessonType(Integer type, Candidate candidate) {
        switch (type) {
            case 1: {
                candidate.numberOfLessons -= 2;
                candidateRepository.save(candidate);
                return LessonType.Два_часа;
            }
            case 2: {
                candidate.numberOfLessons -= 1;
                candidateRepository.save(candidate);
                return LessonType.Еден_час;
            }
            case 3: return LessonType.Полага_гратска;
            case 4: return LessonType.Полага_полигон;
            default:
                return null;
        }
    }
}
