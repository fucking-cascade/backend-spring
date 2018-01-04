package org.latheild.user.dao;

import org.latheild.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface UserRepository extends MongoRepository<User, String> {
    ArrayList<User> findAll();

    User findById(String id);

    User findByEmail(String email);

    int countByEmail(String email);

    int countById(String id);

    void deleteByEmail(String email);

    void deleteById(String id);
}
