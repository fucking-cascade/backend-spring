package org.latheild.user.service;

import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.domain.User;

public interface UserService {
    public User register(RegisterDTO registerDTO);
}
