package com.toplyh.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.toplyh.latte.core.delegates.bottom.BottomItemDelegate;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;
import com.toplyh.latte.ec.main.personal.list.ListAdapter;
import com.toplyh.latte.ec.main.personal.list.ListBean;
import com.toplyh.latte.ec.main.personal.list.PersonalListItemType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PersonalDelegate extends BottomItemDelegate {


    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRecyclerView = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final ListBean address = new ListBean.Builder()
                .setItemType(PersonalListItemType.ARROW_ITEM_LAYOUT)
                .setId(1)
                .setText("收货地址")
                .build();
        final ListBean system = new ListBean.Builder()
                .setItemType(PersonalListItemType.ARROW_ITEM_LAYOUT)
                .setId(2)
                .setText("系统设置")
                .build();

        List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
    }
}
