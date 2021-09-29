package com.laplace.encryptchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.laplace.adapter.ReAdapter;
import com.laplace.client.WebSocketClient;
import com.laplace.encryptUtils.AHelper;
import com.laplace.intermediator.Chat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "YEP";
    WebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(new Chat("聊天室"));
        ReAdapter adapter = new ReAdapter(chats);

        Intent intent = getIntent();
        long time = intent.getLongExtra("time", 0L);

        String userId = AHelper.toContent(String.valueOf(time), intent.getStringExtra("userId"));
        String friendId = AHelper.toContent(String.valueOf(time), intent.getStringExtra("friendId"));
        adapter.key = AHelper.toContent(String.valueOf(time), intent.getStringExtra("key"));
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("friendId", friendId);
        try {
            webSocketClient = new WebSocketClient(new URI("ws://81.68.81.151:8788"), map);
            webSocketClient.addAdapter(adapter);
            webSocketClient.connect();
            Log.e(TAG, "webSocket");
        } catch (URISyntaxException e) {
            Log.e(TAG, "fail");
            e.printStackTrace();
        }
        RecyclerView recyclerView = findViewById(R.id.msg_item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getLayoutInflater().getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        webSocketClient.close();
    }
}