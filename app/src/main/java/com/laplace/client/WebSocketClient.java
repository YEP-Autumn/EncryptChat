package com.laplace.client;


import com.laplace.client.manager.MessageManager;

import org.java_websocket.WebSocket;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.handshake.ClientHandshake;
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
    public void onWebsocketHandshakeSentAsClient(WebSocket conn, ClientHandshake request) throws InvalidDataException {
        super.onWebsocketHandshakeSentAsClient(conn, request);
        manager.setSocketKey(request.getFieldValue("Sec-WebSocket-Key"));
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
    public void sendToken() {
        send(manager.sign("","TOKEN"));
    }
}