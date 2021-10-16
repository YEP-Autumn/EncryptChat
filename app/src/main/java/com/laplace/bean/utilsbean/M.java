package com.laplace.bean.utilsbean;

public class M {

    private String message;

    private boolean isMe;

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


}
