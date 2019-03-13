package com.example.kekec.model.service;

import com.example.kekec.model.jpa.User;

public interface UserService {

    User findUserByEmail(String email);
    void saveUser(User user);
}
