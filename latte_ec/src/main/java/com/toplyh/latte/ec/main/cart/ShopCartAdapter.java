package com.toplyh.latte.ec.main.cart;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.toplyh.latte.core.app.Latte;
import com.toplyh.latte.core.image.ImageHelper;
import com.toplyh.latte.core.util.format.PriceFormat;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ui.recycler.MultipleFields;
import com.toplyh.latte.ui.recycler.MultipleItemEntity;
import com.toplyh.latte.ui.recycler.MultipleRecyclerAdapter;
import com.toplyh.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

public class ShopCartAdapter extends MultipleRecyclerAdapter {

    private boolean mIsSelectedAll = false;
    private int mSelectedCount = 0;
    private double mTotalPrice = 0.00;
    private IShopItemStateListener mShopItemStateListener = null;
    private ICartItemPriceListener mCartItemPriceListener = null;

    protected ShopCartAdapter(List<MultipleItemEntity> data) {
        super(data);
        //初始化总价
        for (MultipleItemEntity entity : data) {
            final double price = entity.getField(ShopCartItemFields.PRICE);
            final int count = entity.getField(ShopCartItemFields.COUNT);
            final double total = price * count;
            mTotalPrice += total;
        }
        //添加添加购物车item布局
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
        openLoadAnimation(ALPHAIN);
    }

    public double getTotalPrice() {
        return mTotalPrice;
    }

    public void setShopItemStateListener(IShopItemStateListener listener) {
        mShopItemStateListener = listener;
    }

    public void setCartItemPriceListener(ICartItemPriceListener listener) {
        mCartItemPriceListener = listener;
    }

    public void setIsSelectedAll(boolean isSelectedAll) {
        this.mIsSelectedAll = isSelectedAll;
        mSelectedCount = mIsSelectedAll ? getData().size() : 0;
        setAllSelectState();
    }

    private void setAllSelectState() {
        List<MultipleItemEntity> data = mData;
        for (MultipleItemEntity entity : data) {
            entity.setField(ShopCartItemFields.IS_SELECTED, mIsSelectedAll);
        }
    }

    @Override
    protected void convert(MultipleViewHolder holder, final MultipleItemEntity item) {
        super.convert(holder, item);
        switch (holder.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                //先取出所有的值
                final int id = item.getField(MultipleFields.ID);
                final String thumb = item.getField(MultipleFields.IMAGE_URL);
                final String title = item.getField(ShopCartItemFields.TITLE);
                final String desc = item.getField(ShopCartItemFields.DESC);
                final int count = item.getField(ShopCartItemFields.COUNT);
                final double price = item.getField(ShopCartItemFields.PRICE);
                final boolean isSelected = item.getField(ShopCartItemFields.IS_SELECTED);
                //取出所有控件
                final AppCompatImageView imgThumb = holder.getView(R.id.image_item_shop_cart);
                final AppCompatTextView tvTitle = holder.getView(R.id.tv_item_shop_cart_title);
                final AppCompatTextView tvDesc = holder.getView(R.id.tv_item_shop_cart_desc);
                final AppCompatTextView tvPrice = holder.getView(R.id.tv_item_shop_cart_price);
                final IconTextView iconMinus = holder.getView(R.id.icon_item_minus);
                final AppCompatTextView tvCount = holder.getView(R.id.tv_item_shop_cart_count);
                final IconTextView iconPlus = holder.getView(R.id.icon_item_plus);
                final IconTextView iconIsSelected = holder.getView(R.id.icon_item_shop_cart);
                //赋值
                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvCount.setText(String.valueOf(count));
                tvPrice.setText(PriceFormat.formatPrice(price));
                ImageHelper.LoadCacheAll(mContext, thumb, imgThumb);
                //根据数据状态显示左侧勾勾
                if (isSelected) {
                    iconIsSelected.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                } else {
                    iconIsSelected.setTextColor(Color.GRAY);
                }
                //添加左侧勾勾的点击事件
                iconIsSelected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final boolean currentSelected = item.getField(ShopCartItemFields.IS_SELECTED);
                        item.setField(ShopCartItemFields.IS_SELECTED, !currentSelected);
                        if (currentSelected) {
                            iconIsSelected.setTextColor(Color.GRAY);
                            mSelectedCount--;
                            if (mIsSelectedAll) {
                                mIsSelectedAll = false;
                                if (mShopItemStateListener != null) {
                                    mShopItemStateListener.onAllNotSelected();
                                }
                            }
                        } else {
                            iconIsSelected.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(), R.color.app_main));
                            mSelectedCount++;
                            if (mSelectedCount == getData().size()) {
                                mIsSelectedAll = true;
                                if (mShopItemStateListener != null) {
                                    mShopItemStateListener.onAllSelected();
                                }
                            }
                        }
                    }
                });
                //添加加减事件
                iconMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentCount = item.getField(ShopCartItemFields.COUNT);
                        if (currentCount > 1) {
                            currentCount--;
                            item.setField(ShopCartItemFields.COUNT, currentCount);
                            tvCount.setText(String.valueOf(currentCount));
                            if (mCartItemPriceListener != null) {
                                mTotalPrice -= price;
                                final double itemTotal = currentCount * price;
                                mCartItemPriceListener.onItemChange(itemTotal);
                            }
                        }
                    }
                });
                iconPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentCount = item.getField(ShopCartItemFields.COUNT);
                        currentCount++;
                        item.setField(ShopCartItemFields.COUNT, currentCount);
                        tvCount.setText(String.valueOf(currentCount));
                        if (mCartItemPriceListener != null) {
                            mTotalPrice += price;
                            final double itemTotal = currentCount * price;
                            mCartItemPriceListener.onItemChange(itemTotal);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
