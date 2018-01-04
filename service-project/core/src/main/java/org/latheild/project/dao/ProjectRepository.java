package org.latheild.project.dao;

import org.latheild.project.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface ProjectRepository extends MongoRepository<Project, String> {
    Project findById(String id);

    ArrayList<Project> findAllByOwnerId(String ownerId);

    ArrayList<Project> findAll();

    int countById(String id);

    int countByOwnerId(String ownerId);

    void deleteById(String id);

    void deleteAllByOwnerId(String ownerID);
}
