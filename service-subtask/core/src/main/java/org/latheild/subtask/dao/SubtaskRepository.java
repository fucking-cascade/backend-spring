package org.latheild.subtask.dao;

import org.latheild.common.api.CommonTaskStatus;
import org.latheild.subtask.domain.Subtask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface SubtaskRepository extends MongoRepository<Subtask, String> {
    ArrayList<Subtask> findAllByUserId(String userId);

    ArrayList<Subtask> findAllByTaskId(String taskId);

    ArrayList<Subtask> findAllByTaskStatus(CommonTaskStatus taskStatus);

    ArrayList<Subtask> findAll();

    Subtask findById(String id);

    int countByUserId(String userId);

    int countByTaskId(String taskId);

    int countById(String id);

    void deleteAllByUserId(String userId);

    void deleteAllByTaskId(String taskId);

    void deleteById(String id);
}
