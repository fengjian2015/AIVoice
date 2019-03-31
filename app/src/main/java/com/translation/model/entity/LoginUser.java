package com.translation.model.entity;

import java.io.Serializable;

public class LoginUser implements Serializable{

    private String userId;
    private String username;
    private String nickName;
    private String phoneNum;
    private String email;
    private String password;

    public LoginUser() {
    }

    public LoginUser(String userId, String username, String nickName, String phoneNum, String email) {
        this.userId = userId;
        this.username = username;
        this.nickName = nickName;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public LoginUser(String username, String phoneNum, String email) {
        this.username = username;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", nickName='" + nickName + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
