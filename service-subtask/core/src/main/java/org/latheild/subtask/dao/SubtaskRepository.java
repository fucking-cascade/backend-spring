package org.latheild.subtask.dao;

import org.latheild.common.api.CommonTaskStatus;
import org.latheild.subtask.domain.Subtask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface SubtaskRepository extends MongoRepository<Subtask, String> {
    Subtask findById(String id);

    ArrayList<Subtask> findAllByUserId(String userId);

    ArrayList<Subtask> findAllByTaskId(String taskId);

    ArrayList<Subtask> findAllByUserIdAndTaskId(String userId, String taskId);

    ArrayList<Subtask> findAll();

    int countById(String id);

    int countByUserId(String userId);

    int countByTaskId(String taskId);

    void deleteById(String id);

    void deleteAllByUserId(String userId);

    void deleteAllByTaskId(String taskId);

    void deleteAllByUserIdAndTaskId(String userId, String taskId);
}
