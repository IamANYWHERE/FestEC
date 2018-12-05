package com.toplyh.latte.ec.detail;

import com.toplyh.latte.core.fragments.LatteDelegate;

public class PageEntity {

    private final String mTitle;
    private final LatteDelegate mDelegate;

    public PageEntity(String string, LatteDelegate delegate) {
        mTitle = string;
        mDelegate = delegate;
    }

    public String getTitle() {
        return mTitle;
    }

    public LatteDelegate getDelegate() {
        return mDelegate;
    }
}
