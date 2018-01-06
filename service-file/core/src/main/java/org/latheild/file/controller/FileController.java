package org.latheild.file.controller;

import org.latheild.apiutils.api.BaseResponseBody;
import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ExceptionResponseBody;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.file.api.dto.FileDTO;
import org.latheild.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.file.api.constant.FileURL.*;

@RestController
public class FileController {
    @Autowired
    FileService fileService;

    @RequestMapping(value = CHECK_FILE_EXIST_URL, method = RequestMethod.GET, produces =  PRODUCE_JSON)
    public boolean checkFileExistence(
            @RequestParam(value = "fileId") String fileId
    ) {
        return fileService.checkFileExistence(fileId);
    }

    @RequestMapping(value = UPLOAD_FILE_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object uploadFile(
            @RequestBody FileDTO fileDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, fileService.uploadFile(fileDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = RENAME_FILE_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object renameFile(
            @RequestBody FileDTO fileDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, fileService.renameFile(fileDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = DELETE_FILE_BY_ID_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object deleteFileById(
            @RequestBody FileDTO fileDTO
    ) {
        try {
            fileService.deleteFileById(fileDTO);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_FILE_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getFileById(
            @RequestParam(value = "id") String id
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, fileService.getFileById(id));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_FILES_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getFiles(
            @RequestParam(value = "ownerId", required = false) String ownerId,
            @RequestParam(value = "projectId", required = false) String projectId
    ) {
        try {
            if (ownerId != null && projectId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, fileService.getFilesByOwnerIdAndProjectId(ownerId, projectId));
            } else if (ownerId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, fileService.getFilesByOwnerId(ownerId));
            } else if (projectId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, fileService.getFilesByProjectId(projectId));
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_GET_ALL_FILES_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminGetAllFiles(
            @RequestParam(value = "code") String code
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, fileService.adminGetAllFiles(code));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_FILE_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteFileById(
            @RequestParam(value = "id") String id,
            @RequestParam(value = "code") String code
    ) {
        try {
            fileService.adminDeleteFileById(id, code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_FILES_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteFiles(
            @RequestParam(value = "ownerId", required = false) String ownerId,
            @RequestParam(value = "projectId", required = false) String projectId,
            @RequestParam(value = "code") String code
    ) {
        try {
            if (ownerId != null && projectId != null) {
                fileService.adminDeleteFilesByOwnerIdAndProjectId(ownerId, projectId, code);
            } else if (ownerId != null) {
                fileService.adminDeleteFilesByOwnerId(ownerId, code);
            } else if (projectId != null) {
                fileService.adminDeleteFilesByProjectId(projectId, code);
            } else {
                fileService.adminDeleteAllFiles(code);
            }
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }
}
