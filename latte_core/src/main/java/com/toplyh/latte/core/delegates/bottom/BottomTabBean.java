package com.toplyh.latte.core.delegates.bottom;

public final class BottomTabBean {
    private final CharSequence ICON;
    private final CharSequence TITLE;


    public BottomTabBean(CharSequence icon, CharSequence title) {
        ICON = icon;
        TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
