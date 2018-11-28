package com.toplyh.latte.ec.main.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.toplyh.latte.core.delegates.bottom.BottomItemDelegate;
import com.toplyh.latte.core.image.ImageHelper;
import com.toplyh.latte.core.net.RestClient;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;
import com.toplyh.latte.ec.main.personal.list.ListAdapter;
import com.toplyh.latte.ec.main.personal.list.ListBean;
import com.toplyh.latte.ec.main.personal.list.PersonalListItemType;
import com.toplyh.latte.ec.main.personal.order.OrderListDelegate;
import com.toplyh.latte.ec.main.personal.order.OrderType;
import com.toplyh.latte.ec.main.personal.profile.UserProfileDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalDelegate extends BottomItemDelegate {

    public static final String ORDER_TYPE = "ORDER_TYPE";
    private Bundle mArgs = null;

    @BindView(R2.id.rv_personal_setting)
    RecyclerView mRecyclerView = null;

    @BindView(R2.id.img_user_avatar)
    CircleImageView mAvatar;

    @OnClick(R2.id.tv_all_order)
    void onClickAllOrder(){
        mArgs.putString(ORDER_TYPE,OrderType.ORDER_ALL.name());
        startOrderListByType();
    }

    @OnClick(R2.id.ll_pay)
    void onClickPendingPayment(){
        mArgs.putString(ORDER_TYPE,OrderType.ORDER_PENDING_PAYMENT.name());
        startOrderListByType();
    }

    @OnClick(R2.id.ll_receive)
    void onClickPendReceipt(){
        mArgs.putString(ORDER_TYPE,OrderType.ORDER_PENDING_RECEIPT.name());
        startOrderListByType();
    }

    @OnClick(R2.id.ll_evaluate)
    void onClickPendEvaluation(){
        mArgs.putString(ORDER_TYPE,OrderType.ORDER_PENDING_EVALUATION.name());
        startOrderListByType();
    }

    @OnClick(R2.id.ll_after_market)
    void onClickAfterSale(){
        mArgs.putString(ORDER_TYPE,OrderType.ORDER_AFTER_SALE.name());
        startOrderListByType();
    }

    @OnClick(R2.id.img_user_avatar)
    void onClickAvatar(){
        getParentDelegate().start(new UserProfileDelegate());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_personal;
    }

    private void startOrderListByType(){
        final OrderListDelegate delegate = new OrderListDelegate();
        delegate.setArguments(mArgs);
        getParentDelegate().start(delegate);
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

        ImageHelper.LoadCacheAll(getContext(),"http://toplyh.oss-cn-hongkong.aliyuncs.com/FestEC/avatar.jpg",mAvatar);
    }
}
