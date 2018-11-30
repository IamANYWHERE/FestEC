package com.toplyh.latte.ec.main.personal.list;

import android.support.v7.widget.SwitchCompat;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toplyh.latte.core.image.ImageHelper;
import com.toplyh.latte.core.util.log.LatteLogger;
import com.toplyh.latte.core.util.storage.LattePreference;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.main.personal.settings.SettingsDelegate;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean, BaseViewHolder> {

    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(PersonalListItemType.ARROW_ITEM_AVATAR, R.layout.arrow_item_avatar);
        addItemType(PersonalListItemType.ARROW_ITEM_LAYOUT, R.layout.arrow_item_layout);
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
                final CircleImageView circleImageView = helper.getView(R.id.img_arrow_avatar);
                ImageHelper.LoadCacheAll(mContext,item.getImageUrl(),circleImageView);
                break;
            case PersonalListItemType.ARROW_SWITCH_LAYOUT:
                helper.setText(R.id.tv_arrow_switch_text,item.getText());
                final SwitchCompat switchCompat = helper.getView(R.id.list_item_switch);
                final boolean ispush;
                if (LattePreference.isAppFlagStored(SettingsDelegate.PREFERENCE_KEY_PUSH)){
                    ispush = LattePreference.getAppFlag(SettingsDelegate.PREFERENCE_KEY_PUSH);
                }else {
                    ispush = true;
                }
                switchCompat.setChecked(ispush);
                switchCompat.setOnCheckedChangeListener(item.getOnCheckedChangeListener());
                break;
            default:
                break;
        }
    }
}
