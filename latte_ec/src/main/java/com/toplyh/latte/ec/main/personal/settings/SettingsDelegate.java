package com.toplyh.latte.ec.main.personal.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.toplyh.latte.core.fragments.LatteDelegate;
import com.toplyh.latte.core.util.callback.CallbackManager;
import com.toplyh.latte.core.util.callback.CallbackType;
import com.toplyh.latte.core.util.storage.LattePreference;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;
import com.toplyh.latte.ec.main.personal.list.ListAdapter;
import com.toplyh.latte.ec.main.personal.list.ListBean;
import com.toplyh.latte.ec.main.personal.list.PersonalListItemType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SettingsDelegate extends LatteDelegate {

    public static final String PREFERENCE_KEY_PUSH = "push";

    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_settings;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final ListBean push = new ListBean.Builder()
                .setItemType(PersonalListItemType.ARROW_SWITCH_LAYOUT)
                .setId(1)
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_OPEN_PUSH).execute(null);
                            LattePreference.setAppFlag(PREFERENCE_KEY_PUSH,true);
                            Toast.makeText(getContext(),"打开推送",Toast.LENGTH_SHORT).show();
                        }else {
                            CallbackManager.getInstance().getCallback(CallbackType.TAG_STOP_PUSH).execute(null);
                            LattePreference.setAppFlag(PREFERENCE_KEY_PUSH,false);
                            Toast.makeText(getContext(),"关闭推送",Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setText("消息推送")
                .build();
        final ListBean about = new ListBean.Builder()
                .setItemType(PersonalListItemType.ARROW_ITEM_LAYOUT)
                .setId(2)
                .setDelegate(new AboutDelegate())
                .setText("关于")
                .build();

        List<ListBean> data = new ArrayList<>();
        data.add(push);
        data.add(about);

        //设置RecyclerView
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        final ListAdapter adapter = new ListAdapter(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new SettingsClickListener(this));
    }
}
