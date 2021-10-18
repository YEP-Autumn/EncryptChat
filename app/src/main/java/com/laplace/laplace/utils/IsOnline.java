package com.laplace.laplace.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class IsOnline {

    public static String isOnline(long userId) {
//        if (userId == 0) return true;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Request request = new Request.Builder().url("https://81.68.81.151:9999/isUsed")
                .addHeader("userId", String.valueOf(userId))
                .build();
        try (Response response = builder.build().newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
