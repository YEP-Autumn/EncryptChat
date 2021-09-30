package com.laplace.encryptchat;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class OpenScreenActivity extends AppCompatActivity {
    private static final String TAG = "YEP";
    private ObjectAnimator objectAnimator;
    private boolean StIn = true;

    private Intent intent;

    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_screen);
        intent = new Intent(getLayoutInflater().getContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        objectAnimator = ObjectAnimator.ofFloat((ImageView) findViewById(R.id.image_view), "alpha", 0f, 1f)
                .setDuration(3000);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!flag) return;
                flag = false;
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
//        此方法会调用onDestroy
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!flag) return true;
        flag = false;
        Log.e(TAG, "onTouchEvent: " + event);
        startActivity(intent);
        objectAnimator.cancel();
        return super.onTouchEvent(event);
    }
}