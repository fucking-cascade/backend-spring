package org.latheild.comment.service;

import org.latheild.comment.api.dto.CommentDTO;

import java.util.ArrayList;

public interface CommentService {
    public CommentDTO addComment(CommentDTO commentDTO);

    public void deleteComment(CommentDTO commentDTO);

    public CommentDTO getCommentById(String id);

    public ArrayList<CommentDTO> getCommentsByUserId(String userId);

    public ArrayList<CommentDTO> getCommentsByTaskId(String taskId);

    public ArrayList<CommentDTO> getCommentsByUserIdAndTaskId(String userId, String taskId);

    public ArrayList<CommentDTO> adminGetAllComments(String code);

    public void adminDeleteCommentById(String id, String code);

    public void adminDeleteCommentsByUserId(String userId, String code);

    public void adminDeleteCommentsByTaskId(String taskId, String code);

    public void adminDeleteCommentsByUserIdAndTaskId(String userId, String taskId, String code);

    public void adminDeleteAllComments(String code);
}
