package org.latheild.user.dao;

import org.latheild.user.domain.User;

import java.util.ArrayList;

public interface UserRepository {
    User findById(String id);

    User findByEmail(String email);

    ArrayList<User> findAll();

    int countById(String id);

    int countByEmail(String email);

    void deleteById(String id);

    void deleteByEmail(String email);

    void deleteAll();

    void save(User user);

    int count();
}
