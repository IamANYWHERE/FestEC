package com.toplyh.latte.ec.launcher;

import android.app.Activity;

import com.toplyh.latte.core.app.AccountManager;
import com.toplyh.latte.core.app.IUserChecker;
import com.toplyh.latte.core.delegates.LatteDelegate;
import com.toplyh.latte.core.ui.launcher.ILauncherListener;
import com.toplyh.latte.core.ui.launcher.OnLauncherFinishTag;

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
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                }
            }

            @Override
            public void onNotSignIn() {
                if (mILauncherListener != null) {
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            }
        });
    }
}
