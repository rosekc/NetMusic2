package com.android.netmusic.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.netmusic.R;
import com.android.netmusic.utils.Util;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

public class LoginAndRegisterActivity extends AppCompatActivity {
    public static LinearLayout viewNoLogin;
    //   public static LinearLayout viewLogin;
    //   public static LinearLayout viewLogout;
    private ImageView imgUserHead;
    private TextView tvUserName;
    // private ImageButton igbSign;
    private Switch switchLockFunction;
    private LinearLayout layoutShutdown;
    private Intent intent;
    public static SharedPreferences share;
    private SharedPreferences.Editor shareEditor;
    public static Tencent mTencent;
    private UserInfo mInfo;
    private LinearLayout linear_sendM;

    private ImageButton lqmQQLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);
        //imgUserHead //= (ImageView) view.findViewById(R.id.info_img_head);
        //tvUserName //= (TextView) view.findViewById(R.id.info_tv_name);
        // igbSign = (ImageButton) view.findViewById(R.id.info_igb_sign);

        mTencent = Tencent.createInstance("222222", this.getApplicationContext());
        lqmQQLogin = (ImageButton) findViewById(R.id.lqm_igb_qq_login);

        CustomInfoClickListener cl = new CustomInfoClickListener();
        lqmQQLogin.setOnClickListener(cl);
        //  viewNoLogin.setOnClickListener(cl);
//        viewLogin.setOnClickListener(cl);
//        viewLogout.setOnClickListener(cl);
//        viewLogout.setVisibility(View.GONE);


        share = this.getSharedPreferences("settingsInfo", Context.MODE_PRIVATE);
        shareEditor = share.edit();
    }


    class CustomInfoClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//                case R.id.info_layout_nologin:
////                    Intent intent = new Intent(getActivity(), LoginActivity.class);
////                    startActivity(intent);
//                    qqLogin();
//                    getUserInfo();
//                    break;
                case R.id.lqm_igb_qq_login:
                    qqLogin();
                    getUserInfo();
                    break;
//                case R.id.info_igb_sign:
//
//                    break;
//                case R.id.info_layout_logout:
//                    mTencent.logout(getActivity());
//                    updateUserInfo();
//                    viewLogout.setVisibility(View.GONE);
//                    viewNoLogin.setVisibility(View.VISIBLE);
//                    viewLogout.setVisibility(View.GONE);
//                    break;

            }
        }
    }


    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    //User.isLogin = true;
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread() {

                        @Override
                        public void run() {
                            JSONObject json = (JSONObject) response;
                            if (json.has("figureurl")) {
                                Bitmap bitmap = null;
                                try {
                                    //bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                                    // User.userHeadURL = json.getString("figureurl_qq_2");
                                    String userHeadURL = json.getString("figureurl_qq_2");
                                } catch (JSONException e) {

                                }
                                Message msg = new Message();
                                msg.obj = bitmap;
                                msg.what = 1;
                                mHandler.sendMessage(msg);
                            }
                        }

                    }.start();
                }

                @Override
                public void onCancel() {

                }
            };
            mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);

        } else {
//            tvUserName.setText("");
//            tvUserName.setVisibility(View.GONE);
//            imgUserHead.setVisibility(View.GONE);
        }
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
//                    try {
//                       // User.userName = response.getString("nickname");
//                        tvUserName.setVisibility(View.VISIBLE);
//                       // tvUserName.setText(User.userName);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                }
            } else if (msg.what == 1) {
                Bitmap bitmap = (Bitmap) msg.obj;
//                imgUserHead.setImageBitmap(bitmap);
//                imgUserHead.setVisibility(View.VISIBLE);
            }
        }

    };

    public static void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
//                viewLogin.setVisibility(View.VISIBLE);
//                viewNoLogin.setVisibility(View.GONE);
//                viewLogout.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            initOpenidAndToken(values);
            updateUserInfo();
            //updateLoginButton();
        }
    };

    public class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                Util.showResultDialog(getApplicationContext(), "返回为空", "登录失败");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                Util.showResultDialog(getApplicationContext(), "返回为空", "登录失败");
                return;
            }

            // Util.showResultDialog(getApplicationContext(), response.toString(), "登录成功");
            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

//            try {
//                User.userAccount = ((JSONObject) response).getString("openid");
//                String openid = User.userAccount;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            // 有奖分享处理
            // handlePrizeShare();
            doComplete((JSONObject) response);
        }

        protected void doComplete(JSONObject values) {

        }

        @Override
        public void onError(UiError e) {
            // Util.toastMessage(, "onError: " + e.errorDetail);
            //Util.dismissDialog();
        }

        @Override
        public void onCancel() {
            //Util.toastMessage(getApplicationContext(), "onCancel: ");
            //  Util.dismissDialog();
//            if (isServerSideLogin) {
//                isServerSideLogin = false;
//            }
        }
    }


    public void qqLogin() {

        //if (!mTencent.isSessionValid()) {
        mTencent.login(this, "all", loginListener);

        // }
    }

    public void getUserInfo() {
        IRequestListener mListener = new IRequestListener() {
            @Override
            public void onComplete(JSONObject jsonObject) {
                //  IToast.showToast(getActivity().getApplicationContext(), jsonObject.toString(), 10);
            }

            @Override
            public void onIOException(IOException e) {

            }

            @Override
            public void onMalformedURLException(MalformedURLException e) {

            }

            @Override
            public void onJSONException(JSONException e) {

            }

            @Override
            public void onConnectTimeoutException(ConnectTimeoutException e) {

            }

            @Override
            public void onSocketTimeoutException(SocketTimeoutException e) {

            }

            @Override
            public void onNetworkUnavailableException(HttpUtils.NetworkUnavailableException e) {

            }

            @Override
            public void onHttpStatusException(HttpUtils.HttpStatusException e) {

            }

            @Override
            public void onUnknowException(Exception e) {

            }
        };
        mTencent.requestAsync(Constants.GRAPH_BASE, null,
                Constants.HTTP_GET, mListener, null);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN) {
            Tencent.onActivityResultData(requestCode, resultCode, data, loginListener);
        }

    }
}
