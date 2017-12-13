package userService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import userService.domain.*;
import userService.service.UserService;

import javax.xml.ws.Response;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/createUser/v1")
    @ResponseBody
    public ResponseEntity<User> createUserV1(
            @RequestBody UserWrapper userWrapper
    ) {
        int id = userService.generateNewId();
        User user = new User(
                id,
                userWrapper.getEmail(),
                userWrapper.getPassword()
        );
        UserInfoWrapper userInfoWrapper = new UserInfoWrapper(
                id,
                userWrapper.getName(),
                userWrapper.getGender(),
                userWrapper.getPhoneNumber(),
                userWrapper.getAddress(),
                userWrapper.getWebsite(),
                userWrapper.getJob(),
                userWrapper.getAvatar()
        );
        userService.createUser(user, userInfoWrapper);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/createUser/v2")
    @ResponseBody
    public ResponseEntity<User> createUserV2(
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "gender", required = true) Gender gender,
            @RequestParam(value = "phoneNumber", required = true) String phoneNumber,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "website", required = false) String website,
            @RequestParam(value = "job", required = false) String job,
            @RequestParam(value = "avatar", required = false) String avatar
    ) {
        int id = userService.generateNewId();
        User user = new User(id, email, password);
        UserInfoWrapper userInfoWrapper = new UserInfoWrapper(id, name, gender, phoneNumber, address, website, job, avatar);
        userService.createUser(user, userInfoWrapper);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/updateUser/v1")
    @ResponseBody
    public ResponseEntity<User> updateUserV1(
            @RequestBody UserWrapper userWrapper
    ) {
        User user = new User(
                userWrapper.getId(),
                userWrapper.getEmail(),
                userWrapper.getPassword()
        );
        userService.updateUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/updateUser/v2")
    @ResponseBody
    public ResponseEntity<User> updateUserV2(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "password", required = true) String password
    ) {
        User user = new User(id, email, password);
        userService.updateUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/userLogin/v1")
    @ResponseBody
    public ResponseEntity<Boolean> userLoginV1(
            @RequestBody LoginWrapper loginWrapper
    ) {
        return new ResponseEntity<Boolean>(userService.checkLogin(loginWrapper.getId(), loginWrapper.getPassword()), HttpStatus.OK);
    }

    @PostMapping("/userLogin/v2")
    @ResponseBody
    public ResponseEntity<Boolean> userLoginV2(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "password", required = true) String password
    ) {
        return new ResponseEntity<Boolean>(userService.checkLogin(id, password), HttpStatus.OK);
    }

    @GetMapping("/getUserEmail/{id}")
    @ResponseBody
    public ResponseEntity<String> getUserEmail(
            @PathVariable int id
    ) {
        return new ResponseEntity<String>(userService.getUserEmailById(id), HttpStatus.OK);
    }

    @GetMapping("/deleteUser/test")
    @ResponseBody
    public ResponseEntity<String> deleteUser() {
        userService.deleteUser();
        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

    @PostMapping("/resetPassword/v1")
    @ResponseBody
    public ResponseEntity<String> resetPasswordV1(
            @RequestBody ResetPasswordWrapper resetPasswordWrapper
    ) {
        if (userService.checkLogin(resetPasswordWrapper.getId(), resetPasswordWrapper.getOldPassword()) == true) {
            userService.resetPassword(resetPasswordWrapper.getId(), resetPasswordWrapper.getNewPassword());
            return new ResponseEntity<String>("Password reset success", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Password reset failed", HttpStatus.OK);
        }
    }

    @PostMapping("/resetPassword/v2")
    @ResponseBody
    public ResponseEntity<String> resetPasswordV2(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "oldPassword", required = true) String oldPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword
    ) {
        if (userService.checkLogin(id, oldPassword) == true) {
            userService.resetPassword(id, newPassword);
            return new ResponseEntity<String>("Password reset success", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Password reset failed", HttpStatus.OK);
        }
    }

    @PostMapping("/resetEmail/v1")
    @ResponseBody
    public ResponseEntity<String> resetEmailV1(
            @RequestBody ResetEmailWrapper resetEmailWrapper
    ) {
        userService.resetEmail(resetEmailWrapper.getId(), resetEmailWrapper.getEmail());
        return new ResponseEntity<String>("Email reset success", HttpStatus.OK);
    }

    @PostMapping("/resetEmail/v2")
    @ResponseBody
    public ResponseEntity<String> resetEmailV2(
            @RequestParam(value = "id", required = true) int id,
            @RequestParam(value = "email", required = true) String email
    ) {
        userService.resetEmail(id, email);
        return new ResponseEntity<String>("Email reset success", HttpStatus.OK);
    }
}
