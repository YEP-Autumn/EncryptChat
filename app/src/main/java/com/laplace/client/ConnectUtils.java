package com.laplace.client;


import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.laplace.encryptUtils.AHelper;
import com.laplace.intermediator.Chat;
import com.laplace.intermediator.M;
import com.laplace.intermediator.Signalman;

import java.util.ArrayList;
import java.util.List;

public class ConnectUtils {
    public Handler handler;
    private String key;
    private String TAG = "YEP";

    public ConnectUtils(Handler handler, String key) {
        this.handler = handler;
        this.key = key;
    }

    public String toStr(Chat chat) {
        if (chat.isSign()) {
            return AHelper.toContent(key, chat.getMsg());
        }
        return chat.getMsg();
    }

    public String toJson(int userId, String msg) {
        Chat s = new Chat(AHelper.toSecret(key, msg), true);
        Signalman signalman = new Signalman("SIGN", userId, s);
        return new Gson().toJson(signalman);
    }

    public List<M> toStrList(List<Chat> chats) {
        List<M> messages = new ArrayList<>();
        for (Chat chat : chats) {
            messages.add(new M(toStr(chat), false));
        }
        return messages;
    }
}
