package org.latheild.user.controller;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.domain.User;
import org.latheild.user.service.UserService;
import org.latheild.userinfo.api.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.latheild.user.api.UserUrl.USER_REGISTER_URL;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = USER_REGISTER_URL, method = RequestMethod.POST)
    @ResponseBody
    public Object register(
            @RequestBody RegisterDTO registerDTO
    ) {
        User user = userService.register(registerDTO);
        registerDTO.setUserId(user.getId());
        UserInfoDTO userInfo = userService.createUserInfo(registerDTO);
        return userInfo;
    }
}
