package com.toplyh.latte.ec.sign;

import android.app.Activity;

import com.toplyh.latte.core.fragments.LatteDelegate;

public abstract class SignDelegate extends LatteDelegate {

    protected ISignListener mISignListener = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListener = (ISignListener) activity;
        }
    }
}
