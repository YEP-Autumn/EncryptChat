package com.laplace.intermediator;

import java.sql.Timestamp;

public class Chat {
    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public int getUserId() {
        return userId;
    }

    public Chat() {
    }

    public Chat(int friendId, int userId, String msg, Timestamp sendTime, boolean friendStatus) {
        this.friendId = friendId;
        this.userId = userId;
        this.msg = msg;
        this.sendTime = sendTime;
        this.friendStatus = friendStatus;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(boolean friendStatus) {
        this.friendStatus = friendStatus;
    }

    private int friendId;
    private int userId;
    private String msg;
    private Timestamp sendTime;
    private boolean friendStatus;
}
