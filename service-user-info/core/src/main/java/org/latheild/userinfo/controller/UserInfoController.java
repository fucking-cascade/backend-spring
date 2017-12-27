package org.latheild.userinfo.controller;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.userinfo.api.dto.UserInfoDTO;
import org.latheild.userinfo.domain.UserInfo;
import org.latheild.userinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserInfoController {
    @Autowired
    UserInfoService userinfoService;

    @RequestMapping
    @ResponseBody
    public UserInfoDTO register(
            @RequestBody RegisterDTO registerDTO
    ) {
        UserInfo userInfo = userinfoService.register(registerDTO);
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
}
