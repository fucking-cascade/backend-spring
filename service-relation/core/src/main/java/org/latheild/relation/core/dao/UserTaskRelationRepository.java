package org.latheild.relation.core.dao;

import org.latheild.relation.core.domain.UserTaskRelation;

import java.util.ArrayList;

public interface UserTaskRelationRepository {
    UserTaskRelation findById(String id);

    ArrayList<UserTaskRelation> findAllByUserId(String userId);

    ArrayList<UserTaskRelation> findAllByTaskId(String taskId);

    UserTaskRelation findByUserIdAndTaskId(String userId, String taskId);

    ArrayList<UserTaskRelation> findAll();

    int countById(String id);

    int countByUserId(String userId);

    int countByTaskId(String taskId);

    int countByUserIdAndTaskId(String userId, String taskId);

    void deleteById(String id);

    void deleteAllByUserId(String userId);

    void deleteAllByTaskId(String taskId);

    void deleteByUserIdAndTaskId(String userId, String taskId);

    void deleteAll();

    void save(UserTaskRelation userTaskRelation);

    int count();
}
