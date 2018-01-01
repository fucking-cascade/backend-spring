package org.latheild.user.controller;

import org.latheild.apiutils.api.BaseResponseBody;
import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.api.ExceptionResponseBody;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.api.dto.ResetPasswordDTO;
import org.latheild.user.api.dto.UserDTO;
import org.latheild.user.api.dto.UserProfileDTO;
import org.latheild.user.domain.User;
import org.latheild.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.latheild.apiutils.constant.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.user.api.UserUrl.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = USER_REGISTER_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object register(
            @RequestBody RegisterDTO registerDTO
    ) {
        try {
            UserDTO userDTO = userService.register(registerDTO);

            UserProfileDTO userProfileDTO = new UserProfileDTO();
            userProfileDTO.setUserId(userDTO.getUserId());
            userProfileDTO.setEmail(userDTO.getEmail());
            userProfileDTO.setAddress(registerDTO.getAddress());
            userProfileDTO.setAvatar(registerDTO.getAvatar());
            userProfileDTO.setGender(registerDTO.getGender());
            userProfileDTO.setJob(registerDTO.getJob());
            userProfileDTO.setPhoneNumber(registerDTO.getPhoneNumber());
            userProfileDTO.setWebsite(registerDTO.getWebsite());
            userProfileDTO.setName(registerDTO.getName());

            return new BaseResponseBody(CommonErrorCode.SUCCESS, userProfileDTO);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = USER_RESET_PASSWORD_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object resetPassword(
            @RequestBody ResetPasswordDTO resetPasswordDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.resetPassword(resetPasswordDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = USER_CHECK_PASSWORD_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public Object checkPassword(
            @RequestBody RegisterDTO registerDTO
    ) {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.checkPassword(registerDTO));
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_USER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getUser(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "email", required = false) String email
    ) {
        try {
            if (userId != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.getUserByUserId(userId));
            } else if (email != null) {
                return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.getUserByEmail(email));
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = GET_USERS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object getAllUsers() {
        try {
            return new BaseResponseBody(CommonErrorCode.SUCCESS, userService.getAllUsers());
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = CHECK_USER_EXIST_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    public boolean checkUserExistance(
            @RequestParam(value = "userId", required = true) String userId
    ) {
        return userService.checkUserExist(userId);
    }

    @RequestMapping(value = ADMIN_DELETE_ALL_USERS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteAllUsers(
            @RequestParam(value = "code", required = true) String code
    ) {
        try {
            userService.adminDeleteAllUsers(code);
            return new BaseResponseBody(CommonErrorCode.SUCCESS);
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }

    @RequestMapping(value = ADMIN_DELETE_USER_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public Object adminDeleteUser(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "code", required = true) String code
    ) {
        try {
            if (userId != null) {
                userService.adminDeleteUserByUserId(userId, code);
                return new BaseResponseBody(CommonErrorCode.SUCCESS);
            } else if (email != null) {
                userService.adminDeleteUserByEmail(email, code);
                return new BaseResponseBody(CommonErrorCode.SUCCESS);
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
        } catch (AppBusinessException e) {
            return new ExceptionResponseBody(e.getHttpStatus(), e.getCode(), e.getExceptionType(), e.getMessage());
        }
    }
}
