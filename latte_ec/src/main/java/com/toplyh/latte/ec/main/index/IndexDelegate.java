package com.toplyh.latte.ec.main.index;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.toplyh.latte.core.app.Latte;
import com.toplyh.latte.core.delegates.bottom.BottomItemDelegate;
import com.toplyh.latte.core.net.RestClient;
import com.toplyh.latte.core.net.callback.ISuccess;
import com.toplyh.latte.core.ui.recycler.BaseDecoration;
import com.toplyh.latte.core.ui.recycler.MultipleFields;
import com.toplyh.latte.core.ui.recycler.MultipleItemEntity;
import com.toplyh.latte.core.ui.refresh.RefreshHandler;
import com.toplyh.latte.core.util.log.LatteLogger;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;

import java.util.ArrayList;

import butterknife.BindView;

public class IndexDelegate extends BottomItemDelegate {

    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mRefreshLayout = null;
    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.tb_index)
    Toolbar mToolbar = null;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconScan = null;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchView = null;
    @BindView(R2.id.icon_index_message)
    IconTextView mIconMessage = null;

    private RefreshHandler mRefreshHandler = null;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter());
    }

    private void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        mRefreshLayout.setProgressViewOffset(true, 120, 300);
    }

    private void initRecyclerView() {
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(),4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(),R.color.app_background),5));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("http://mock.fulingjie.com/mock/api/");
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

}
