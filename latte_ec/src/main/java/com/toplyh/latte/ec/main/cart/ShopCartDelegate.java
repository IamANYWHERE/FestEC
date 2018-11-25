package com.toplyh.latte.ec.main.cart;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;
import com.toplyh.latte.core.delegates.bottom.BottomItemDelegate;
import com.toplyh.latte.core.net.RestClient;
import com.toplyh.latte.core.net.callback.ISuccess;
import com.toplyh.latte.core.util.format.PriceFormat;
import com.toplyh.latte.core.util.log.LatteLogger;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;
import com.toplyh.latte.ec.pay.FastPay;
import com.toplyh.latte.ec.pay.IAlPayResultListener;
import com.toplyh.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopCartDelegate extends BottomItemDelegate implements
        ISuccess,
        IShopItemStateListener,
        ICartItemPriceListener,
        IAlPayResultListener {

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_shop_cart_select_all)
    IconTextView mIconSelected = null;
    @BindView(R2.id.stub_no_item)
    ViewStubCompat mStubNoItem = null;
    @BindView(R2.id.tv_shop_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;

    private ShopCartAdapter mAdapter;
    private LinkedList<Integer> mDeletedPositions = new LinkedList<>();
    private double mTotalPrice = 0.00;

    /**
     * notifyItemRangeChanged(int positionStart, int itemCount,Object payload)
     * payload是一个标记，可以标记部分item或者一个item，用于去更新item中个别组件，而不影响到其他的UI
     * 所有notifyChanged方法最后都会调用这个方法，只是没有payload参数时，则payload为null
     * 当payload为null时，则表示不进行特殊更新，而当payload不为null时，则表示进行特殊更新
     * 此时必须继承onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads)方法
     * 当到达需要进行特殊更新的item时，payloads不为空，且其size=1，里面的值为notifyChanged传入的payload。
     * 根据payloads的值进行需要的特殊更新。
     */
    @OnClick(R2.id.icon_shop_cart_select_all)
    void onClickSelectAll() {
        final int tag = (int) mIconSelected.getTag();
        final boolean state = (tag == 0);
        setIconSelectState(state);
        mAdapter.setIsSelectedAll(state);
        //更新view的显示状态
        mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
    }

    @OnClick(R2.id.tv_top_shop_cart_remove_selected)
    void onClickRemoveSelectedItem() {
        final List<MultipleItemEntity> data = mAdapter.getData();
        //找到要删除的数据
        final List<MultipleItemEntity> deleteEntities = new ArrayList<>();
        for (MultipleItemEntity entity : data) {
            final boolean isSelected = entity.getField(ShopCartItemFields.IS_SELECTED);
            if (isSelected) {
                deleteEntities.add(entity);
            }
        }

        if (deleteEntities.size() == 0) {
            return;
        }
        //更新adapter中选中的数量，更新全选UI
        mAdapter.setIsSelectedAll(false);
        setIconSelectState(false);

        for (MultipleItemEntity entity : deleteEntities) {
            final int entityPosition = entity.getField(ShopCartItemFields.POSITION);
            final int realPosition = entityPosition - addDeletedPosition(entityPosition);
            mAdapter.remove(realPosition);
        }
        checkItemCount();
        mAdapter.updateTotalPrice();
    }

    @OnClick(R2.id.tv_top_shop_cart_clear)
    void onClickClear() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        mAdapter.setIsSelectedAll(false);
        setIconSelectState(false);
        checkItemCount();
        mAdapter.updateTotalPrice();
    }

    @OnClick(R2.id.tv_shop_cart_pay)
    void onClickPay(){
        createOrder();
    }

    //创建订单、注意和支付是没有关系的
    private void createOrder(){
        final String orderUrl = "";
        final WeakHashMap<String,Object> orderParams = new WeakHashMap<>();
        orderParams.put("userId",151325);
        orderParams.put("amount",0.01);
        orderParams.put("comment","测试支付");
        orderParams.put("type",1);
        orderParams.put("ordertype",0);
        orderParams.put("isanonymous",true);
        orderParams.put("followeduser",0);
        RestClient.builder()
                .url(orderUrl)
                .loader(getContext())
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //进行一个支付的过程
                        LatteLogger.d("ORDER",response);
                        final int orderId = 1;
                        FastPay.create(ShopCartDelegate.this)
                                .setPayResultListener(ShopCartDelegate.this)
                                .setOrderId(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();
    }

    private void checkItemCount() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            @SuppressLint("RestrictedApi") final View stubView = mStubNoItem.inflate();
            final IconTextView tvToBuy = stubView.findViewById(R.id.tv_stub_to_buy);
            tvToBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "人丑还不买东西？", Toast.LENGTH_LONG).show();
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private int addDeletedPosition(int position) {
        final int size = mDeletedPositions.size();
        if (size == 0) {
            mDeletedPositions.add(position);
            return 0;
        } else if (size == 1) {
            final int value = mDeletedPositions.get(0);
            if (position >= value) {
                mDeletedPositions.add(position);
                return 1;
            }
            mDeletedPositions.addFirst(position);
            return 0;
        }
        final int startValue = mDeletedPositions.getFirst();
        final int endValue = mDeletedPositions.getLast();
        if (position <= startValue) {
            mDeletedPositions.addFirst(position);
            return 0;
        } else if (position >= endValue) {
            mDeletedPositions.addLast(position);
            return size;
        } else {
            final int index = binarySearch(0, size - 1, position);
            mDeletedPositions.add(index, position);
            return index;
        }
    }

    private int binarySearch(int start, int end, int value) {
        int b = (end - start) / 2;
        if (b == 0) {
            return end;
        }
        final int index = start + b;
        final int binaryValue = mDeletedPositions.get(index);
        if (value > binaryValue) {
            return binarySearch(index, end, value);
        } else {
            return binarySearch(start, index, value);
        }

    }

    private void setIconSelectState(boolean state) {
        if (state) {
            mIconSelected.setTextColor(ContextCompat.getColor(getContext(), R.color.app_main));
            mIconSelected.setTag(1);
        } else {
            mIconSelected.setTextColor(Color.GRAY);
            mIconSelected.setTag(0);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url("shop_cart_data.json")
                .success(this)
                .build()
                .get();
        mIconSelected.setTag(0);
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> data = new ShopCartDataConverter()
                .setJsonData(response).convert();
        mAdapter = new ShopCartAdapter(data);
        mAdapter.setShopItemStateListener(this);
        mAdapter.setCartItemPriceListener(this);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        mTotalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(PriceFormat.formatPrice(mTotalPrice));
        checkItemCount();
    }

    @Override
    public void onAllSelected() {
        setIconSelectState(true);
    }

    @Override
    public void onAllNotSelected() {
        setIconSelectState(false);
    }

    @Override
    public void onItemChange(double itemTotalPrice) {
        final double price = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(PriceFormat.formatPrice(price));
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }
}
