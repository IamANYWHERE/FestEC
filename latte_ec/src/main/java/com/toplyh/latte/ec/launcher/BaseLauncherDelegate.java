package com.toplyh.latte.ec.launcher;

import android.app.Activity;

import com.toplyh.latte.core.global.AccountManager;
import com.toplyh.latte.core.global.IUserChecker;
import com.toplyh.latte.core.fragments.LatteDelegate;
import com.toplyh.latte.ui.launcher.ILauncherListener;


public abstract class BaseLauncherDelegate extends LatteDelegate {

    private ILauncherListener mILauncherListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }

    protected void checkAccountToOtherDelegate(){
        AccountManager.checkAccount(new IUserChecker() {
            @Override
            public void onSignIn() {
                if (mILauncherListener != null) {
                    mILauncherListener.onSigned();
                }
            }

            @Override
            public void onNotSignIn() {
                if (mILauncherListener != null) {
                    mILauncherListener.onNotSigned();
                }
            }
        });
    }
}
