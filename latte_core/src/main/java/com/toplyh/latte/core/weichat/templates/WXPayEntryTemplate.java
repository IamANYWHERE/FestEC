package com.toplyh.latte.core.weichat.templates;

import android.widget.Toast;

import com.toplyh.latte.core.activities.ProxyActivity;
import com.toplyh.latte.core.delegates.LatteDelegate;
import com.toplyh.latte.core.weichat.BaseWXPayEntryActivity;

public class WXPayEntryTemplate extends BaseWXPayEntryActivity {


    @Override
    protected void onPaySuccess() {
        Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT);
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPayFail() {
        Toast.makeText(this,"支付失败",Toast.LENGTH_SHORT);
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onPayCancel() {
        Toast.makeText(this,"支付取消",Toast.LENGTH_SHORT);
        finish();
        overridePendingTransition(0,0);
    }
}
