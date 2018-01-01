package org.latheild.user.api.dto;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String userId;

    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
