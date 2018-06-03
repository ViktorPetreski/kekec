package com.example.kekec.model.service;

import com.example.kekec.model.jpa.Candidate;
import com.example.kekec.model.jpa.ContactInfo;
import com.example.kekec.model.jpa.PaymentInfo;
import com.example.kekec.model.jpa.User;

import java.time.LocalDateTime;
import java.util.List;

public interface CandidateService {

    Candidate createCandidate(ContactInfo contactInfo, PaymentInfo paymentInfo, String ssn, String drivingCategory, Integer numberOfLessons);

    List<Candidate> getAll();

    Candidate getById(Long candidateId);

    List<Candidate> getCandidatesNotPaid();

    Candidate updateCandidate(Long candidateId, PaymentInfo paymentInfo, ContactInfo contactInfo, String ssn, Integer numberOfLessons, String drivingCategory);

    void removeCandidate(Long candidateId);

    void checkDebt(Long candidateId);

    void paySpending(Long candidateId, Long spendingId);

    void markAsLegalDriver(Long candidateId);
}
