package org.latheild.subtask.dao;

import org.latheild.common.api.CommonTaskStatus;
import org.latheild.subtask.domain.Subtask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface SubtaskRepository extends MongoRepository<Subtask, String> {
    public ArrayList<Subtask> findAllByUserId(String userId);

    public ArrayList<Subtask> findAllByTaskId(String taskId);

    public ArrayList<Subtask> findAllByTaskStatus(CommonTaskStatus taskStatus);

    public ArrayList<Subtask> findAll();

    public Subtask findById(String id);

    public int countByUserId(String userId);

    public int countByTaskId(String taskId);

    public int countByTaskStatus(CommonTaskStatus taskStatus);

    public int countById(String id);

    public void deleteAllByUserId(String userId);

    public void deleteAllByTaskId(String taskId);

    public void deleteById(String id);
}
