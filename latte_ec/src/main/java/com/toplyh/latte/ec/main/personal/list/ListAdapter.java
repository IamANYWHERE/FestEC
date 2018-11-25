package com.toplyh.latte.ec.main.personal.list;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toplyh.latte.ec.R;

import java.util.List;

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {

    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(PersonalListItemType.ARROW_ITEM_AVATAR, R.layout.arrow_item_layout);
        addItemType(PersonalListItemType.ARROW_ITEM_LAYOUT, R.layout.arrow_item_avatar);
        addItemType(PersonalListItemType.ARROW_SWITCH_LAYOUT, R.layout.arrow_switch_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (helper.getItemViewType()) {
            case PersonalListItemType.ARROW_ITEM_LAYOUT:
                helper.setText(R.id.tv_arrow_text, item.getText());
                helper.setText(R.id.tv_arrow_value, item.getValue());

                break;
            case PersonalListItemType.ARROW_ITEM_AVATAR:
                break;
            case PersonalListItemType.ARROW_SWITCH_LAYOUT:
                break;
            default:
                break;
        }
    }
}
