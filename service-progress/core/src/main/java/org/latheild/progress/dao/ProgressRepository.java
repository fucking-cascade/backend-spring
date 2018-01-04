package org.latheild.progress.dao;

import org.latheild.progress.domain.Progress;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface ProgressRepository extends MongoRepository<Progress, String> {
    Progress findById(String id);

    ArrayList<Progress> findAllByOwnerId(String ownerId);

    ArrayList<Progress> findAllByProjectId(String projectId);

    ArrayList<Progress> findAllByProjectIdOrderByIndexAsc(String projectId);

    ArrayList<Progress> findAllByOwnerIdAndProjectId(String ownerId, String projectId);

    ArrayList<Progress> findAll();

    int countById(String id);

    int countByOwnerId(String ownerId);

    int countByProjectId(String projectId);

    void deleteById(String id);

    void deleteAllByOwnerId(String ownerId);

    void deleteAllByProjectId(String projectId);

    void deleteAllByOwnerIdAndProjectId(String ownerId, String projectId);
}
