package com.laplace.encryptchat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.laplace.adapter.ReAdapter;
import com.laplace.client.WebSocketClient;
import com.laplace.encryptUtils.AHelper;
import com.laplace.bean.utilsbean.M;
import com.laplace.encryptchat.manager.SocketManager;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "YEP";
    private ReAdapter reAdapter = new ReAdapter();
    private TextView statusText;
    private ImageView statusImg;
    private EditText editText;
    private TextView friendIdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        editText = findViewById(R.id.msg);
        statusText = findViewById(R.id.status_text);
        statusImg = findViewById(R.id.status_img);
        friendIdText = findViewById(R.id.friend_id_text);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String friendId = intent.getStringExtra("friendId");
        HashMap<String, String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("friendId", friendId);
        friendIdText.setText(friendId);

        // 为RecyclerView设置Adapter
        RecyclerView recyclerView = findViewById(R.id.msg_item);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getLayoutInflater().getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(reAdapter);

        // 用户通信的handler
        Handler handler = new Handler(Looper.myLooper(), message -> {
            switch (message.what) {
                case 0x000:
                    // WELCOME
                    reAdapter.setMessage((List<M>) message.obj);
                    reAdapter.notifyDataSetChanged();
                    Log.e(TAG, "handleMessage: ");
                    break;
                case 0x111:
                    // SIGN
                    reAdapter.setMessage((List<M>) message.obj);
                    reAdapter.notifyDataSetChanged();
                    break;
                case 0x222:
                    // RECEIVED
                    Toast.makeText(getLayoutInflater().getContext(), "好友未上线，数据保存到数据库中！", Toast.LENGTH_SHORT).show();
                    break;
                case 0x333:
                    // ONLINE
                    Toast.makeText(getLayoutInflater().getContext(), "好友在线", Toast.LENGTH_SHORT).show();
                    statusText.setText("在线");
                    Picasso.get().load(R.drawable.online).into(statusImg);
                    break;
                case 0x444:
                    // OFFLINE
                    Toast.makeText(getLayoutInflater().getContext(), "好友离线", Toast.LENGTH_SHORT).show();
                    statusText.setText("离线");
                    Picasso.get().load(R.drawable.offline).into(statusImg);
                    break;
                case 0x555:
                    List<M> msg = reAdapter.getMessage();
                    msg.add(new M((String) message.obj, true));
                    reAdapter.setMessage(msg);
                    reAdapter.notifyDataSetChanged();
                    break;
                case 0x666:
                    break;
            }
            return false;
        });
        SocketManager socketManager = new SocketManager(handler);
        socketManager.setKey(intent.getStringExtra("key"));
        try {
            map.put("uri","ws://127.0.0.1:8083");
            socketManager.start(map);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        // 设置点击事件
        findViewById(R.id.send).setOnClickListener(view -> {
            Editable edText = editText.getText();
            String text = edText.toString();
            if ("".equals(text)) return;
            socketManager.send(text);
            edText.clear();
        });
    }

    /**
     * 隐藏输入法
     */
    private void hideTypeWriting() {
        try {
            InputMethodManager im = (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}