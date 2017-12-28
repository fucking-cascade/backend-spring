package org.latheild.userinfo.service;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.userinfo.domain.UserInfo;

import java.util.ArrayList;

public interface UserInfoService {
    public UserInfo register(RegisterDTO registerDTO);

    public ArrayList<UserInfo> listUsers();
}
