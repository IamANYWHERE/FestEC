package com.toplyh.latte.ec.main.personal.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.toplyh.latte.core.delegates.LatteDelegate;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;
import com.toplyh.latte.ec.main.personal.list.ListAdapter;
import com.toplyh.latte.ec.main.personal.list.ListBean;
import com.toplyh.latte.ec.main.personal.list.PersonalListItemType;
import com.toplyh.latte.ec.main.personal.settings.NameDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UserProfileDelegate extends LatteDelegate {

    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecyclerView = null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_user_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final ListBean image = new ListBean.Builder()
                .setItemType(PersonalListItemType.ARROW_ITEM_AVATAR)
                .setId(1)
                .setImageUrl("http://toplyh.oss-cn-hongkong.aliyuncs.com/FestEC/avatar.jpg")
                .build();
        final ListBean name = new ListBean.Builder()
                .setItemType(PersonalListItemType.ARROW_ITEM_LAYOUT)
                .setId(2)
                .setText("姓名")
                .setValue("未设置姓名")
                .setDelegate(new NameDelegate())
                .build();
        final ListBean gender = new ListBean.Builder()
                .setItemType(PersonalListItemType.ARROW_ITEM_LAYOUT)
                .setId(3)
                .setText("性别")
                .setValue("未设置性别")
                .build();
        final ListBean birth = new ListBean.Builder()
                .setItemType(PersonalListItemType.ARROW_ITEM_LAYOUT)
                .setId(4)
                .setText("生日")
                .setValue("未设置生日")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(image);
        data.add(name);
        data.add(gender);
        data.add(birth);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }
}
