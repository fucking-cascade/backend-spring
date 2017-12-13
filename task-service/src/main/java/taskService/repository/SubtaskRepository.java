package taskService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import taskService.domain.Subtask;

public interface SubtaskRepository extends MongoRepository<Subtask, String> {
}
