package org.latheild.user.dao;

import org.latheild.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface UserRepository extends MongoRepository<User, String> {
    User findById(String id);

    User findByEmail(String email);

    ArrayList<User> findAll();

    int countById(String id);

    int countByEmail(String email);

    void deleteById(String id);

    void deleteByEmail(String email);
}
