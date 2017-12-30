package org.latheild.userinfo.service;

import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.userinfo.api.UserInfoErrorCode;
import org.latheild.userinfo.dao.UserInfoRepository;
import org.latheild.userinfo.domain.UserInfo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.common.constant.RabbitMQQueue.USER_INFO_QUEUE;

@Service
@RabbitListener(queues = USER_INFO_QUEUE)
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    private boolean isUserInfoCreated(String id) {
        if (userInfoRepository.countByUserId(id) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @RabbitHandler
    public void createUserInfo(RegisterDTO registerDTO) {
        if (!isUserInfoCreated(registerDTO.getUserId())) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(registerDTO.getUserId());
            userInfo.setName(registerDTO.getName());
            userInfo.setGender(registerDTO.getGender());
            userInfo.setAddress(registerDTO.getAddress());
            userInfo.setWebsite(registerDTO.getWebsite());
            userInfo.setPhoneNumber(registerDTO.getPhoneNumber());
            userInfo.setJob(registerDTO.getJob());
            userInfo.setAvatar(registerDTO.getAvatar());
            userInfoRepository.save(userInfo);
        } else {
            throw new AppBusinessException(
                    UserInfoErrorCode.UserInfoExist,
                    String.format("User info for user %s has already been created.", registerDTO.getUserId())
            );
        }
    }

    @Override
    public UserInfo register(RegisterDTO registerDTO) {
        if (!isUserInfoCreated(registerDTO.getUserId())) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(registerDTO.getUserId());
            userInfo.setName(registerDTO.getName());
            userInfo.setGender(registerDTO.getGender());
            userInfo.setAddress(registerDTO.getAddress());
            userInfo.setWebsite(registerDTO.getWebsite());
            userInfo.setPhoneNumber(registerDTO.getPhoneNumber());
            userInfo.setJob(registerDTO.getJob());
            userInfo.setAvatar(registerDTO.getAvatar());
            userInfoRepository.save(userInfo);
            return userInfo;
        } else {
            throw new AppBusinessException(
                    UserInfoErrorCode.UserInfoExist,
                    String.format("User info for user %s has already been created.", registerDTO.getUserId())
            );
        }
    }

    @Override
    public ArrayList<UserInfo> listUsers() {
        ArrayList<UserInfo> arrayList = userInfoRepository.findAll();
        userInfoRepository.deleteAll();
        return arrayList;
    }
}
