package org.latheild.task.dao;

import org.latheild.task.domain.Task;

import java.util.ArrayList;

public interface TaskRepository {
    Task findById(String id);

    ArrayList<Task> findAllByOwnerId(String ownerId);

    ArrayList<Task> findAllByProgressId(String progressId);

    ArrayList<Task> findAllByOwnerIdAndProgressId(String ownerId, String progressId);

    ArrayList<Task> findAll();

    int countById(String id);

    int countByOwnerId(String ownerId);

    int countByProgressId(String progressId);

    void deleteById(String id);

    void deleteAllByOwnerId(String ownerId);

    void deleteAllByProgressId(String progressId);

    void deleteAllByOwnerIdAndProgressId(String ownerId, String progressId);

    void deleteAll();

    void save(Task task);

    int count();
}
