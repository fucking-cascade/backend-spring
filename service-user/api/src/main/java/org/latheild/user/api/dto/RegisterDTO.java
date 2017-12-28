package org.latheild.user.api.dto;

import org.latheild.common.constant.Gender;

import java.io.Serializable;

public class RegisterDTO implements Serializable {
    private String userId;
    private String email;
    private String password;
    private String name;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private String website;
    private String job;
    private String avatar;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }

    public String getJob() {
        return job;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", job='" + job + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
