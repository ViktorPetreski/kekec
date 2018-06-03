package com.example.kekec.model.persistence;

import com.example.kekec.model.jpa.ContactInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Viktor on 10-Apr-17.
 */
public interface ContactInfoRepository extends CrudRepository<ContactInfo, Long> {
}
