package com.example.kekec.model.jpa;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;
    @Column(name = "email")
    @NotEmpty(message = "*Фали корисничко име")
    private String email;
    @Column(name = "password")
    @Length(min = 5, message = "*Лозинката треба да има барем 5 карактери")
    @NotEmpty(message = "*Фали лозинка")
    private String password;
    @Column(name = "name")
    @NotEmpty(message = "*Фали име")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "*Фали презиме")
    private String lastName;
    @Column(name = "active")
    private int active;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
