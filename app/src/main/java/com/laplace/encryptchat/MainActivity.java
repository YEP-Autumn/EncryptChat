package com.laplace.encryptchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private boolean canExit = false;
    private String TAG = "YEP";

    private EditText userId;
    private EditText friendId;
    private EditText key;

    private int egg = 0;


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
        if (!verifies()) return;


    }

    /**
     * 验证输入是否合法
     *
     * @return
     */
    private boolean verifies() {
        if (egg > 10) ;
        if ("".equals(userId.getText()) || userId.getText() == null) {
            Toast.makeText(getLayoutInflater().getContext(), "UserId不能为空", Toast.LENGTH_SHORT).show();
            egg++;
            return false;
        }
        if ("".equals(friendId.getText()) || friendId.getText() == null) {
            Toast.makeText(getLayoutInflater().getContext(), "FriendId不能为空", Toast.LENGTH_SHORT).show();
            egg++;
            return false;
        }
        if (userId.getText() == friendId.getText()) {
            Toast.makeText(getLayoutInflater().getContext(), "不能给自己发送消息", Toast.LENGTH_SHORT).show();
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
    private boolean Angry() {
        return false;
    }
}