package com.laplace.laplace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.laplace.okhttp.NetHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private boolean canExit = false;
    private String TAG = "YEP";

    private EditText userId;
    private EditText friendId;
    private EditText key;

    public static boolean isExit = true;

    private int egg = 0;

    private static int count = (int) (Math.random() * 10) + 5;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            switch (message.what) {
                case 0x111:
                    canExit = false;
                    break;
                case 0x222:
                    refreshIsExit();

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
        userId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)return;
                Log.e(TAG, "onFocusChange: " + view.getId() + "  " + b);
                Message message = new Message();
                message.what = 0x222;
                handler.sendMessage(message);
            }
        });

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
        intent.putExtra("userId", userId.getText().toString());
        intent.putExtra("friendId", friendId.getText().toString());
        intent.putExtra("key", key.getText().toString());
        startActivity(intent);
    }

    /**
     * 验证输入是否合法
     *
     * @return
     */
    private boolean verifies() {

        if (isExit) {
            Toast.makeText(userId.getContext(), "UserId已被占用", Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(userId.getText().toString()) || userId.getText() == null) {
            Toast.makeText(userId.getContext(), "UserId不能为空", Toast.LENGTH_SHORT).show();
            egg++;
            return false;
        }
        if ("0".equals(userId.getText().toString())) {
            Toast.makeText(userId.getContext(), "用户ID不能为0", Toast.LENGTH_SHORT).show();
            egg++;
            return false;
        }
        if ("".equals(friendId.getText().toString()) || friendId.getText() == null) {
            Toast.makeText(friendId.getContext(), "FriendId不能为空", Toast.LENGTH_SHORT).show();
            egg++;
            return false;
        }
        if ("0".equals(userId.getText().toString()) || "0".equals(friendId.getText().toString())) {
            Toast.makeText(userId.getContext(), "用户ID不能为0", Toast.LENGTH_SHORT).show();
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

    /**
     * 调用接口判断用户名是否已被占用
     */
    private void refreshIsExit() {
        String userIdNum = userId.getText().toString();
        if ("".equals(userIdNum) || userId.getText() == null) {
            return;
        }
        NetHelper.isOnline(userIdNum);
    }
}