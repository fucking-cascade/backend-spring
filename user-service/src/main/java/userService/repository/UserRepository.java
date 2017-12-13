package userService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import userService.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
    public User findById(int id);
}
