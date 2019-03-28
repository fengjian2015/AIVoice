package com.translation.model.entity;

import java.io.Serializable;

public class FriendInfo implements Serializable{

    private String friendId;
    private String nickname;
    private String username;

    public FriendInfo() {
    }

    public FriendInfo(String friendId, String nickname, String username) {
        this.friendId = friendId;
        this.nickname = nickname;
        this.username = username;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "FriendInfo{" +
                "friendId='" + friendId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
