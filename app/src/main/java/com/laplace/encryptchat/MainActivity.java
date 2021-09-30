package com.laplace.encryptchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.laplace.encryptUtils.AHelper;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private boolean canExit = false;
    private String TAG = "YEP";

    private EditText userId;
    private EditText friendId;
    private EditText key;


    private int egg = 0;

    private static int count = (int) (Math.random() * 10) + 5;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if (message.what == 0x111) {
                canExit = false;
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userId = findViewById(R.id.user_id);
        friendId = findViewById(R.id.friend_id);
        key = findViewById(R.id.key);
        Button submit = findViewById(R.id.button_submit);
        submit.setOnClickListener(this);
        userId.getFocusable();
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && !canExit) {
            canExit = true;
            Toast.makeText(getLayoutInflater().getContext(), "再按一次返回键退出", Toast.LENGTH_SHORT).show();
            Message message = new Message();
            message.what = 0x111;
            handler.sendMessageDelayed(message, 3000);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_submit:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {
        if (egg > count) {
            angry();
            return;
        }
        if (!verifies()) return;
        Intent intent = new Intent(getLayoutInflater().getContext(), HomeActivity.class);
        long time = System.currentTimeMillis();
        intent.putExtra("userId", AHelper.toSecret(String.valueOf(time), userId.getText().toString()));
        intent.putExtra("friendId", AHelper.toSecret(String.valueOf(time), friendId.getText().toString()));
        intent.putExtra("key", AHelper.toSecret(String.valueOf(time), key.getText().toString()));
        intent.putExtra("time", time);
        startActivity(intent);
        Log.e(TAG, "submit key: " + key.getText().toString());
    }

    /**
     * 验证输入是否合法
     *
     * @return
     */
    private boolean verifies() {

        if ("".equals(userId.getText().toString()) || userId.getText() == null) {
            Toast.makeText(key.getContext(), "UserId不能为空", Toast.LENGTH_SHORT).show();
            egg++;
            return false;
        }
        if ("".equals(friendId.getText().toString()) || friendId.getText() == null) {
            Toast.makeText(friendId.getContext(), "FriendId不能为空", Toast.LENGTH_SHORT).show();
            egg++;
            return false;
        }
        if (userId.getText().toString().equals(friendId.getText().toString())) {
            Toast.makeText(getWindow().getContext(), "不能给自己发送消息", Toast.LENGTH_SHORT).show();
            friendId.getText().clear();
            egg++;
            return false;
        }
        egg = 0;
        return true;
    }


    /**
     * 请认真一点
     *
     * @return
     */
    private void angry() {
        Toast.makeText(getLayoutInflater().getContext(), "请认真一点!", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(getLayoutInflater().getContext());
        View inflate = getLayoutInflater().inflate(R.layout.image_view, null);
        builder.setView(inflate)
                .create()
                .show();
        int random = (int) (Math.random() * 10);
        random = random > 5 ? random - 5 : random;
        count = +random + 5;
        egg = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideTypeWriting();
        return super.onTouchEvent(event);
    }

    private void hideTypeWriting() {
        try {
            InputMethodManager im = (android.view.inputmethod.InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}