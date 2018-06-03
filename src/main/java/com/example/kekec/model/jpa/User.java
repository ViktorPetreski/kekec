package com.example.kekec.model.jpa;

import com.example.kekec.model.enums.Provider;
import com.example.kekec.model.enums.UserType;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    public String username;

    public String password;

    public String email;

    @Enumerated(EnumType.STRING)
    public UserType type;

    @Enumerated(EnumType.STRING)
    public Provider provider;

    @OneToOne
    public ContactInfo contactInfo;


}
