package com.example.kekec.model.jpa;

import com.example.kekec.model.enums.LessonType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "driving_lessons")
public class DrivingLesson extends BaseEntity{

    @ManyToOne
    public Candidate candidate;

    @ManyToOne
    @NotNull
    public Instructor instructor;

    @NotNull
    public LocalDateTime dateTaken;

    @NotNull
    public LessonType type;
}
