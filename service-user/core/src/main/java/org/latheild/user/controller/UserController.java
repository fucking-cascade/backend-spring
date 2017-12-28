package org.latheild.user.controller;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.api.dto.UserProfileDTO;
import org.latheild.user.domain.User;
import org.latheild.user.service.UserService;
import org.latheild.userinfo.api.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.latheild.user.api.UserUrl.GET_USERS_URL;
import static org.latheild.user.api.UserUrl.USER_REGISTER_URL;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = USER_REGISTER_URL, method = RequestMethod.POST)
    @ResponseBody
    public UserProfileDTO register(
            @RequestBody RegisterDTO registerDTO
    ) {
        User user = userService.register(registerDTO);
        registerDTO.setUserId(user.getId());
        //UserInfoDTO userInfo = userService.createUserInfo(registerDTO);
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        userProfileDTO.setUserId(user.getId());
        userProfileDTO.setEmail(user.getEmail());
        userProfileDTO.setAddress(registerDTO.getAddress());
        userProfileDTO.setAvatar(registerDTO.getAvatar());
        userProfileDTO.setGender(registerDTO.getGender());
        userProfileDTO.setJob(registerDTO.getJob());
        userProfileDTO.setPhoneNumber(registerDTO.getPhoneNumber());
        userProfileDTO.setWebsite(registerDTO.getWebsite());
        userProfileDTO.setName(registerDTO.getName());
        return userProfileDTO;
    }

    @RequestMapping(value = GET_USERS_URL, method = RequestMethod.GET)
    @ResponseBody
    public ArrayList<User> listUsers() {
        return userService.listUsers();
    }
}
