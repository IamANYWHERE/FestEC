package com.toplyh.latte.ec.main.index;

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
import com.toplyh.latte.core.fragments.bottom.BottomItemDelegate;
import com.toplyh.latte.core.net.RestCreator;
import com.toplyh.latte.core.net.rx.RxRestClient;
import com.toplyh.latte.core.util.callback.CallbackManager;
import com.toplyh.latte.core.util.callback.CallbackType;
import com.toplyh.latte.core.util.callback.IGlobalCallback;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;
import com.toplyh.latte.ec.main.EcBottomDelegate;
import com.toplyh.latte.ec.main.index.search.SearchDelegate;
import com.toplyh.latte.ui.recycler.BaseDecoration;
import com.toplyh.latte.ui.refresh.RefreshHandler;

import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class IndexDelegate extends BottomItemDelegate implements View.OnFocusChangeListener {

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

    @OnClick(R2.id.icon_index_scan)
    void onClickScanQrCode() {
        startScanWithCheck(this.getParentDelegate());
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new IndexDataConverter());
        CallbackManager.getInstance().addCallback(CallbackType.ON_SCAN, new IGlobalCallback<String>() {
            @Override
            public void execute(@Nullable String args) {
                Toast.makeText(getContext(), args, Toast.LENGTH_LONG).show();
            }
        });
        mSearchView.setOnFocusChangeListener(this);

        //onCallRxGet();
        onCallRxRestClient();
    }

    //TODO:测试用，可删
    void onCallRxGet() {
        final String url = "api/";
        final WeakHashMap<String, Object> params = new WeakHashMap<>();
        final Observable<String> observable = RestCreator.getRxRestService().get(url, params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        //Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //TODO:测试用，可删
    void onCallRxRestClient(){
        final String url = "api/";
        RxRestClient.builder()
                .url(url)
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        //Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

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
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration
                (BaseDecoration.create(ContextCompat.getColor(getContext(), R.color.app_background), 5));
        final EcBottomDelegate ecBottomDelegate = getParentDelegate();
        mRecyclerView.addOnItemTouchListener(IndexItemClickListener.create(ecBottomDelegate));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("api/");
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            getParentDelegate().start(new SearchDelegate());
        }
    }
}
