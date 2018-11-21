package com.toplyh.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.toplyh.latte.core.delegates.bottom.BottomItemDelegate;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.main.sort.content.ContentDelegate;
import com.toplyh.latte.ec.main.sort.list.VerticalListDelegate;

public class SortDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        loadRootFragment(R.id.vertical_list_container,listDelegate);
        //设置默认显示第一个分类显示
        loadRootFragment(R.id.sort_list_container,ContentDelegate.newInstance(1));
    }

}
