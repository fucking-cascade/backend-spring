package org.latheild.subtask.controller;

import org.latheild.apiutils.api.BaseResponseBody;
import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ExceptionResponseBody;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.subtask.api.dto.SubtaskDTO;
import org.latheild.subtask.service.SubtaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.subtask.api.constant.SubtaskURL.*;

@RestController
public class SubtaskController {
    @Autowired
    SubtaskService subtaskService;

    @RequestMapping(value = CREATE_SUBTASK_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object createSubtask(
            @RequestBody SubtaskDTO subtaskDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, subtaskService.createSubtask(subtaskDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = DELETE_SUBTASK_BY_ID_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object deleteSubtaskById(
            @RequestBody SubtaskDTO subtaskDTO
    ) {
        try {
            subtaskService.deleteSubtaskById(subtaskDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = UPDATE_SUBTASK_CONTENT_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object updateSubtaskContent(
            @RequestBody SubtaskDTO subtaskDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, subtaskService.updateSubtaskContent(subtaskDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = UPDATE_SUBTASK_STATE_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object updateSubtaskState(
            @RequestBody SubtaskDTO subtaskDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, subtaskService.updateSubtaskState(subtaskDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_SUBTASK_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getSubtaskById(
            @RequestParam(value = "id") String id
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, subtaskService.getSubtaskById(id));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_SUBTASKS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getSubtasks(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "taskId", required = false) String taskId
    ) {
        try {
            if (userId != null && taskId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, subtaskService.getSubtasksByUserIdAndTaskId(userId, taskId));
            } else if (userId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, subtaskService.getSubtasksByUserId(userId));
            } else if (taskId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, subtaskService.getSubtasksByTaskId(taskId));
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_GET_ALL_SUBTASKS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminGetlAllSubtasks(
            @RequestParam(value = "code") String code
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, subtaskService.adminGetAllSubtasks(code));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_SUBTASK_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object updateSubtaskState(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "code") String code
    ) {
        try {
            subtaskService.adminDeleteSubtaskById(id, code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_ALL_SUBTASKS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object updateSubtaskState(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "taskId", required = false) String taskId,
            @RequestParam(value = "code") String code
    ) {
        try {
            if (userId != null && taskId != null) {
                subtaskService.adminDeleteAllSubtasksByUserIdAndTaskId(userId, taskId, code);
            } else if (userId != null) {
                subtaskService.adminDeleteAllSubtasksByUserId(userId, code);
            } else if (taskId != null) {
                subtaskService.adminDeleteAllSubtasksByTaskId(taskId, code);
            } else {
                subtaskService.adminDeleteAllSubtasks(code);
            }
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }
}
