package com.laplace.client.manager;

import android.os.Handler;
import android.os.Message;
import android.security.keystore.UserNotAuthenticatedException;

import com.laplace.client.WebSocketClient;
import com.laplace.client.manager.MessageManager;
import com.laplace.encryptUtils.AHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;

public class SocketManager {

    private WebSocketClient webSocketClient;

    private MessageManager manager;

    public SocketManager(Handler handler) {
        manager = new MessageManager(handler);
    }

    public void start(HashMap<String, String> map) throws URISyntaxException {
        webSocketClient = new WebSocketClient(new URI(map.get("uri")), getSign(map));
        webSocketClient.setManager(manager);
        webSocketClient.connect();
    }

    private HashMap<String, String> getSign(HashMap<String, String> map) {
        HashMap<String, String> signMap = new HashMap<>();
        long userId = Long.parseLong(Objects.requireNonNull(map.get("userId")));
        long friendId = Long.parseLong(Objects.requireNonNull(map.get("friendId")));
        long time = System.currentTimeMillis();
        signMap.put("userId", map.get("userId"));
        signMap.put("friendId", map.get("friendId"));
        signMap.put("time", String.valueOf(time));
        String sign = String.valueOf((userId + friendId) * time);
        signMap.put("sign", AHelper.toSecret("YEP", sign));
        manager.setFriendId(map.get("friendId"));
        return signMap;
    }

    public void setKey(String key) {
        manager.setKey(key);
    }

    public void send(String text) {
        webSocketClient.send(text);
        manager.showMyMessage(text);
    }

    public void close() {
        if (webSocketClient.isClosed()) {
            return;
        }
        webSocketClient.close();

    }


    public void sendToken() {
        webSocketClient.sendToken();
    }
}
