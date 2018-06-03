package com.example.kekec.model.jpa;


import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "payment_info")
public class PaymentInfo extends BaseEntity{

    public Double totalSum;
    public Integer numberOfInstallments;

    @OneToMany
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    public List<Installment> installments;

    @OneToMany
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    public List<AdditionalSpending> additionalSpendings;

}
