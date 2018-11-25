package com.toplyh.latte.ui.recycler;

/**
 * 用static final的占用的内存要比枚举类少，但是枚举类使得代码更工整
 */
public enum MultipleFields {
    ITEM_TYPE,
    TEXT,
    TITLE,
    IMAGE_URL,
    BANNERS,
    SPAN_SIZE,
    ID,
    NAME,
    TAG
}
