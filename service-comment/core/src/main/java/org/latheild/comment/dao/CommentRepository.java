package org.latheild.comment.dao;

import org.latheild.comment.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;

public interface CommentRepository extends MongoRepository<Comment, String> {
    public Comment findById(String id);

    public ArrayList<Comment> findAllByUserId(String userId);

    public ArrayList<Comment> findAllByTaskId(String userId);

    public ArrayList<Comment> findAllByUserIdAndAndTaskId(String userId, String taskId);

    public ArrayList<Comment> findAll();

    public int countById(String id);

    public int countByUserId(String userId);

    public int countByTaskId(String taskId);

    public void deleteById(String id);

    public void deleteAllByUserId(String userId);

    public void deleteAllByTaskId(String taskId);

    public void deleteAllByUserIdAndTaskId(String userId, String taskId);
}
