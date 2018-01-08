package org.latheild.project.dao;

import org.latheild.project.domain.Project;

import java.util.ArrayList;

public interface ProjectRepository {
    Project findById(String id);

    ArrayList<Project> findAllByOwnerId(String ownerId);

    ArrayList<Project> findAll();

    int countById(String id);

    int countByOwnerId(String ownerId);

    void deleteById(String id);

    void deleteAllByOwnerId(String ownerID);

    void deleteAll();

    void save(Project project);

    int count();
}
