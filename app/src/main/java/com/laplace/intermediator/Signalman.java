package com.laplace.intermediator;


import java.util.List;

public class Signalman {

    /**
     * 消息模式
     * WELCOME: 欢迎模式，用户第一次上线时发送
     * COMMON: 普通模式，给指定用户发送未经加密的消息
     * CLOSE: 通知用户连接即将关闭
     * ONLINE: 好友上线
     * RECEIVED: 用户不在线，记录消息
     * SIGN: 加密数据
     * OFFLINE: 用户下线
     */
    public String MODE;

    public Chat msg;

    public List<Chat> messages;

    public String secWebSocketKey;

    /**
     * 用户的登录名
     */
    public int userId;

    /**
     * 用户发送的消息
     */

    /**
     * 用户的Sec-WebSocket-Key，用于用户消息发送失败时的反馈
     */
    public String mKey;

    public Signalman(String MODE) {
        this.MODE = MODE;
    }

    /**
     * 标识消息来源
     */
    public final static String SOURCE = "SERVER";

    /**
     * 消息的发送方
     */
    public String who;

    public Signalman(String MODE, Chat msg, String secWebSocketKey) {
        this.MODE = MODE;
        this.msg = msg;
        this.secWebSocketKey = secWebSocketKey;
    }

    public Signalman(String MODE, List<Chat> messages, String secWebSocketKey) {
        this.MODE = MODE;
        this.messages = messages;
        this.secWebSocketKey = secWebSocketKey;
    }

    public Signalman(String MODE, Chat msg) {
        this.MODE = MODE;
        this.msg = msg;
    }


    public Signalman(String MODE, int userId, Chat msg, String mKey) {
        this.MODE = MODE;
        this.userId = userId;
        this.msg = msg;
        this.mKey = mKey;
    }

    public Signalman(String MODE, int userId, Chat msg) {
        this.MODE = MODE;
        this.userId = userId;
        this.msg = msg;
    }
}
