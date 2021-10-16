package com.laplace.client;


import com.laplace.client.manager.MessageManager;

import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;

public class WebSocketClient extends org.java_websocket.client.WebSocketClient {

    private MessageManager manager;

    public void setManager(MessageManager manager) {
        this.manager = manager;
    }

    public WebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void connect() {
        if (manager == null) {
            return;
        }
        super.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {

    }

    @Override
    public void onMessage(String message) {
        manager.receive(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        manager.analysisClose(code, reason, remote);
    }

    @Override
    public void onError(Exception ex) {
        manager.dealWithError(ex);

    }

    @Override
    public void send(String text) {
        text = manager.sign(text);
        super.send(text);
    }

    //    @Override
//    public void onMessage(String s) {
//        Signalman signalman = gson.fromJson(s, Signalman.class);
//        if (signalman.MODE == null) {
//            return;
//        }
//        Log.e(TAG, "signalman.MODE: " + signalman.MODE);
//        if ("WELCOME".equals(signalman.MODE)) {
//            Message message = new Message();
//            message.what = 0x000;
//            messages.add(new M(connectUtils.toStr(new Chat("欢迎进入", false)),false));
//            if (signalman.messages.size() != 0) {
//                messages.addAll(connectUtils.toStrList(signalman.messages));
//            } else {
//                messages.add(new M(connectUtils.toStr(new Chat("暂无最新好友信息", false)),false));
//            }
//            message.obj = messages;
//            connectUtils.handler.sendMessage(message);
//            return;
//        }
//        if ("COMMON".equals(signalman.MODE)) {
//            return;
//        }
//        if ("SIGN".equals(signalman.MODE)) {
//            messages.add(new M(connectUtils.toStr(signalman.msg),false));
//            Message message = getMessage(messages);
//            connectUtils.handler.sendMessage(message);
//            return;
//        }
//        if ("CLOSE".equals(signalman.MODE)) {
//            return;
//        }
//
//        if ("RECEIVED".equals(signalman.MODE)) {
//            connectUtils.handler.sendMessage(getMessage(0x222));
//            return;
//        }
//
//        if ("ONLINE".equals(signalman.MODE)) {
//            connectUtils.handler.sendMessage(getMessage(0x333));
//            return;
//        }
//        if ("OFFLINE".equals(signalman.MODE)) {
//            connectUtils.handler.sendMessage(getMessage(0x444));
//            return;
//        }
//
//    }


}