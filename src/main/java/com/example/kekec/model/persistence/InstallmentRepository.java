package com.example.kekec.model.persistence;

import com.example.kekec.model.jpa.Installment;
import org.springframework.data.repository.CrudRepository;

public interface InstallmentRepository  extends CrudRepository<Installment, Long> {
}
