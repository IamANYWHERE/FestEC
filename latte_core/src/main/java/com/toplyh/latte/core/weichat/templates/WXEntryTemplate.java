package com.toplyh.latte.core.weichat.templates;

import com.toplyh.latte.core.weichat.BaseWXEntryActivity;
import com.toplyh.latte.core.weichat.LatteWeChat;

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        LatteWeChat.getInstance().getSignInCallback().onSignInSuccess(userInfo);
    }
}
