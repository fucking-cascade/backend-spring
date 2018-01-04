package org.latheild.comment.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.comment.api.constant.CommentErrorCode;
import org.latheild.comment.api.dto.CommentDTO;
import org.latheild.comment.client.TaskClient;
import org.latheild.comment.client.UserClient;
import org.latheild.comment.constant.DAOQueryMode;
import org.latheild.comment.dao.CommentRepository;
import org.latheild.comment.domain.Comment;
import org.latheild.common.domain.Message;
import org.latheild.task.api.constant.TaskErrorCode;
import org.latheild.user.api.constant.UserErrorCode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_CODE;
import static org.latheild.apiutils.constant.Constants.ADMIN_DELETE_ALL;
import static org.latheild.common.constant.RabbitMQQueue.COMMENT_QUEUE;

@Service
@RabbitListener(queues = COMMENT_QUEUE)
public class CommentServiceImpl implements CommentService {
    @Autowired
    UserClient userClient;

    @Autowired
    TaskClient taskClient;

    @Autowired
    CommentRepository commentRepository;

    private boolean isCommentExist(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                return (commentRepository.countById(target) > 0);
            case QUERY_BY_USER_ID:
                return (commentRepository.countByUserId(target) > 0);
            case QUERY_BY_TASK_ID:
                return (commentRepository.countByTaskId(target) > 0);
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
        ArrayList<CommentDTO> commentDTOs = new ArrayList<>();
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

    @RabbitHandler
    public void eventHandler(Message message) {
        String messageBody;
        switch (message.getMessageType()) {
            case USER_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    if (commentRepository.count() > 0) {
                        adminDeleteAllComments(ADMIN_CODE);
                    }
                } else {
                    if (isCommentExist(DAOQueryMode.QUERY_BY_USER_ID, messageBody)) {
                        commentRepository.deleteAllByUserId(messageBody);
                    }
                }
                break;
            case TASK_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    if (commentRepository.count() > 0) {
                        adminDeleteAllComments(ADMIN_CODE);
                    }
                } else {
                    if (isCommentExist(DAOQueryMode.QUERY_BY_TASK_ID, messageBody)) {
                        commentRepository.deleteAllByTaskId(messageBody);
                    }
                }
                break;
            default:
                System.out.println(message.toString());
        }
    }

    @Override
    public CommentDTO addComment(CommentDTO commentDTO) {
        if (userClient.checkUserExistence(commentDTO.getUserId())) {
            if (taskClient.checkTaskExistence(commentDTO.getTaskId())) {
                Comment comment = convertFromCommentDTOToComment(commentDTO);
                commentRepository.save(comment);
                return convertFromCommentToCommentDTO(comment);
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST,
                        String.format("Task %s does not exist", commentDTO.getTaskId())
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", commentDTO.getUserId())
            );
        }
    }

    @Override
    public void deleteComment(CommentDTO commentDTO) {
        if (isCommentExist(DAOQueryMode.QUERY_BY_ID, commentDTO.getCommentId())) {
            Comment comment = commentRepository.findById(commentDTO.getCommentId());
            if (comment.getUserId().equals(commentDTO.getUserId())) {
                commentRepository.deleteById(commentDTO.getCommentId());
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    CommentErrorCode.COMMENT_NOT_EXIST,
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
                    CommentErrorCode.COMMENT_NOT_EXIST,
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
                    CommentErrorCode.COMMENT_NOT_EXIST,
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
                    CommentErrorCode.COMMENT_NOT_EXIST,
                    String.format("No comment is made under task %s", taskId)
            );
        }
    }

    @Override
    public ArrayList<CommentDTO> getCommentsByUserIdAndTaskId(String userId, String taskId) {
        if (isCommentExist(DAOQueryMode.QUERY_BY_USER_ID, userId)) {
            if (isCommentExist(DAOQueryMode.QUERY_BY_TASK_ID, taskId)) {
                return convertFromCommentsToCommentDTOs(commentRepository.findAllByUserIdAndTaskId(userId, taskId));
            } else {
                throw new AppBusinessException(
                        CommentErrorCode.COMMENT_NOT_EXIST,
                        String.format("No comment is made by user %s under task %s", userId, taskId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommentErrorCode.COMMENT_NOT_EXIST,
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
                        CommentErrorCode.COMMENT_NOT_EXIST
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
                        CommentErrorCode.COMMENT_NOT_EXIST,
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
                        CommentErrorCode.COMMENT_NOT_EXIST,
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
                        CommentErrorCode.COMMENT_NOT_EXIST,
                        String.format("No comment is made under task %s", taskId)
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
                        CommentErrorCode.COMMENT_NOT_EXIST,
                        String.format("No comment is made by user %s under task %s", userId, taskId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommentErrorCode.COMMENT_NOT_EXIST,
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
                        CommentErrorCode.COMMENT_NOT_EXIST
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }
}
