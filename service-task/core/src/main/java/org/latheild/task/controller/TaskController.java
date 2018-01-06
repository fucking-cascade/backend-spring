package org.latheild.task.controller;

import org.latheild.apiutils.api.BaseResponseBody;
import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ExceptionResponseBody;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.task.api.dto.TaskDTO;
import org.latheild.task.api.dto.TaskParticipantOperationDTO;
import org.latheild.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.task.api.constant.TaskURL.*;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    @RequestMapping(value = GET_PROJECT_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public String getProjectId(
            @RequestParam(value = "taskId") String taskId
    ) {
        return taskService.getProjectId(taskId);
    }

    @RequestMapping(value = CHECK_TASK_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkTaskExistence(
            @RequestParam(value = "taskId") String taskId
    ) {
        return taskService.checkTaskExistence(taskId);
    }

    @RequestMapping(value = CREATE_TASK_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object createTask(
            @RequestBody TaskDTO taskDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.createTask(taskDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = DELETE_TASK_BY_ID_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object deleteTaskById(
            @RequestBody TaskDTO taskDTO
    ) {
        try {
            taskService.deleteTaskById(taskDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = UPDATE_TASK_INFO_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object updateTaskInfo(
            @RequestBody TaskDTO taskDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.updateTaskInfo(taskDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = UPDATE_TASK_STATE_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object updateTaskState(
            @RequestBody TaskDTO taskDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.updateTaskState(taskDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = CHANGE_TASK_ORDER_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object changeTaskOrder(
            @RequestBody TaskDTO taskDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.changeTaskOrder(taskDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = CHANGE_TASK_PROGRESS_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object changeTaskProgress(
            @RequestBody TaskDTO taskDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.changeTaskProgress(taskDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_TASK_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getTaskById(
            @RequestParam(value = "id") String id
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.getTaskById(id));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_TASKS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getTasks(
            @RequestParam(value = "ownerId", required = false) String ownerId,
            @RequestParam(value = "progressId", required = false) String progressId,
            @RequestParam(value = "userId", required = false) String userId
    ) {
        try {
            if (ownerId != null && progressId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.getTasksByOwnerIdAndProgressId(ownerId, progressId));
            } else if (ownerId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.getTasksByOwnerId(ownerId));
            } else if (progressId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.getTasksByProgressId(progressId));
            } else if (userId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.getAllTasksByUserId(userId));
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_GET_ALL_TASKS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminGetAllTasks(
            @RequestParam(value = "code") String code
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, taskService.adminGetAllTasks(code));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_TASK_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteTaskById(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "code") String code
    ) {
        try {
            taskService.adminDeleteTaskById(id, code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_TASKS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteTasks(
            @RequestParam(value = "ownerId", required = false) String ownerId,
            @RequestParam(value = "progressId", required = false) String progressId,
            @RequestParam(value = "code") String code
    ) {
        try {
            if (ownerId != null && progressId != null) {
                taskService.adminDeleteTasksByOwnerIdAndProgressId(ownerId, progressId, code);
            } else if (ownerId != null) {
                taskService.adminDeleteTasksByOwnerId(ownerId, code);
            } else if (progressId != null) {
                taskService.adminDeleteTasksByProgressId(progressId, code);
            } else {
                taskService.adminDeleteAllTasks(code);
            }
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADD_TASK_PARTICIPANT_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object addTaskParticipant(
            @RequestBody TaskParticipantOperationDTO taskParticipantOperationDTO
    ) {
        try {
            taskService.addTaskParticipant(taskParticipantOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = REMOVE_TASK_PARTICIPANT_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object removeTaskParticipant(
            @RequestBody TaskParticipantOperationDTO taskParticipantOperationDTO
    ) {
        try {
            taskService.removeTaskParticipant(taskParticipantOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }
}
