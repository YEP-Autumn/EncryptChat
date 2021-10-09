package com.laplace.client;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.laplace.adapter.ReAdapter;
import com.laplace.encryptUtils.AHelper;
import com.laplace.intermediator.Chat;
import com.laplace.intermediator.M;
import com.laplace.intermediator.Signalman;

import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebSocketClient extends org.java_websocket.client.WebSocketClient {

    private String TAG = "YEP";
    private Gson gson = new Gson();
    private ConnectUtils connectUtils;
    private List<M> messages = new ArrayList<>();
    private String secWebSocketKey;

    public WebSocketClient(URI serverUri) {
        super(serverUri);
    }

    public WebSocketClient(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    public WebSocketClient(URI serverUri, Map<String, String> httpHeaders, ConnectUtils connectUtils) {
        super(serverUri, httpHeaders);
        this.connectUtils = connectUtils;

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
        if (signalman.MODE == null) {
            return;
        }
        Log.e(TAG, "signalman.MODE: " + signalman.MODE);
        if ("WELCOME".equals(signalman.MODE)) {
            Message message = new Message();
            message.what = 0x000;
            messages.add(new M(connectUtils.toStr(new Chat("欢迎进入", false)),false));
            if (signalman.messages.size() != 0) {
                messages.addAll(connectUtils.toStrList(signalman.messages));
            } else {
                messages.add(new M(connectUtils.toStr(new Chat("暂无最新好友信息", false)),false));
            }
            message.obj = messages;
            connectUtils.handler.sendMessage(message);
            return;
        }
        if ("COMMON".equals(signalman.MODE)) {
            return;
        }
        if ("SIGN".equals(signalman.MODE)) {
            messages.add(new M(connectUtils.toStr(signalman.msg),false));
            Message message = getMessage(messages);
            connectUtils.handler.sendMessage(message);
            return;
        }
        if ("CLOSE".equals(signalman.MODE)) {
            return;
        }

        if ("RECEIVED".equals(signalman.MODE)) {
            connectUtils.handler.sendMessage(getMessage(0x222));
            return;
        }

        if ("ONLINE".equals(signalman.MODE)) {
            connectUtils.handler.sendMessage(getMessage(0x333));
            return;
        }
        if ("OFFLINE".equals(signalman.MODE)) {
            connectUtils.handler.sendMessage(getMessage(0x444));
            return;
        }


    }


    @NonNull
    private Message getMessage(Object ogj) {
        Message message = getMessage();
        message.obj = ogj;
        return message;
    }

    private Message getMessage() {
        Message message = new Message();
        message.what = 0x111;
        return message;
    }

    private Message getMessage(int what) {
        Message message = getMessage();
        message.what = what;
        return message;
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }


    @Override
    public void onError(Exception e) {

    }
}