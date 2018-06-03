package com.example.kekec.model.jpa;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "installments")
public class Installment extends BaseEntity{

    public Double price;
    public Boolean isPaid;
    public LocalDateTime dueDate;
    public LocalDateTime startingDate;
    public LocalDateTime datePaid;


}
