package com.toplyh.latte.ec.main.personal.order;

import android.support.v7.widget.AppCompatImageView;

import com.toplyh.latte.core.image.ImageHelper;
import com.toplyh.latte.core.util.format.PriceFormat;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ui.recycler.MultipleFields;
import com.toplyh.latte.ui.recycler.MultipleItemEntity;
import com.toplyh.latte.ui.recycler.MultipleRecyclerAdapter;
import com.toplyh.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

public class OrderListAdapter extends MultipleRecyclerAdapter {


    protected OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_LIST, R.layout.item_order_list);
    }

    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        switch (holder.getItemViewType()) {
            case OrderListItemType.ITEM_ORDER_LIST:
                final String thumb = item.getField(MultipleFields.IMAGE_URL);
                final String title = item.getField(MultipleFields.TITLE);
                final double price = item.getField(OderItemFields.PRICE);
                final String time = item.getField(OderItemFields.TIME);

                final AppCompatImageView imageView = holder.getView(R.id.image_order_list);
                ImageHelper.LoadCacheAll(mContext,thumb,imageView);
                holder.setText(R.id.tv_order_list_title,title);
                holder.setText(R.id.tv_order_list_price,PriceFormat.formatPrice(price));
                holder.setText(R.id.tv_order_list_time,"时间: "+time);
                break;
            default:
                break;
        }
    }
}
