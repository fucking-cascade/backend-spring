package org.latheild.comment.dao;

import org.latheild.comment.domain.Comment;

import java.util.ArrayList;

public interface CommentRepository {
    Comment findById(String id);

    ArrayList<Comment> findAllByUserId(String userId);

    ArrayList<Comment> findAllByTaskId(String taskId);

    ArrayList<Comment> findAllByUserIdAndTaskId(String userId, String taskId);

    ArrayList<Comment> findAll();

    int countById(String id);

    int countByUserId(String userId);

    int countByTaskId(String taskId);

    void deleteById(String id);

    void deleteAllByUserId(String userId);

    void deleteAllByTaskId(String taskId);

    void deleteAllByUserIdAndTaskId(String userId, String taskId);

    void deleteAll();

    void save(Comment comment);

    int count();
}
