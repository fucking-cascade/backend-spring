package org.latheild.progress.controller;

import org.latheild.apiutils.api.BaseResponseBody;
import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ExceptionResponseBody;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.progress.api.dto.ProgressDTO;
import org.latheild.progress.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.progress.api.constant.ProgressURL.*;

@RestController
public class ProgressController {
    @Autowired
    ProgressService progressService;

    @RequestMapping(value = CHECK_PROGRESS_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkProgressExistence(
            @RequestParam(value = "progressId") String progressId
    ) {
        return progressService.checkProgressExistence(progressId);
    }

    @RequestMapping(value = CREATE_PROGRESS_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object createProgress(
            @RequestBody ProgressDTO progressDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, progressService.createProgress(progressDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = UPDATE_PROGRESS_NAME_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object updateProgressName(
            @RequestBody ProgressDTO progressDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, progressService.updateProgressName(progressDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = CHANGE_PROGRESS_ORDER_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object changeProgressOrder(
            @RequestBody ProgressDTO progressDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, progressService.changeProgressOrder(progressDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = DELETE_PROGRESS_BY_ID_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object deleteProgressById(
            @RequestBody ProgressDTO progressDTO
    ) {
        try {
            progressService.deleteProgressById(progressDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_PROGRESS_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getProgressById(
            @RequestParam(value = "id") String id
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, progressService.getProgressById(id));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_PROGRESS_LIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getProgressList(
            @RequestParam(value = "ownerId", required = false) String ownerId,
            @RequestParam(value = "projectId", required = false) String projectId
    ) {
        try {
            if (ownerId != null & projectId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, progressService.getProgressListByOwnerIdAndProjectId(ownerId, projectId));
            } else if (ownerId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, progressService.getProgressListByOwnerId(ownerId));
            } else if (projectId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, progressService.getProgressListByProjectId(projectId));
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_GET_ALL_PROGRESS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminGetAllProgress(
            @RequestParam(value = "code") String code
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, progressService.adminGetAllProgress(code));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_PROGRESS_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteProgressById(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "code") String code
    ) {
        try {
            progressService.adminDeleteProgressById(id, code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_ALL_PROGRESS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteAllProgress(
            @RequestParam(value = "ownerId", required = false) String ownerId,
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "code") String code
    ) {
        try {
            if (ownerId != null & projectId != null) {
                progressService.adminDeleteProgressByOwnerIdAndProjectId(ownerId, projectId, code);
            } else if (ownerId != null) {
                progressService.adminDeleteProgressByOwnerId(ownerId, code);
            } else if (projectId != null) {
                progressService.adminDeleteProgressByProjectId(projectId, code);
            } else {
                progressService.adminDeleteAllProgress(code);
            }
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }
}
