package com.example.kekec.model.persistence;

import com.example.kekec.model.jpa.AdditionalSpending;
import org.springframework.data.repository.CrudRepository;

public interface AdditionalSpendingRepository extends CrudRepository<AdditionalSpending, Long> {
}
