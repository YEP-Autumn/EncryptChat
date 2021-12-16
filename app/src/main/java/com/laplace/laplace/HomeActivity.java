package com.laplace.laplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.laplace.adapter.ReAdapter;
import com.laplace.bean.utilsbean.M;
import com.laplace.client.manager.SocketManager;
import com.laplace.laplace.utils.ToastUtils;
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
    private SocketManager socketManager;

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
        @SuppressLint("NotifyDataSetChanged")
        Handler handler = new Handler(Looper.myLooper(), message -> {
            switch (message.what) {
                case 0x000:
                    // WELCOME
                    reAdapter.message.addAll((List<M>) message.obj);
                    reAdapter.notifyDataSetChanged();
                    break;
                case 0x111:
                    // 添加消息
                    reAdapter.message.add((M) message.obj);
                    reAdapter.notifyDataSetChanged();
                    break;
                case 0x222:
                    // RECEIVED
                    break;
                case 0x333:
                    // ONLINE
                    statusText.setText("在线");
                    Picasso.get().load(R.drawable.online).into(statusImg);
                    break;
                case 0x444:
                    // OFFLINE
                    statusText.setText("离线");
                    Picasso.get().load(R.drawable.offline).into(statusImg);
                    break;
                case 0x555:
                    // 用户名重复
//                    Intent intentBack = new Intent(Objects.requireNonNull(peekAvailableContext()).getApplicationContext(), MainActivity.class);
//                    intentBack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(intentBack);
                    this.finish();
                    ToastUtils.showToast(getLayoutInflater().getContext(), "用户名已被使用");
                    break;
                case 0x666:
                    // 向发送token表示自己在线
                    socketManager.sendToken();
                    break;
            }
            return false;
        });
        socketManager = new SocketManager(handler);
        socketManager.setKey(intent.getStringExtra("key"));
        try {
//            map.put("uri", "ws://192.168.0.73:8788");
            map.put("uri", "wss://lzstarrynight.cn/channel");

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideTypeWriting();
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        hideTypeWriting();
        return super.onKeyLongPress(keyCode, event);
    }

    /**
     * 隐藏输入法
     */
    private void hideTypeWriting() {
        try {
            InputMethodManager im = (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        socketManager.close();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        socketManager.close();
        super.onDestroy();
    }


}