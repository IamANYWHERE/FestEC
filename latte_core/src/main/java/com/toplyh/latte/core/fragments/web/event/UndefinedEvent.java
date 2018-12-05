package com.toplyh.latte.core.fragments.web.event;

import com.toplyh.latte.core.util.log.LatteLogger;

public class UndefinedEvent extends Event {
    @Override
    public String execute(String params) {
        LatteLogger.e("UndefinedEvent",params);
        return null;
    }
}
