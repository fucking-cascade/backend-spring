package org.latheild.user.controller;

import org.latheild.user.api.dto.RegisterDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.latheild.user.api.UserUrl.USER_REGISTER_URL;

@Controller
public class UserController {
    @RequestMapping(value = USER_REGISTER_URL, method = RequestMethod.POST)
    public Object register(
            @RequestBody RegisterDTO registerDTO
    ) {

    }
}
