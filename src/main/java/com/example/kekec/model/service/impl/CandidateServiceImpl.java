package com.example.kekec.model.service.impl;

import com.example.kekec.model.jpa.*;
import com.example.kekec.model.persistence.*;
import com.example.kekec.model.service.CandidateService;
import com.example.kekec.model.service.DrivingLessonService;
import com.example.kekec.model.service.PaymentInfoService;
import com.example.kekec.model.service.QueryService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final AdditionalSpendingRepository additionalSpendingRepository;
    private final PaymentInfoService paymentInfoService;
    private final QueryService queryService;
    private final DrivingLessonService drivingLessonService;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository, AdditionalSpendingRepository additionalSpendingRepository,
                                PaymentInfoService paymentInfoService, QueryService queryService, DrivingLessonService drivingLessonService) {
        this.candidateRepository = candidateRepository;
        this.additionalSpendingRepository = additionalSpendingRepository;
        this.paymentInfoService = paymentInfoService;
        this.queryService = queryService;
        this.drivingLessonService = drivingLessonService;
    }

    @Override
    public Candidate createCandidate(ContactInfo contactInfo, PaymentInfo paymentInfo, String ssn, String drivingCategory, Integer numberOfLessons) {
        Candidate candidate = new Candidate();
        candidate.contactInfo = contactInfo;
        candidate.paymentInfo = paymentInfo;
        candidate.inDebt = candidate.paymentInfo.totalSum;
        candidate.ssn = ssn;
        candidate.isPassed = false;
        candidate.drivingCategory = drivingCategory;
        candidate.numberOfLessons = numberOfLessons;
        return candidateRepository.save(candidate);
    }

    @Override
    public List<Candidate> getAll() {
        return (List<Candidate>) candidateRepository.findAll();
    }

    @Override
    public Candidate getById(Long candidateId) {
        return candidateRepository.findOne(candidateId);
    }

    @Override
    public List<Candidate> getCandidatesNotPaid() {
        LocalDateTime now = LocalDateTime.now().minusDays(1);
        List<Candidate> candidates = getAll();
        List<Candidate> lateCandidates = new ArrayList<>();
        for (Candidate c : candidates) {
            if (!c.paymentInfo.installments.stream().filter(installment -> installment.dueDate.isBefore(now) && !installment.isPaid).collect(Collectors.toList()).isEmpty()
                    && c.inDebt != 0) {
                lateCandidates.add(c);
            }

        }
        return lateCandidates;
    }

    @Override
    public Candidate updateCandidate(Long candidateId, PaymentInfo paymentInfo, ContactInfo contactInfo, String ssn, Integer numberOfLessons, String drivingCategory) {
        Candidate candidate = candidateRepository.findOne(candidateId);
        candidate.ssn = ssn;
        candidate.paymentInfo = paymentInfo;
        candidate.contactInfo = contactInfo;
        candidate.inDebt = paymentInfo.totalSum;
        candidate.drivingCategory = drivingCategory;
        candidate.numberOfLessons = numberOfLessons;
        return candidateRepository.save(candidate);
    }

    @Override
    public void removeCandidate(Long candidateId) {
        /*Candidate candidate = candidateRepository.findOne(candidateId);
        PaymentInfo paymentInfo = candidate.paymentInfo;


        for(Installment i : paymentInfo.installments){
            installmentRepository.delete(i.id);
        }

        for(AdditionalSpending ad : paymentInfo.additionalSpendings){
            additionalSpendingRepository.delete(ad.id);
        }


        paymentInfoRepository.delete(paymentInfo.id);
        contactInfoRepository.delete(candidate.contactInfo.id);*/
        List<DrivingLesson> drivingLessons = queryService.getDrivingLessonsForCandidate(candidateId);
        for(DrivingLesson d : drivingLessons){
            drivingLessonService.removeDrivingLesson(d.id);
        }
        candidateRepository.delete(candidateId);

    }

    @Override
    public void checkDebt(Long candidateId) {
        Candidate candidate = getById(candidateId);
        try {
            candidate.inDebt = paymentInfoService.inDebt(candidate.paymentInfo.id);
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        candidateRepository.save(candidate);
    }

    @Override
    public void paySpending(Long candidateId, Long spendingId) {
        Candidate candidate = getById(candidateId);
        AdditionalSpending additionalSpending = additionalSpendingRepository.findOne(spendingId);
        candidate.inDebt -= additionalSpending.price;
        candidateRepository.save(candidate);
    }

    @Override
    public void markAsLegalDriver(Long candidateId) {
        Candidate candidate = getById(candidateId);
        candidate.isPassed = !candidate.isPassed;
        candidateRepository.save(candidate);
    }
}
