package org.latheild.comment.service;

import org.latheild.comment.api.dto.CommentDTO;

import java.util.ArrayList;

public interface CommentService {
    CommentDTO addComment(CommentDTO commentDTO);

    void deleteComment(CommentDTO commentDTO);

    CommentDTO getCommentById(String id);

    ArrayList<CommentDTO> getCommentsByUserId(String userId);

    ArrayList<CommentDTO> getCommentsByTaskId(String taskId);

    ArrayList<CommentDTO> getCommentsByUserIdAndTaskId(String userId, String taskId);

    ArrayList<CommentDTO> adminGetAllComments(String code);

    void adminDeleteCommentById(String id, String code);

    void adminDeleteCommentsByUserId(String userId, String code);

    void adminDeleteCommentsByTaskId(String taskId, String code);

    void adminDeleteCommentsByUserIdAndTaskId(String userId, String taskId, String code);

    void adminDeleteAllComments(String code);
}
