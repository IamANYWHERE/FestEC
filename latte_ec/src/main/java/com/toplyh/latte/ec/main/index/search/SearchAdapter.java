package com.toplyh.latte.ec.main.index.search;

import android.support.v7.widget.AppCompatTextView;

import com.toplyh.latte.ec.R;
import com.toplyh.latte.ui.recycler.MultipleFields;
import com.toplyh.latte.ui.recycler.MultipleItemEntity;
import com.toplyh.latte.ui.recycler.MultipleRecyclerAdapter;
import com.toplyh.latte.ui.recycler.MultipleViewHolder;

import java.util.List;

public class SearchAdapter extends MultipleRecyclerAdapter {


    protected SearchAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);
    }


    @Override
    protected void convert(MultipleViewHolder holder, MultipleItemEntity item) {
        switch (item.getItemType()) {
            case SearchItemType.ITEM_SEARCH:
                final AppCompatTextView tvSearchItem = holder.getView(R.id.tv_search_item);
                final String history = item.getField(MultipleFields.TEXT);
                tvSearchItem.setText(history);
                break;
            default:
                break;
        }
    }
}
