package com.laplace.client.manager;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.laplace.bean.utilsbean.Signalman;
import com.laplace.bean.utilsbean.YEP;
import com.laplace.client.WebSocketClient;
import com.laplace.encryptUtils.AHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class MessageManager {

    private Handler handler;

    private String key;

    private Gson gson = new Gson();

    public void setKey(String key) {
        this.key = key;
    }

    public MessageManager(Handler handler) {
        this.handler = handler;
    }

    private static final String TAG = "YEP";

    /**
     * 加密文本
     *
     * @param text
     * @return
     */
    public String sign(String text) {
        String message = AHelper.toSecret(key, text);
        Signalman signalman = new Signalman("SIGN");
        signalman.setMessage(message);
        YEP autumn = new YEP();
        autumn.SIGNATURE = gson.toJson(signalman);
        return gson.toJson(autumn);
    }

    public void receive(String sign) {
        YEP autumn = gson.fromJson(sign, YEP.class);
        String mode = autumn.MODE;
        if (mode != null) {
            if (mode == "WELCOME_TRUE") {

            }
            if (mode == "WELCOME_FALSE") {

            }
            if (mode == "ONLINE") {

            }
            if (mode == "OFFLINE") {

            }
            if (mode == "RECEIVED") {

            }
            return;
        }
        Signalman signalman = gson.fromJson(autumn.SIGNATURE, Signalman.class);


    }

    public void analysisClose(int code, String reason, boolean remote) {

    }

    public void dealWithError(Exception ex) {
        Log.e(TAG, "dealWithError: ", ex);
    }

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
}
