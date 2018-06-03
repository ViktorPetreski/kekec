package com.example.kekec.model.persistence;

import com.example.kekec.model.jpa.DrivingLesson;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface DrivingLessonRepository extends CrudRepository<DrivingLesson, Long>, JpaSpecificationExecutor<DrivingLesson> {
}
