package com.toplyh.latte.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.latte.core.ui.recycler.DataConverter;

import java.util.ArrayList;
import java.util.List;

public class SectionDataConverter {

    final List<SectionBean> convert(String json) {
        final JSONArray data = JSON.parseObject(json).getJSONArray("data");
        final List<SectionBean> list = new ArrayList<>();

        final int size = data.size();
        for (int i = 0; i < size; i++) {
            final JSONObject section = data.getJSONObject(i);
            final String title = section.getString("section");
            final int id = section.getInteger("id");
            //插入标题item
            final SectionBean titleBean = new SectionBean(true, title);
            titleBean.setId(id);
            titleBean.setMore(true);
            list.add(titleBean);

            final JSONArray goods = section.getJSONArray("goods");
            final int goodsSize = goods.size();
            for (int j = 0; j < goodsSize; j++) {
                final JSONObject good = goods.getJSONObject(j);
                final int goodsId = good.getInteger("goods_id");
                final String goodsName = good.getString("goods_name");
                final String goodsThumb = good.getString("goods_thumb");

                final SectionContentItemEntity entity = new SectionContentItemEntity();
                entity.setGoodsId(goodsId);
                entity.setGoodsName(goodsName);
                entity.setGoodsThumb(goodsThumb);
                //插入商品item
                list.add(new SectionBean(entity));
            }
            //商品循环结束
        }
        //section循环结束
        return list;
    }
}
