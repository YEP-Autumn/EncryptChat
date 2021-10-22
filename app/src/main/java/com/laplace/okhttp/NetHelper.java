package com.laplace.okhttp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.laplace.laplace.MainActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetHelper {

    private String TAG = "YEP";

    public static void isOnline(String userId) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://lzstarrynight.cn/isUsed")
//                .url("https://192.168.0.73:8088/isUsed")
                .addHeader("userId",userId)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("YEP", "onResponse: " + e.toString());
            }
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Log.e("YEP", "onResponse: " + response.toString());
            }
        });
    }
}
