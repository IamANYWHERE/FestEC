package com.toplyh.festec.example;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.widget.Toast;

import com.toplyh.latte.annotations.AppRegisterGenerator;
import com.toplyh.latte.core.activities.ProxyActivity;
import com.toplyh.latte.core.app.Latte;
import com.toplyh.latte.core.delegates.LatteDelegate;
import com.toplyh.latte.core.util.callback.CallbackManager;
import com.toplyh.latte.core.util.callback.CallbackType;
import com.toplyh.latte.core.util.callback.IGlobalCallback;
import com.toplyh.latte.core.util.log.LatteLogger;
import com.toplyh.latte.ec.launcher.LauncherDelegate;
import com.toplyh.latte.ec.main.EcBottomDelegate;
import com.toplyh.latte.ec.sign.ISignListener;
import com.toplyh.latte.ec.sign.SignInDelegate;
import com.toplyh.latte.ui.launcher.ILauncherListener;

import java.lang.annotation.Annotation;

import cn.jpush.android.api.JPushInterface;
import qiu.niorgai.StatusBarCompat;

public class ExampleActivity extends ProxyActivity implements
        ISignListener,
        ILauncherListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }


    @Override
    public void onSigned() {
        getSupportDelegate().startWithPop(new EcBottomDelegate());
    }

    @Override
    public void onNotSigned() {
        getSupportDelegate().startWithPop(new SignInDelegate());
    }
}
