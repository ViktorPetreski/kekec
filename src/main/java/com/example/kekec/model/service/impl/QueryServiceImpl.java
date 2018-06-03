package com.example.kekec.model.service.impl;

import com.example.kekec.model.jpa.Candidate;
import com.example.kekec.model.jpa.DrivingLesson;
import com.example.kekec.model.persistence.QueryRepository;
import com.example.kekec.model.persistence.SearchRepository;
import com.example.kekec.model.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QueryServiceImpl implements QueryService {


    private SearchRepository searchRepository;
    private QueryRepository queryRepository;

    @Autowired
    public QueryServiceImpl(SearchRepository searchRepository, QueryRepository queryRepository) {
        this.searchRepository = searchRepository;
        this.queryRepository = queryRepository;
    }

    @Override
    public List<Candidate> searchCandidate(String query) {
        return searchRepository.searchPhrase(
                Candidate.class,
                query,
                "contactInfo.firstName", "contactInfo.lastName", "contactInfo.phone", "ssn");
    }

    @Override
    public List<DrivingLesson> getDrivingLessonsForCandidate(Long candidateId) {
        List<DrivingLesson> drivingLessons = queryRepository.getDrivingLessonsForCandidate(candidateId);
        sortList(drivingLessons);
        return drivingLessons;
    }

    private void sortList(List<DrivingLesson> drivingLessons) {
        drivingLessons.sort(Comparator.comparing((DrivingLesson o) -> o.candidate.contactInfo.firstName + o.candidate.contactInfo.lastName).thenComparing(o -> o.dateTaken));
    }

    @Override
    public List<DrivingLesson> getDrivingLessonsForInstructor(Long instructorId) {
        List<DrivingLesson> drivingLessons = queryRepository.getDrivingLessonsForInstructor(instructorId);
        sortList(drivingLessons);
        return drivingLessons;
    }

    @Override
    public Map<String, List<DrivingLesson>> getDrivingLessonsForInstructorForMonth(Long instructorId, String date) {
        List<DrivingLesson> drivingLessons = getDrivingLessonsForInstructor(instructorId);

        YearMonth pickedDate = parseDate(date);


        drivingLessons = drivingLessons.stream().filter(lesson -> (lesson.dateTaken.getYear() == pickedDate.getYear() && lesson.dateTaken.getMonth().compareTo(pickedDate.getMonth()) == 0)).collect(Collectors.toList());

        Set<String> categories = new TreeSet<>();
        for(DrivingLesson drivingLesson : drivingLessons){
            categories.add(drivingLesson.candidate.drivingCategory);
        }

        Map<String, List<DrivingLesson>> sortedLessons = new HashMap<>();

        for(String s : categories){
            List<DrivingLesson> tmp =  drivingLessons.stream().filter(lesson -> (lesson.candidate.drivingCategory.equalsIgnoreCase(s))).collect(Collectors.toList());
            sortedLessons.put(s, tmp);
        }

        return sortedLessons;
    }

    @Override
    public List<DrivingLesson> getDrivingLessonsForInstructorForCategory(Long instructorId, String category) {
        List<DrivingLesson> drivingLessons = getDrivingLessonsForInstructor(instructorId);
        return drivingLessons.stream().filter(lesson -> (lesson.candidate.drivingCategory.equalsIgnoreCase(category))).collect(Collectors.toList());
    }

    @Override
    public YearMonth parseDate(String date){
        if(!date.isEmpty()){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
           return YearMonth.parse(date, formatter);
        }
        else{
            return YearMonth.now();
        }
    }

}
