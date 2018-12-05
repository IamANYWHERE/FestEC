package com.toplyh.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.latte.core.fragments.LatteDelegate;
import com.toplyh.latte.core.util.format.PriceFormat;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;

import butterknife.BindView;

public class GoodsInfoDelegate extends LatteDelegate {

    @BindView(R2.id.tv_goods_info_title)
    AppCompatTextView mGoodsInfoTitle = null;
    @BindView(R2.id.tv_goods_info_desc)
    AppCompatTextView mGoodsInfoDesc = null;
    @BindView(R2.id.tv_goods_info_price)
    AppCompatTextView mGoodsInfoPrice = null;

    private static final String ARG_GOODS_DATA = "ARG_GOODS_DATA";
    private JSONObject mData = null;

    public static GoodsInfoDelegate create(String goodsData) {
        final Bundle args = new Bundle();
        args.putString(ARG_GOODS_DATA, goodsData);
        final GoodsInfoDelegate delegate = new GoodsInfoDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        final String goodsData = args.getString(ARG_GOODS_DATA);
        mData = JSON.parseObject(goodsData);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_info;
    }

    @Override
    public boolean onBackPressedSupport() {
        return false;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final String name = mData.getString("name");
        final String description = mData.getString("description");
        final double price = mData.getDouble("price");

        mGoodsInfoTitle.setText(name);
        mGoodsInfoDesc.setText(description);
        mGoodsInfoPrice.setText(PriceFormat.formatPrice(price));
    }
}
