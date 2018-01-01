package org.latheild.userinfo.controller;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ExceptionResponseBody;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.apiutils.api.BaseResponseBody;
import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.userinfo.api.dto.UserInfoDTO;
import org.latheild.userinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.userinfo.api.UserInfoURL.*;

@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = REGISTER_USER_INFO_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object register(
            @RequestBody RegisterDTO registerDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, userInfoService.register(registerDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = UPDATE_USER_INFO_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object updateUserInfo(
            @RequestBody UserInfoDTO userInfoDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, userInfoService.updateUserInfo(userInfoDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_USER_INFO_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getUserInfoById(
            @RequestParam(value = "userId", required = true) String userId
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, userInfoService.getUserInfoByUserId(userId));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_USER_INFOS_BY_NAME_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getUserInfosByName(
            @RequestParam(value = "name", required = true) String name
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, userInfoService.getUserInfosByName(name));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_USER_INFOS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getUserInfos() {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, userInfoService.getAllUserInfos());
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_RESET_USER_INFO_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminResetUserInfoById(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "code", required = true) String code
    ) {
        try {
            userInfoService.resetUserInfoById(userId, code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_ALL_USER_INFOS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteAllUserInfos(
            @RequestParam(value = "code", required = true) String code
    ) {
        try {
            userInfoService.deleteAllUserInfos(code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_USER_INFO_BY_ID_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteUserInfoByUserId(
            @RequestParam(value = "userId", required = true) String userId,
            @RequestParam(value = "code", required = true) String code
    ) {
        try {
            userInfoService.deleteUserInfoByUserId(userId, code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }
}
