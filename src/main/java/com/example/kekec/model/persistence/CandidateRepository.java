package com.example.kekec.model.persistence;

import com.example.kekec.model.jpa.Candidate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CandidateRepository extends CrudRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {
}
