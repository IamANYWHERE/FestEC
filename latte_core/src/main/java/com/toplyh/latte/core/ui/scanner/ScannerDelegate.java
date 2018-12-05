package com.toplyh.latte.core.ui.scanner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.toplyh.latte.core.fragments.LatteDelegate;
import com.toplyh.latte.core.util.callback.CallbackManager;
import com.toplyh.latte.core.util.callback.CallbackType;
import com.toplyh.latte.core.util.callback.IGlobalCallback;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScannerDelegate extends LatteDelegate implements ZBarScannerView.ResultHandler {

    private ScanView mScanView = null;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mScanView == null) {
            mScanView = new ScanView(getContext());
        }
        mScanView.setAutoFocus(true);
    }


    @Override
    public Object setLayout() {
        return mScanView;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mScanView != null) {
            mScanView.setResultHandler(this);
            mScanView.startCamera();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mScanView != null) {
            mScanView.stopCamera();
        }
    }

    @Override
    public void handleResult(Result result) {
        @SuppressWarnings("unchecked") final IGlobalCallback<String> callback = CallbackManager.getInstance().getCallback(CallbackType.ON_SCAN);
        if (callback != null) {
            callback.execute(result.getContents());
        }
        pop();
    }
}
