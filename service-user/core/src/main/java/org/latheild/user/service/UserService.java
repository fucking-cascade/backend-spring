package org.latheild.user.service;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.domain.User;
import org.latheild.userinfo.api.dto.UserInfoDTO;

public interface UserService {
    public User register(RegisterDTO registerDTO);

    public UserInfoDTO createUserInfo(RegisterDTO registerDTO);
}
