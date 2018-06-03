package com.example.kekec.model.jpa;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "additional_spending")
public class AdditionalSpending extends BaseEntity {

    public String description;
    public Double price;
    public Boolean isPaid;
    public LocalDateTime datePaid;

}
