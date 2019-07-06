package com.rainbow.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rainbow.R;
import com.rainbow.utils.UserUtils;
import com.rainbow.views.InputView;

public class LoginActivity extends BaseActivity {
    private InputView mInputPhone,mInputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView(){
        initNavBar(false,"登录",false);
        mInputPhone=fd(R.id.input_phone);
        mInputPassword=fd(R.id.input_password);
    }
    public void onRegisterClick(View v){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
    public void onCommitClick(View v){
        String phone = mInputPhone.getInputStr();
        String pass = mInputPassword.getInputStr();
        if(!UserUtils.validateLogin(this,phone,pass)){
            return;
        }
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
