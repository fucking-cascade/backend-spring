package org.latheild.user.dao;

import org.latheild.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface UserRepository extends MongoRepository<User, String> {
    public ArrayList<User> findAll();

    public User findById(String id);

    public User findByEmail(String email);

    public int countByEmail(String email);

    public int countById(String id);

    public void deleteByEmail(String email);

    public void deleteById(String id);
}
