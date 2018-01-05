package org.latheild.relation.core.dao;

import org.latheild.relation.core.domain.UserProjectRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface UserProjectRelationRepository extends MongoRepository<UserProjectRelation, String> {
    UserProjectRelation findById(String id);

    ArrayList<UserProjectRelation> findAllByUserId(String userId);

    ArrayList<UserProjectRelation> findAllByProjectId(String projectId);

    UserProjectRelation findByUserIdAndProjectId(String userId, String projectId);

    ArrayList<UserProjectRelation> findAll();

    int countById(String id);

    int countByUserId(String userId);

    int countByProjectId(String projectId);

    int countByUserIdAndProjectId(String userId, String projectId);

    void deleteById(String id);

    void deleteAllByUserId(String userId);

    void deleteAllByProjectId(String projectId);

    void deleteByUserIdAndProjectId(String userId, String projectId);
}
