package org.latheild.comment.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.comment.api.constant.CommentErrorCode;
import org.latheild.comment.api.dto.CommentDTO;
import org.latheild.comment.client.UserClient;
import org.latheild.comment.constant.DAOQueryMode;
import org.latheild.comment.dao.CommentRepository;
import org.latheild.comment.domain.Comment;
import org.latheild.user.api.constant.UserErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_CODE;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    UserClient userClient;

    @Autowired
    CommentRepository commentRepository;

    private boolean isCommentExist(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                if (commentRepository.countById(target) > 0) {
                    return true;
                } else {
                    return false;
                }
            case QUERY_BY_USER_ID:
                if (commentRepository.countByUserId(target) > 0) {
                    return true;
                } else {
                    return false;
                }
            case QUERY_BY_TASK_ID:
                if (commentRepository.countByTaskId(target) > 0) {
                    return true;
                } else {
                    return false;
                }
            default:
                throw new AppBusinessException(
                        CommonErrorCode.INTERNAL_ERROR
                );
        }
    }

    private CommentDTO convertFromCommentToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setTaskId(comment.getTaskId());
        commentDTO.setUserId(comment.getUserId());
        return commentDTO;
    }

    private ArrayList<CommentDTO> convertFromCommentsToCommentDTOs(ArrayList<Comment> comments) {
        ArrayList<CommentDTO> commentDTOs = new ArrayList<CommentDTO>();
        for (Comment comment : comments) {
            commentDTOs.add(convertFromCommentToCommentDTO(comment));
        }
        return commentDTOs;
    }

    private Comment convertFromCommentDTOToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setTaskId(commentDTO.getTaskId());
        comment.setUserId(commentDTO.getUserId());
        return comment;
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        if (userClient.checkUserExistance(commentDTO.getUserId())) {
            /*if (taskClient.checkTaskExistance(commentDTO.getTaskId())) {
                commentRepository.save(convertFromCommentDTOToComment(commentDTO));
                return commentDTO;
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TaskNotExist,
                        String.format("Task %s does not exist", commentDTO.getTaskId())
                );
            }*/
            commentRepository.save(convertFromCommentDTOToComment(commentDTO));
            return commentDTO;
        } else {
            throw new AppBusinessException(
                    UserErrorCode.UserNotExist,
                    String.format("User %s does not exist", commentDTO.getUserId())
            );
        }
    }

    @Override
    public void deleteComment(CommentDTO commentDTO) {
        if (isCommentExist(DAOQueryMode.QUERY_BY_ID, commentDTO.getCommentId())) {
            Comment comment = commentRepository.findById(commentDTO.getCommentId());
            if (comment.getUserId().equals(commentDTO.getUserId())) {
                commentRepository.deleteById(commentDTO.getUserId());
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    CommentErrorCode.CommentNotExist,
                    String.format("Comment %s does not exist", commentDTO.getCommentId())
            );
        }
    }

    @Override
    public CommentDTO getCommentById(String id) {
        if (isCommentExist(DAOQueryMode.QUERY_BY_ID, id)) {
            return convertFromCommentToCommentDTO(commentRepository.findById(id));
        } else {
            throw new AppBusinessException(
                    CommentErrorCode.CommentNotExist,
                    String.format("Comment %s does not exist", id)
            );
        }
    }

    @Override
    public ArrayList<CommentDTO> getCommentsByUserId(String userId) {
        if (isCommentExist(DAOQueryMode.QUERY_BY_USER_ID, userId)) {
            return convertFromCommentsToCommentDTOs(commentRepository.findAllByUserId(userId));
        } else {
            throw new AppBusinessException(
                    CommentErrorCode.CommentNotExist,
                    String.format("User %s hasn't made any comment", userId)
            );
        }
    }

    @Override
    public ArrayList<CommentDTO> getCommentsByTaskId(String taskId) {
        if (isCommentExist(DAOQueryMode.QUERY_BY_TASK_ID, taskId)) {
            return convertFromCommentsToCommentDTOs(commentRepository.findAllByTaskId(taskId));
        } else {
            throw new AppBusinessException(
                    CommentErrorCode.CommentNotExist,
                    String.format("No comment was made under task %s", taskId)
            );
        }
    }

    @Override
    public ArrayList<CommentDTO> getCommentsByUserIdAndTaskId(String userId, String taskId) {
        if (isCommentExist(DAOQueryMode.QUERY_BY_USER_ID, userId)) {
            if (isCommentExist(DAOQueryMode.QUERY_BY_TASK_ID, taskId)) {
                return convertFromCommentsToCommentDTOs(commentRepository.findAllByUserIdAndAndTaskId(userId, taskId));
            } else {
                throw new AppBusinessException(
                        CommentErrorCode.CommentNotExist,
                        String.format("No comment was made by user %s under task %s", userId, taskId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommentErrorCode.CommentNotExist,
                    String.format("User %s hasn't made any comment", userId)
            );
        }
    }

    @Override
    public ArrayList<CommentDTO> adminGetAllComments(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (commentRepository.count() > 0) {
                return convertFromCommentsToCommentDTOs(commentRepository.findAll());
            } else {
                throw new AppBusinessException(
                        CommentErrorCode.CommentNotExist
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }


    @Override
    public void adminDeleteCommentById(String id, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isCommentExist(DAOQueryMode.QUERY_BY_ID, id)) {
                commentRepository.deleteById(id);
            } else {
                throw new AppBusinessException(
                        CommentErrorCode.CommentNotExist,
                        String.format("Comment %s does not exist", id)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteCommentsByUserId(String userId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isCommentExist(DAOQueryMode.QUERY_BY_USER_ID, userId)) {
                commentRepository.deleteAllByUserId(userId);
            } else {
                throw new AppBusinessException(
                        CommentErrorCode.CommentNotExist,
                        String.format("User %s hasn't made any comment", userId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteCommentsByTaskId(String taskId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isCommentExist(DAOQueryMode.QUERY_BY_TASK_ID, taskId)) {
                commentRepository.deleteAllByTaskId(taskId);
            } else {
                throw new AppBusinessException(
                        CommentErrorCode.CommentNotExist,
                        String.format("No comment was made under task %s", taskId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteCommentsByUserIdAndTaskId(String userId, String taskId, String code) {
        if (isCommentExist(DAOQueryMode.QUERY_BY_USER_ID, userId)) {
            if (isCommentExist(DAOQueryMode.QUERY_BY_TASK_ID, taskId)) {
                commentRepository.deleteAllByUserIdAndTaskId(userId, taskId);
            } else {
                throw new AppBusinessException(
                        CommentErrorCode.CommentNotExist,
                        String.format("No comment was made by user %s under task %s", userId, taskId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommentErrorCode.CommentNotExist,
                    String.format("User %s hasn't made any comment", userId)
            );
        }
    }

    @Override
    public void adminDeleteAllComments(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (commentRepository.count() > 0) {
                commentRepository.deleteAll();
            } else {
                throw new AppBusinessException(
                        CommentErrorCode.CommentNotExist
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }
}
