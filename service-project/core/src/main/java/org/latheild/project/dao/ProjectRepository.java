package org.latheild.project.dao;

import org.latheild.project.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface ProjectRepository extends MongoRepository<Project, String> {
    public Project findById(String id);

    public ArrayList<Project> findAllByOwnerId(String ownerId);

    public ArrayList<Project> findAll();

    public int countById(String id);

    public int countByOwnerId(String ownerId);

    public void deleteById(String id);

    public void deleteAllByOwnerId(String ownerID);
}
