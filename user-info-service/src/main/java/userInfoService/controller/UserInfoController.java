package userInfoService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import userInfoService.domain.Gender;
import userInfoService.domain.UserInfo;
import userInfoService.domain.UserInfoWrapper;
import userInfoService.service.UserInfoService;

import java.util.Map;

@RestController
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/createUserInfo/v1")
    @ResponseBody
    public ResponseEntity<UserInfo> createUserInfoV1(
            @RequestBody UserInfoWrapper userInfoWrapper
    ) {
        UserInfo userInfo = new UserInfo(
                userInfoWrapper.getId(),
                userInfoWrapper.getName(),
                userInfoWrapper.getGender(),
                userInfoWrapper.getPhoneNumber(),
                userInfoWrapper.getAddress(),
                userInfoWrapper.getWebsite(),
                userInfoWrapper.getJob(),
                userInfoWrapper.getAvatar()
        );
        userInfoService.createUserInfo(userInfo);
        return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
    }

    @PostMapping("/createUserInfo/v2")
    @ResponseBody
    public ResponseEntity<UserInfo> createUserInfoV2(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "gender", required = true) Gender gender,
            @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "website", required = false) String website,
            @RequestParam(value = "job", required = false) String job,
            @RequestParam(value = "avatar", required = false) String avatar
    ) {
        UserInfo userInfo = new UserInfo(id, name, gender, phoneNumber, address, website, job, avatar);
        userInfoService.createUserInfo(userInfo);
        return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
    }

    @PostMapping("/createUserInfo/v3")
    public Object createUserInfoV3(
            @RequestBody UserInfoWrapper userInfoWrapper
    ) {
        UserInfo userInfo = new UserInfo(
                userInfoWrapper.getId(),
                userInfoWrapper.getName(),
                userInfoWrapper.getGender(),
                userInfoWrapper.getPhoneNumber(),
                userInfoWrapper.getAddress(),
                userInfoWrapper.getWebsite(),
                userInfoWrapper.getJob(),
                userInfoWrapper.getAvatar()
        );
        userInfoService.createUserInfo(userInfo);
        return userInfo;
    }

    @PostMapping("/updateUserInfo/v1")
    @ResponseBody
    public ResponseEntity<UserInfo> updateUserInfoV1(
            @RequestBody UserInfoWrapper userInfoWrapper
    ) {
        UserInfo userInfo = new UserInfo(
                userInfoWrapper.getId(),
                userInfoWrapper.getName(),
                userInfoWrapper.getGender(),
                userInfoWrapper.getPhoneNumber(),
                userInfoWrapper.getAddress(),
                userInfoWrapper.getWebsite(),
                userInfoWrapper.getJob(),
                userInfoWrapper.getAvatar()
        );
        userInfoService.updateUserInfo(userInfo);
        return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
    }

    @PostMapping("/updateUserInfo/v2")
    @ResponseBody
    public ResponseEntity<UserInfo> changeUserInfo(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "gender", required = true) Gender gender,
            @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "website", required = false) String website,
            @RequestParam(value = "job", required = false) String job,
            @RequestParam(value = "avatar", required = false) String avatar
    ) {
        UserInfo userInfo = new UserInfo(id, name, gender, phoneNumber, address, website, job, avatar);
        userInfoService.updateUserInfo(userInfo);
        return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
    }

    @GetMapping("/getUserInfo/{id}")
    @ResponseBody
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable int id) {
        return new ResponseEntity<UserInfo>(userInfoService.findUserInfo(id), HttpStatus.OK);
    }

    @GetMapping("/deleteUserInfo/test")
    public ResponseEntity<String> deleteUserInfo() {
        userInfoService.deleteUserInfo();
        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }
}
