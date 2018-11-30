package com.toplyh.latte.core.util.callback;

import android.support.annotation.Nullable;

public interface IGlobalCallback<T> {
    void execute(@Nullable T args);
}
