package com.android.netmusic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.netmusic.R;
import com.android.netmusic.adapter.FriendsListAdapter;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText accountEditText;
    private EditText psdEditText;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        accountEditText = (EditText) findViewById(R.id.lqm_login_edit_account);
        psdEditText = (EditText) findViewById(R.id.lqm_login_edit_psd);
        loginBtn = (Button) findViewById(R.id.lqm_login_btn_login);

        loginBtn.setOnClickListener(this);
    }

    /**
     * 登录
     */
    private void login() {
        EMClient.getInstance().login(accountEditText.getText().toString(), psdEditText.getText().toString(), new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");

                EMClient.getInstance().contactManager().aysncGetAllContactsFromServer(new EMValueCallBack<List<String>>() {
                    @Override
                    public void onSuccess(List<String> strings) {
                        FriendsListAdapter.mUserAccountList = strings;
                        Intent intent = new Intent(LoginActivity.this, FriendsActivity.class);
                        startActivity(intent);
                    }
                    @Override
                    public void onError(int i, String s) {

                    }
                });


            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lqm_login_btn_login:
                login();
                break;
        }
    }
}
