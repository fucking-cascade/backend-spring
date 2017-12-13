package userInfoService.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class UserInfo {
    @Id
    @NotNull
    private final int id;

    @NotBlank
    private String name;

    @NotNull
    private Gender gender;

    private String address;

    private String website;

    @NotBlank
    private String phoneNumber;

    private String job;

    private String avatar;

    public UserInfo(int id, String name, Gender gender, String phoneNumber, String address, String website, String job, String avatar) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.website = website;
        this.job = job;
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJob() {
        return job;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }

    public void update(UserInfo userInfo) {
        this.name = userInfo.name;
        this.gender = userInfo.gender;
        this.phoneNumber = userInfo.phoneNumber;
        this.address = userInfo.address;
        this.website = userInfo.website;
        this.job = userInfo.job;
        this.avatar = userInfo.avatar;
    }
}
