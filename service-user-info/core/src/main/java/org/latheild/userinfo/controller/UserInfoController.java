package org.latheild.userinfo.controller;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.userinfo.api.dto.UserInfoDTO;
import org.latheild.userinfo.domain.UserInfo;
import org.latheild.userinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.latheild.apiutils.URLRequestSetting.PRODUCE_JSON;
import static org.latheild.userinfo.api.UserInfoURL.GET_USER_INFOS_URL;
import static org.latheild.userinfo.api.UserInfoURL.USER_INFO_CREATE_URL;

@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping(value = USER_INFO_CREATE_URL, method = RequestMethod.POST, produces = PRODUCE_JSON)
    @ResponseBody
    public UserInfoDTO register(
            @RequestBody RegisterDTO registerDTO
    ) {
        UserInfo userInfo = userInfoService.register(registerDTO);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setAddress(userInfo.getAddress());
        userInfoDTO.setAvatar(userInfo.getAvatar());
        userInfoDTO.setGender(userInfo.getGender());
        userInfoDTO.setJob(userInfo.getJob());
        userInfoDTO.setName(userInfo.getName());
        userInfoDTO.setPhoneNumber(userInfo.getPhoneNumber());
        userInfoDTO.setWebsite(userInfo.getWebsite());
        return userInfoDTO;
    }

    @RequestMapping(value = GET_USER_INFOS_URL, method = RequestMethod.GET, produces = PRODUCE_JSON)
    @ResponseBody
    public ArrayList<UserInfo> listUsers() {
        return userInfoService.listUsers();
    }
}
