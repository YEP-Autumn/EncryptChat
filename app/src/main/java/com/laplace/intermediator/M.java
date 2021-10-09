package com.laplace.intermediator;

public class M {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public M(String message) {
        this.message = message;
    }

    public M(String message, boolean isMe) {
        this.message = message;
        this.isMe = isMe;
    }

    private boolean isMe;
}
