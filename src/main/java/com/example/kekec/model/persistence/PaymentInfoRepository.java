package com.example.kekec.model.persistence;

import com.example.kekec.model.jpa.PaymentInfo;
import org.springframework.data.repository.CrudRepository;

public interface PaymentInfoRepository  extends CrudRepository<PaymentInfo, Long> {
}
