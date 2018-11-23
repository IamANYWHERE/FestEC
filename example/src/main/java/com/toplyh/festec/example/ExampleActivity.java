package com.toplyh.festec.example;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.widget.Toast;

import com.toplyh.latte.annotations.AppRegisterGenerator;
import com.toplyh.latte.core.activities.ProxyActivity;
import com.toplyh.latte.core.app.Latte;
import com.toplyh.latte.core.delegates.LatteDelegate;
import com.toplyh.latte.ec.launcher.LauncherDelegate;
import com.toplyh.latte.ec.main.EcBottomDelegate;
import com.toplyh.latte.ec.sign.ISignListener;
import com.toplyh.latte.ec.sign.SignInDelegate;
import com.toplyh.latte.ui.launcher.ILauncherListener;

import java.lang.annotation.Annotation;

import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this,true);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        //Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        //Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }


    @Override
    public void onSigned() {
        //Toast.makeText(this, "启动结束，用户登录了", Toast.LENGTH_LONG).show();
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onNotSigned() {
        //Toast.makeText(this, "启动结束，用户没登录", Toast.LENGTH_LONG).show();
        getSupportDelegate().startWithPop(new SignInDelegate());
    }
}
