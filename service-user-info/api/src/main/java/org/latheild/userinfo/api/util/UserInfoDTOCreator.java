package org.latheild.userinfo.api.util;

import org.latheild.userinfo.api.dto.UserInfoDTO;

public class UserInfoDTOCreator {
    public static UserInfoDTO newInstance(
            String userId,
            String name,
            Boolean gender,
            String address,
            String website,
            String phoneNumber,
            String job,
            String avatar,
            String birthday
    ) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();

        userInfoDTO.setUserId(userId);
        userInfoDTO.setName(name);
        userInfoDTO.setWebsite(website);
        userInfoDTO.setPhoneNumber(phoneNumber);
        userInfoDTO.setJob(job);
        userInfoDTO.setGender(gender);
        userInfoDTO.setAddress(address);
        userInfoDTO.setAvatar(avatar);
        userInfoDTO.setBirthday(birthday);

        return userInfoDTO;
    }
}
