package com.laplace.encryptchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.laplace.adapter.ReAdapter;
import com.laplace.client.WebSocketClient;
import com.laplace.encryptUtils.AHelper;
import com.laplace.intermediator.Chat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "YEP";
    private WebSocketClient webSocketClient;
    private ReAdapter reAdapter;
    Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Log.e(TAG, "message.what: " + message.what);
            switch (message.what) {
                case 0x000:
                    reAdapter.setMessage((List<Chat>) message.obj);
                    reAdapter.notifyDataSetChanged();
                    Log.e(TAG, "handleMessage: ");
                    break;
                case 0x111:
                    reAdapter.setMessage((List<Chat>) message.obj);
                    reAdapter.notifyDataSetChanged();
                    break;
                case 0x222:
                    Toast.makeText(getApplicationContext(), "好友未上线，数据保存到数据库中！", Toast.LENGTH_SHORT).show();
                    break;
                case 0x333:
                    break;
            }
            return false;
        }
    });
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Intent intent = getIntent();
        long time = intent.getLongExtra("time", 0L);
        String userId = AHelper.toContent(String.valueOf(time), intent.getStringExtra("userId"));
        String friendId = AHelper.toContent(String.valueOf(time), intent.getStringExtra("friendId"));
        String key = AHelper.toContent(String.valueOf(time), intent.getStringExtra("key"));

        setAdapter(key);
        startClient(userId, friendId);


        EditText editText = findViewById(R.id.msg);
        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webSocketClient.sendMsg(Integer.parseInt(userId), editText.getText().toString(), key);
            }
        });
    }

    private void startClient(String userId, String friendId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("friendId", friendId);
        try {
            webSocketClient = new WebSocketClient(new URI("ws://81.68.81.151:8788"), map, handler);
            webSocketClient.connect();
        } catch (URISyntaxException e) {
            Log.e(TAG, "fail");
            e.printStackTrace();
        }
    }

    private void setAdapter(String key) {
        reAdapter = new ReAdapter(key);
        RecyclerView recyclerView = findViewById(R.id.msg_item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getLayoutInflater().getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(reAdapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        webSocketClient.close();
    }
}