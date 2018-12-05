package com.toplyh.latte.ec.detail;

import android.support.v7.widget.AppCompatImageView;

import com.toplyh.latte.core.image.ImageHelper;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ui.recycler.ItemType;
import com.toplyh.latte.ui.recycler.MultipleFields;
import com.toplyh.latte.ui.recycler.MultipleItemEntity;
import com.toplyh.latte.ui.recycler.MultipleRecyclerAdapter;
import com.toplyh.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

public class RecyclerImageAdapter extends MultipleRecyclerAdapter {
    protected RecyclerImageAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(ItemType.SINGLE_BIG_IMAGE, R.layout.item_image);
        
    }


    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        final int type = holder.getItemViewType();
        switch (type) {
            case ItemType.SINGLE_BIG_IMAGE:
                final AppCompatImageView imageView = holder.getView(R.id.image_rv_item);
                final String url = item.getField(MultipleFields.IMAGE_URL);
                ImageHelper.LoadOnlyNoCache(mContext,url,imageView);
                break;
            default:
                break;
        }
    }
}
