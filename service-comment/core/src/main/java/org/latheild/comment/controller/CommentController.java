package org.latheild.comment.controller;

import org.latheild.apiutils.api.BaseResponseBody;
import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ExceptionResponseBody;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.comment.api.dto.CommentDTO;
import org.latheild.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.comment.api.constant.CommentURL.*;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value = ADD_COMMENT_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object addComment(
            @RequestBody CommentDTO commentDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, commentService.addComment(commentDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = DELETE_COMMENT_BY_ID_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object deleteCommentById(
            @RequestBody CommentDTO commentDTO
    ) {
        try {
            commentService.deleteComment(commentDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_COMMENT_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getCommentById(
            @RequestParam(value = "id") String id
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, commentService.getCommentById(id));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_COMMENTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getComments(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "taskId", required = false) String taskId
    ) {
        try {
            if (userId != null && taskId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, commentService.getCommentsByUserIdAndTaskId(userId, taskId));
            } else if (userId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, commentService.getCommentsByUserId(userId));
            } else if (taskId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, commentService.getCommentsByTaskId(taskId));
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_GET_ALL_COMMENTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminGetAllComments(
            @RequestParam(value = "code") String code
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, commentService.adminGetAllComments(code));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_COMMENT_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteCommentById(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "code") String code
    ) {
        try {
            commentService.adminDeleteCommentById(id, code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_ALL_COMMENTS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteAllComments(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "taskId", required = false) String taskId,
            @RequestParam(value = "code") String code
    ) {
        try {
            if (userId != null && taskId != null) {
                commentService.adminDeleteCommentsByUserIdAndTaskId(userId, taskId, code);
            } else if (userId != null) {
                commentService.adminDeleteCommentsByUserId(userId, code);
            } else if (taskId != null) {
                commentService.adminDeleteCommentsByTaskId(taskId, code);
            } else {
                commentService.adminDeleteAllComments(code);
            }
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }
}
