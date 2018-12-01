package com.toplyh.latte.ec.main.personal.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.toplyh.latte.core.delegates.LatteDelegate;
import com.toplyh.latte.core.net.RestClient;
import com.toplyh.latte.core.net.callback.ISuccess;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;
import com.toplyh.latte.ec.main.personal.PersonalDelegate;
import com.toplyh.latte.ui.recycler.MultipleItemEntity;

import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class OrderListDelegate extends LatteDelegate {

    @BindView(R2.id.rv_order_list)
    RecyclerView mRecyclerView = null;

    @BindView(R2.id.tv_order_list_tb)
    AppCompatTextView mTBTextView = null;

    private OrderType mType = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        mType = OrderType.valueOf(args.getString(PersonalDelegate.ORDER_TYPE));
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        setToolbar();
        RestClient.builder()
                .loader(getContext())
                .url("order_list.json")
                .params("type", mType)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        mRecyclerView.setLayoutManager(manager);
                        final List<MultipleItemEntity> data = new OderListDataConverter().setJsonData(response).convert();
                        final OrderListAdapter adapter = new OrderListAdapter(data);
                        mRecyclerView.setAdapter(adapter);
                        mRecyclerView.addOnItemTouchListener(new OrderListClickListener(OrderListDelegate.this));
                    }
                })
                .build()
                .get();
    }

    private void setToolbar() {
        switch (mType) {
            case ORDER_ALL:
                mTBTextView.setText("全部订单");
                break;
            case ORDER_PENDING_PAYMENT:
                mTBTextView.setText("待付款");
                break;
            case ORDER_PENDING_RECEIPT:
                mTBTextView.setText("待收货");
                break;
            case ORDER_PENDING_EVALUATION:
                mTBTextView.setText("待评价");
                break;
            case ORDER_AFTER_SALE:
                mTBTextView.setText("售后");
            default:
                break;
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
