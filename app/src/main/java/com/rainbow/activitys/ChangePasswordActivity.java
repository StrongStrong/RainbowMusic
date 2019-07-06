package com.rainbow.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.rainbow.R;
import com.rainbow.utils.UserUtils;
import com.rainbow.views.InputView;

public class ChangePasswordActivity extends BaseActivity {
    private InputView mOldPassword, mPassword, mPasswordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initView();
    }

    private void initView() {
        initNavBar(true,"修改密码",false);
        mOldPassword = fd(R.id.input_old_pass);
        mPassword = fd(R.id.input_new_pass);
        mPasswordConfirm = fd(R.id.input_confirm_new_pass);
    }
    public void onChangePasswordClick (View v) {
        String oldPassword = mOldPassword.getInputStr();
        String password = mPassword.getInputStr();
        String passwordConfirm = mPasswordConfirm.getInputStr();

        boolean result = UserUtils.changePassword(this, oldPassword, password, passwordConfirm);
        if (!result) {
            return;
        }

        UserUtils.logout(this);
    }
}
