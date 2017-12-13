package taskService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import taskService.domain.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
    public Task findById(int id);
}
