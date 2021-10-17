package com.laplace.client.manager;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.laplace.bean.utilsbean.M;
import com.laplace.bean.utilsbean.Signalman;
import com.laplace.bean.utilsbean.YEP;
import com.laplace.client.WebSocketClient;
import com.laplace.encryptUtils.AHelper;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MessageManager {

    private Handler handler;

    private long friendId;

    private String key;
    private String socketKey;

    public void setSocketKey(String socketKey) {
        this.socketKey = socketKey;
    }

    private Gson gson = new Gson();

    public void setKey(String key) {
        this.key = key;
    }

    public void setFriendId(String friendId) {
        this.friendId = Long.parseLong(friendId);
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
        signalman.setKey(socketKey);
        signalman.setTargetId(friendId);
        YEP autumn = new YEP();
        autumn.SIGNATURE = gson.toJson(signalman);
        return gson.toJson(autumn);
    }

    public void receive(String sign) {
        YEP autumn = gson.fromJson(sign, YEP.class);
        String mode = autumn.MODE;
        if (mode != null) {
            if ("WELCOME_TRUE".equals(mode)) {
                Signalman signalman = gson.fromJson(AHelper.toContent(socketKey, autumn.SIGNATURE), Signalman.class);
                List<M> reAdapterList = new ArrayList<>();
                reAdapterList.add(new M("欢迎上线!"));
                if (signalman.getMessages() == null) {
                    reAdapterList.add(new M("暂无好友消息"));
                } else {
                    reAdapterList.addAll(toListM(signalman.getMessages()));
                }
                handler.sendMessage(getMessage(0x000, reAdapterList));
                handler.sendMessage(getMessage(0x333));
            }
            if ("WELCOME_FALSE".equals(mode)) {
                List<M> reAdapterList = new ArrayList<>();
                reAdapterList.add(new M("欢迎上线!"));
                reAdapterList.add(new M("暂无好友消息"));
                handler.sendMessage(getMessage(0x000, reAdapterList));
                handler.sendMessage(getMessage(0x444));
            }
            if ("ONLINE".equals(mode)) {
                handler.sendMessage(getMessage(0x333));
            }
            if ("OFFLINE".equals(mode)) {
                handler.sendMessage(getMessage(0x444));
            }
            if ("RECEIVED".equals(mode)) {
                // 暂定
            }
            return;
        }
        if (autumn.SIGNATURE != null) {
            Signalman signalman = gson.fromJson(autumn.SIGNATURE, Signalman.class);
            if ("SIGN".equals(signalman.getMODE())) {
                String signText = signalman.getMessage();
                String message = AHelper.toContent(key, signText);
                handler.sendMessage(getMessage(0x111, new M(message)));
            }
            /**
             * 预留扩展功能
             */
        }


    }

    private List<M> toListM(List<String> messages) {
        List<M> mList = new ArrayList<>();
        messages.iterator().forEachRemaining(new Consumer<String>() {
            @Override
            public void accept(String s) {
                mList.add(new M(AHelper.toContent(key, s)));

            }
        });

        return mList;
    }

    public void analysisClose(int code, String reason, boolean remote) {
        if (code == 4040) {
            handler.sendMessage(getMessage(0x555));
        }
    }

    public void dealWithError(Exception ex) {
        Log.e(TAG, "dealWithError: ", ex);
    }

    private Message getMessage(int what, Object ogj) {
        Message message = getMessage(what);
        message.obj = ogj;
        return message;
    }

    private Message getMessage() {
        return new Message();
    }

    private Message getMessage(int what) {
        Message message = getMessage();
        message.what = what;
        return message;
    }

    public void showMyMessage(String message) {
        M m = new M(message);
        m.setMe(true);
        handler.sendMessage(getMessage(0x111, m));
    }

}
