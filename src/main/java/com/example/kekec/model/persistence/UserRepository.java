package com.example.kekec.model.persistence;


import com.example.kekec.model.jpa.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Riste Stojanov
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

  User findByUsername(String username);
}
