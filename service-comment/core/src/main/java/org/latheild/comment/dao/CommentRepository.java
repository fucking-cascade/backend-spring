package org.latheild.comment.dao;

import org.latheild.comment.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface CommentRepository extends MongoRepository<Comment, String> {
    Comment findById(String id);

    ArrayList<Comment> findAllByUserId(String userId);

    ArrayList<Comment> findAllByTaskId(String userId);

    ArrayList<Comment> findAllByUserIdAndTaskId(String userId, String taskId);

    ArrayList<Comment> findAll();

    int countById(String id);

    int countByUserId(String userId);

    int countByTaskId(String taskId);

    void deleteById(String id);

    void deleteAllByUserId(String userId);

    void deleteAllByTaskId(String taskId);

    void deleteAllByUserIdAndTaskId(String userId, String taskId);
}
