package org.latheild.file.dao;

import org.latheild.file.domain.File;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface FileRepository extends MongoRepository<File, String> {
    File findById(String id);

    ArrayList<File> findAllByOwnerId(String ownerId);

    ArrayList<File> findAllByProjectId(String projectId);

    ArrayList<File> findAllByOwnerIdAndProjectId(String ownerId, String projectId);

    ArrayList<File> findAll();

    int countById(String id);

    int countByOwnerId(String ownerId);

    int countByProjectId(String projectId);

    void deleteById(String id);

    void deleteAllByOwnerId(String ownerId);

    void deleteAllByProjectId(String projectId);

    void deleteAllByOwnerIdAndProjectId(String ownerId, String projectId);
}
