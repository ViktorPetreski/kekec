package com.example.kekec.model.service.impl;

import com.example.kekec.model.jpa.Role;
import com.example.kekec.model.persistence.RoleRepository;
import com.example.kekec.model.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role createRole(String type) {
        Role role = new Role();
        role.setRole(type);
        return roleRepository.save(role);
    }
}
