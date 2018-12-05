package com.toplyh.latte.core.fragments.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.toplyh.latte.core.fragments.web.event.Event;
import com.toplyh.latte.core.fragments.web.event.EventManager;

final class LatteWebInterface {

    private final WebDelegate DELEGATE;


    private LatteWebInterface(WebDelegate delegate) {
        DELEGATE = delegate;
    }

    static LatteWebInterface create(WebDelegate delegate){
        return new LatteWebInterface(delegate);
    }

    @JavascriptInterface
    public String event(String params){
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        if (event!=null){
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
