package com.laplace.bean.pojo;


import java.sql.Timestamp;

public class User {
    private long userId;
    private String mKey;
    private long friendId;
    private Timestamp connectTime;
    private boolean friendStatus;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getmKey() {
        return mKey;
    }

    public void setmKey(String mKey) {
        this.mKey = mKey;
    }

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public Timestamp getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Timestamp connectTime) {
        this.connectTime = connectTime;
    }

    public boolean isFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(boolean friendStatus) {
        this.friendStatus = friendStatus;
    }
}
