package org.latheild.userinfo.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.user.api.dto.RegisterDTO;
import org.latheild.userinfo.api.UserInfoErrorCode;
import org.latheild.userinfo.api.dto.UserInfoDTO;
import org.latheild.userinfo.api.util.UserInfoDTOCreator;
import org.latheild.userinfo.constant.DAOQueryMode;
import org.latheild.userinfo.dao.UserInfoRepository;
import org.latheild.userinfo.domain.UserInfo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_CODE;
import static org.latheild.common.constant.RabbitMQQueue.USER_INFO_QUEUE;

@Service
@RabbitListener(queues = USER_INFO_QUEUE)
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    private boolean isUserInfoCreated(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                if (userInfoRepository.countById(target) > 0) {
                    return true;
                } else {
                    return false;
                }
            case QUERY_BY_USER_ID:
                if (userInfoRepository.countByUserId(target) > 0) {
                    return true;
                } else {
                    return false;
                }
            case QUERY_BY_NAME:
                if (userInfoRepository.countByName(target) > 0) {
                    return true;
                } else {
                    return false;
                }
            default:
                throw new AppBusinessException(
                        CommonErrorCode.INTERNAL_ERROR
                );
        }
    }

    private UserInfoDTO convertFromUserInfoToUserInfoDTO(UserInfo userInfo) {
        UserInfoDTO userInfoDTO = UserInfoDTOCreator.newInstance(
                userInfo.getUserId(),
                userInfo.getName(),
                userInfo.getGender(),
                userInfo.getAddress(),
                userInfo.getWebsite(),
                userInfo.getPhoneNumber(),
                userInfo.getJob(),
                userInfo.getAvatar()
        );
        return userInfoDTO;
    }

    private ArrayList<UserInfoDTO> convertFromUserInfosToUserInfoDTOs(ArrayList<UserInfo> userInfos) {
        ArrayList<UserInfoDTO> userInfoDTOs = new ArrayList<UserInfoDTO>();
        for (UserInfo userInfo : userInfos) {
            userInfoDTOs.add(convertFromUserInfoToUserInfoDTO(userInfo));
        }
        return userInfoDTOs;
    }

    private UserInfo convertFromRegisterDTOToUserInfoDTO(RegisterDTO registerDTO) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(registerDTO.getUserId());
        userInfo.setName(registerDTO.getName());
        userInfo.setGender(registerDTO.getGender());
        userInfo.setAddress(registerDTO.getAddress());
        userInfo.setWebsite(registerDTO.getWebsite());
        userInfo.setPhoneNumber(registerDTO.getPhoneNumber());
        userInfo.setJob(registerDTO.getJob());
        userInfo.setAvatar(registerDTO.getAvatar());
        return userInfo;
    }

    @RabbitHandler
    public void createUserInfo(RegisterDTO registerDTO) {
        if (!isUserInfoCreated(DAOQueryMode.QUERY_BY_USER_ID, registerDTO.getUserId())) {
            UserInfo userInfo = convertFromRegisterDTOToUserInfoDTO(registerDTO);
            userInfoRepository.save(userInfo);
        } else {
            throw new AppBusinessException(
                    UserInfoErrorCode.UserInfoExist,
                    String.format("User info for user %s has already been created.", registerDTO.getUserId())
            );
        }
    }

    @Override
    public UserInfoDTO register(RegisterDTO registerDTO) {
        if (!isUserInfoCreated(DAOQueryMode.QUERY_BY_USER_ID, registerDTO.getUserId())) {
            UserInfo userInfo = convertFromRegisterDTOToUserInfoDTO(registerDTO);
            UserInfoDTO userInfoDTO = convertFromUserInfoToUserInfoDTO(userInfo);
            userInfoRepository.save(userInfo);
            return userInfoDTO;
        } else {
            throw new AppBusinessException(
                    UserInfoErrorCode.UserInfoExist,
                    String.format("User info for user %s has already been created", registerDTO.getUserId())
            );
        }
    }

    @Override
    public UserInfoDTO updateUserInfo(UserInfoDTO userInfoDTO) {
        if (isUserInfoCreated(DAOQueryMode.QUERY_BY_USER_ID, userInfoDTO.getUserId())) {
            UserInfo userInfo = userInfoRepository.findByUserId(userInfoDTO.getUserId());
            userInfo.setName(userInfoDTO.getName());
            userInfo.setGender(userInfoDTO.getGender());
            userInfo.setAddress(userInfoDTO.getAddress());
            userInfo.setWebsite(userInfoDTO.getWebsite());
            userInfo.setPhoneNumber(userInfoDTO.getPhoneNumber());
            userInfo.setJob(userInfoDTO.getJob());
            userInfo.setAvatar(userInfoDTO.getAvatar());
            userInfoRepository.save(userInfo);
            return userInfoDTO;
        } else {
            throw new AppBusinessException(
                    UserInfoErrorCode.UserInfoNotExist,
                    String.format("User info for user %s has not been created", userInfoDTO.getUserId())
            );
        }
    }

    @Override
    public UserInfoDTO getUserInfoByUserId(String userId) {
        if (isUserInfoCreated(DAOQueryMode.QUERY_BY_USER_ID, userId)) {
            return convertFromUserInfoToUserInfoDTO(userInfoRepository.findByUserId(userId));
        } else {
            throw new AppBusinessException(
                    UserInfoErrorCode.UserInfoNotExist,
                    String.format("User info for user %s has not been created", userId)
            );
        }
    }

    @Override
    public ArrayList<UserInfoDTO> getUserInfosByName(String name) {
        if (isUserInfoCreated(DAOQueryMode.QUERY_BY_NAME, name)) {
            return convertFromUserInfosToUserInfoDTOs(userInfoRepository.findAllByName(name));
        } else {
            throw new AppBusinessException(
                    UserInfoErrorCode.UserInfoNotExist,
                    String.format("User info with name %s has not been created", name)
            );
        }
    }

    @Override
    public ArrayList<UserInfoDTO> getAllUserInfos() {
        if (userInfoRepository.count() > 0) {
            return convertFromUserInfosToUserInfoDTOs(userInfoRepository.findAll());
        } else {
            throw new AppBusinessException(
                    UserInfoErrorCode.UserInfoNotExist,
                    String.format("No user info has been created")
            );
        }
    }

    @Override
    public void resetUserInfoById(String userId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isUserInfoCreated(DAOQueryMode.QUERY_BY_USER_ID, userId)) {
                UserInfo resetUserInfo = new UserInfo();
                resetUserInfo.setId(userInfoRepository.findByUserId(userId).getId());
                resetUserInfo.setUserId(userId);
                userInfoRepository.save(resetUserInfo);
            } else {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(userId);
                userInfoRepository.save(userInfo);
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void deleteAllUserInfos(String code) {
        if (code.equals(ADMIN_CODE)) {
            userInfoRepository.deleteAll();
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void deleteUserInfoByUserId(String userId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isUserInfoCreated(DAOQueryMode.QUERY_BY_USER_ID, userId)) {
                userInfoRepository.deleteByUserId(userId);
            } else {
                throw new AppBusinessException(
                        UserInfoErrorCode.UserInfoNotExist,
                        String.format("No user info has been created")
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }
}
