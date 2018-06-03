package com.example.kekec.model.persistence;

import com.example.kekec.model.jpa.Instructor;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface InstructorRepository extends CrudRepository<Instructor, Long>, JpaSpecificationExecutor<Instructor> {
}
