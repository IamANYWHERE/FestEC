package com.toplyh.latte.ec.main.personal;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.toplyh.latte.core.fragments.LatteDelegate;
import com.toplyh.latte.ec.main.personal.list.ListBean;

public class PersonalOnClickListener extends SimpleClickListener {

    private final LatteDelegate DELEGATE;

    PersonalOnClickListener(LatteDelegate delegate) {
        DELEGATE = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) baseQuickAdapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
                DELEGATE.getParentDelegate().start(bean.getDelegate());
                break;
            case 2:
                DELEGATE.getParentDelegate().start(bean.getDelegate());
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
