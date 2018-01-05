package org.latheild.relation.core.dao;

import org.latheild.relation.core.domain.FileTaskRelation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface FileTaskRelationRepository extends MongoRepository<FileTaskRelation, String> {
    FileTaskRelation findById(String id);

    ArrayList<FileTaskRelation> findAllByFileId(String fileId);

    ArrayList<FileTaskRelation> findAllByTaskId(String taskId);

    FileTaskRelation findByFileIdAndTaskId(String fileId, String taskId);

    ArrayList<FileTaskRelation> findAll();

    int countById(String id);

    int countByFileId(String fileId);

    int countByTaskId(String taskId);

    int countByFileIdAndTaskId(String fileId, String taskId);

    void deleteById(String id);

    void deleteAllByFileId(String fileId);

    void deleteAllByTaskId(String taskId);

    void deleteByFileIdAndTaskId(String fileId, String taskId);
}
