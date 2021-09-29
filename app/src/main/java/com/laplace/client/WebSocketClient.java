package com.laplace.client;


import android.util.Log;

import com.google.gson.Gson;
import com.laplace.adapter.ReAdapter;
import com.laplace.encryptUtils.AHelper;
import com.laplace.intermediator.Chat;
import com.laplace.intermediator.Signalman;

import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebSocketClient extends org.java_websocket.client.WebSocketClient {

    private String TAG = "YEP";
    Gson gson = new Gson();
    private ReAdapter adapter;

    List<Chat> messageTotal = new ArrayList<>();
    private String secWebSocketKey;

    public WebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public WebSocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    public WebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    public WebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders) {
        super(serverUri, protocolDraft, httpHeaders);
    }

    public WebSocketClient(URI serverUri, Draft protocolDraft, Map<String, String> httpHeaders, int connectTimeout) {
        super(serverUri, protocolDraft, httpHeaders, connectTimeout);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
    }


    @Override
    public void onMessage(String s) {
        Signalman signalman = gson.fromJson(s, Signalman.class);
        Log.e(TAG, "signalman: " + signalman);
        if ("WELCOME".equals(signalman.MODE)) {
            Log.e(TAG, "WELCOME模式");
            secWebSocketKey = signalman.secWebSocketKey;
//            System.out.println("欢迎进入");
            messageTotal.add(new Chat("欢迎进入"));
            if (signalman.messages.size() == 0) {
                messageTotal.add(new Chat("无最新好友消息"));
                adapter.setMessage(messageTotal);
                adapter.notifyDataSetChanged();
                return;
            }

            for (Chat message : signalman.messages) {
                messageTotal.add(new Chat(AHelper.toContent(adapter.key, message.getMsg())));
            }
            adapter.setMessage(messageTotal);
            adapter.notifyDataSetChanged();
            return;
        }
        if ("COMMON".equals(signalman.MODE)) {
            Log.e(TAG, "COMMON模式");
            return;
        }
        if ("SIGN".equals(signalman.MODE)) {
            Log.e(TAG, "SIGN模式");
            String msg = signalman.msg == null ? "" : signalman.msg;

            try {
                messageTotal.add(new Chat(AHelper.toContent(adapter.key, msg)));
                adapter.setMessage(messageTotal);
                adapter.notifyItemChanged(messageTotal.size());
            } catch (Exception e) {
                Log.e(TAG, "Exception ", e);
            }
            return;
        }
        if ("CLOSE".equals(signalman.MODE)) {
//            System.out.println("连接即将关闭");
            return;
        }

        if ("RECEIVED".equals(signalman.MODE)) {
            Log.e(TAG, "好友不在线，消息记录到数据库中");
        }

        if ("ONLINE".equals(signalman.MODE)) {
            Log.e(TAG, "好友上线了");
        }
        if ("OFFLINE".equals(signalman.MODE)) {
            Log.e(TAG, "好友离线了");
        }


    }


    @Override
    public void onClose(int i, String s, boolean b) {
    }


    @Override
    public void onError(Exception e) {

    }


    public void addAdapter(ReAdapter adapter) {
        this.adapter = adapter;
    }

    public void sendMsg(WebSocketClient webSocket, int userId, String msg, String key) {

        Signalman signalman = new Signalman("COMMON", userId, msg, secWebSocketKey);

        if (key != null) {
            signalman = new Signalman("SIGN", userId, AHelper.toSecret(key, msg), secWebSocketKey);
        }
        String s = gson.toJson(signalman);
        webSocket.send(s);
    }
}