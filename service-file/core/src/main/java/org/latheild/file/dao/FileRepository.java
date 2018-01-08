package org.latheild.file.dao;

import org.latheild.file.domain.File;

import java.util.ArrayList;

public interface FileRepository {
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

    void deleteAll();

    void save(File file);

    int count();
}
