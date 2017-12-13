package service1.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import service1.domain.Entity;
import service1.domain.Type;

import java.util.List;

public interface EntityRepository extends MongoRepository<Entity, String> {
    public List<Entity> findByName(String name);
    public List<Entity> findByType(Type type);
}
