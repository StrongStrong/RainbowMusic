package com.rainbow.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rainbow.R;
import com.rainbow.utils.UserUtils;

import java.util.Timer;
import java.util.TimerTask;

public class WellcomeActivity extends BaseActivity {
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        init();
    }

    private void init() {
        final boolean isLogin = UserUtils.validateUserLogin(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isLogin) {
                    toMain();
                } else {
                    toLogin();
                }

            }
        },3000);
    }
    private void toMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void toLogin(){
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
