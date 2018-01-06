package org.latheild.schedule.controller;

import org.latheild.apiutils.api.BaseResponseBody;
import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ExceptionResponseBody;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.schedule.api.dto.ScheduleDTO;
import org.latheild.schedule.api.dto.ScheduleParticipantOperationDTO;
import org.latheild.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.schedule.api.constant.ScheduleURL.*;

@RestController
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @RequestMapping(value = GET_PROJECT_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public String getProjectId(
            @RequestParam(value = "scheduleId") String scheduleId
    ) {
        return scheduleService.getScheduleById(scheduleId).getProjectId();
    }

    @RequestMapping(value = CHECK_SCHEDULE_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkScheduleExistence(
            @RequestParam(value = "scheduleId") String scheduleId
    ) {
        return scheduleService.checkScheduleExistence(scheduleId);
    }

    @RequestMapping(value = CREATE_SCHEDULE_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object createSchedule(
            @RequestBody ScheduleDTO scheduleDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, scheduleService.createSchedule(scheduleDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = UPDATE_SCHEDULE_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object updateSchedule(
            @RequestBody ScheduleDTO scheduleDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, scheduleService.updateSchedule(scheduleDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = DELETE_SCHEDULE_BY_ID_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object deleteScheduleById(
            @RequestBody ScheduleDTO scheduleDTO
    ) {
        try {
            scheduleService.deleteScheduleById(scheduleDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_SCHEDULE_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getScheduleById(
            @RequestParam(value = "id") String id
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, scheduleService.getScheduleById(id));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_SCHEDULES_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getSchedules(
            @RequestParam(value = "ownerId", required = false) String ownerId,
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "userId", required = false) String userId
    ) {
        try {
            if (ownerId != null && projectId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, scheduleService.getSchedulesByOwnerIdAndProjectId(ownerId, projectId));
            } else if (ownerId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, scheduleService.getSchedulesByOwnerId(ownerId));
            } else if (projectId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, scheduleService.getSchedulesByProjectId(projectId));
            } else if (userId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, scheduleService.getAllSchedulesByUserId(userId));
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_GET_ALL_SCHEDULES_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminGetAllSchedules(
            @RequestParam(value = "code") String code
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, scheduleService.adminGetAllSchedules(code));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_SCHEDULE_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteScheduleById(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "code") String code
    ) {
        try {
            scheduleService.adminDeleteScheduleById(id, code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_SCHEDULES_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteSchedules(
            @RequestParam(value = "ownerId", required = false) String ownerId,
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "code") String code
    ) {
        try {
            if (ownerId != null && projectId != null) {
                scheduleService.adminDeleteSchedulesByOwnerIdAndProjectId(ownerId, projectId, code);
            } else if (ownerId != null) {
                scheduleService.adminDeleteSchedulesByOwnerId(ownerId, code);
            } else if (projectId != null) {
                scheduleService.adminDeleteSchedulesByProjectId(projectId, code);
            } else {
                scheduleService.adminDeleteAllSchedules(code);
            }
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADD_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object addScheduleParticipant(
            @RequestBody ScheduleParticipantOperationDTO scheduleParticipantOperationDTO
    ) {
        try {
            scheduleService.addScheduleParticipant(scheduleParticipantOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = REMOVE_SCHEDULE_PARTICIPANT_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object removeScheduleParticipant(
            @RequestBody ScheduleParticipantOperationDTO scheduleParticipantOperationDTO
    ) {
        try {
            scheduleService.removeScheduleParticipant(scheduleParticipantOperationDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }
}
