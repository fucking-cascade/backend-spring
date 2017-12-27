package org.latheild.user.dao;

import org.latheild.user.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    public User findById(Long id);

    public User findByEmail(String email);

    public int countByEmail(String email);
}
