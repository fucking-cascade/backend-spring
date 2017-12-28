package org.latheild.user.service;

import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.user.api.UserErrorCode;
import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.user.client.UserInfoClient;
import org.latheild.user.dao.UserRepository;
import org.latheild.user.domain.User;
import org.latheild.userinfo.api.dto.UserInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoClient userInfoClient;

    @Autowired
    private UserRepository userRepository;

    public boolean isEmailUsed(String email) {
        if (userRepository.countByEmail(email) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        if (!isEmailUsed(registerDTO.getEmail())) {
            User user = new User();
            user.setEmail(registerDTO.getEmail());
            user.setPassword(registerDTO.getPassword());
            userRepository.save(user);
            return user;
        } else {
            throw new AppBusinessException(
                    UserErrorCode.EmailExist,
                    String.format("Email %s has already been used.", registerDTO.getEmail())
            );
        }
    }

    @Override
    public UserInfoDTO createUserInfo(RegisterDTO registerDTO) {
        return userInfoClient.register(registerDTO);
    }

    @Override
    public ArrayList<User> listUsers() {
        ArrayList<User> arrayList = userRepository.findAll();
        userRepository.deleteAll();
        return arrayList;
    }
}
