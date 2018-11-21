package com.toplyh.latte.ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.toplyh.latte.core.image.ImageHelper;
import com.toplyh.latte.ec.R;

import java.util.List;

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean,BaseViewHolder> {


    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.header,item.header);
        helper.setVisible(R.id.more,item.isMore());
        helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        //item.t返回SectionContentItemEntity类型
        final String thumb = item.t.getGoodsThumb();
        final String name = item.t.getGoodsName();
        final int goodsId = item.t.getGoodsId();
        final SectionContentItemEntity entity = item.t;
        helper.setText(R.id.tv,name);
        final AppCompatImageView goodsImageView = helper.getView(R.id.iv);
        ImageHelper.LoadCacheAll(mContext,thumb,goodsImageView);
    }
}
