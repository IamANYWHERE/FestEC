package com.toplyh.latte.ec.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.toplyh.latte.core.net.RestClient;
import com.toplyh.latte.core.net.RestCreator;
import com.toplyh.latte.core.net.callback.IFailure;
import com.toplyh.latte.core.net.callback.ISuccess;
import com.toplyh.latte.core.net.rx.RxRestClient;
import com.toplyh.latte.core.util.log.LatteLogger;
import com.toplyh.latte.core.weichat.LatteWeChat;
import com.toplyh.latte.core.weichat.callbacks.IWeChatSignInCallback;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInDelegate extends SignDelegate {

    @BindView(R2.id.edit_sign_in_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_sign_in_password)
    TextInputEditText mPassword = null;


    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){
        if (checkForm()) {
            RestClient.builder()
                    .url("data/user_profile.json")
                    .params("email",mEmail.getText().toString())
                    .params("password",mPassword.getText().toString())
                    .loader(getContext())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            LatteLogger.json("USER_PROFILE",response);
                            SignHandler.onSignUp(response,mISignListener);
                            Toast.makeText(getContext(), "登录通过", Toast.LENGTH_LONG).show();
                        }
                    })
                    .failure(new IFailure() {
                        @Override
                        public void onFailure() {
                            LatteLogger.json("USER_PROFILE","登陆失败呀");
                            Toast.makeText(getContext(), "登录失败", Toast.LENGTH_LONG).show();
                        }
                    })
                    .build()
                    .post();
        }
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    void onClickWeChat(){
        LatteWeChat.getInstance().onSignSuccess(new IWeChatSignInCallback() {
            @Override
            public void onSignInSuccess(String userInfo) {
                Toast.makeText(getContext(),userInfo,Toast.LENGTH_LONG).show();
            }
        }).signIn();
    }

    @OnClick(R2.id.tv_link_sign_in)
    void onClickLink(){
        start(new SignUpDelegate());
    }

    private boolean checkForm() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;
        //是否为空或者格式是否正确,android里提供了很多校验的方法
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
