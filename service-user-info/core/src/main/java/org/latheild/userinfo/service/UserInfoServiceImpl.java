package org.latheild.userinfo.service;

import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.userinfo.api.UserInfoErrorCode;
import org.latheild.userinfo.dao.UserInfoRepository;
import org.latheild.userinfo.domain.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoRepository userInfoRepository;

    private boolean isUserInfoCreated(Long id) {
        if (userInfoRepository.countByUserId(id) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserInfo register(RegisterDTO registerDTO) {
        if (isUserInfoCreated(registerDTO.getUserId())) {
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
}
