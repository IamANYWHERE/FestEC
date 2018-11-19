package com.toplyh.latte.ec.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.latte.core.ui.recycler.DataConverter;
import com.toplyh.latte.core.ui.recycler.ItemType;
import com.toplyh.latte.core.ui.recycler.MultipleFields;
import com.toplyh.latte.core.ui.recycler.MultipleItemEntity;


import java.util.ArrayList;

public class IndexDataConverter extends DataConverter {


    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);

            final String imageUrl = data.getString("imageUrl");
            final String text = data.getString("text");
            final int spanSize = data.getInteger("spanSize");
            final int id = data.getInteger("goodsId");
            final JSONArray banners = data.getJSONArray("banners");

            final ArrayList<String> bannerImages = new ArrayList<>();
            int type = 0;
            if (imageUrl == null && text != null) {
                type = ItemType.TEXT;
            } else if (imageUrl != null && text == null) {
                type = ItemType.IMAGE;
            } else if (imageUrl != null) {
                type = ItemType.TEXT_IMAGE;
            } else if (banners != null) {
                type = ItemType.BANNER;
                //banner的初始化
                final int bannersSize = banners.size();
                for (int j = 0; j < bannersSize; j++) {
                    final String banner = banners.getString(j);
                    bannerImages.add(banner);
                }
            }
            final MultipleItemEntity entity=MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,type)
                    .setField(MultipleFields.IMAGE_URL,imageUrl)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.SPAN_SIZE,spanSize)
                    .setField(MultipleFields.TEXT,text)
                    .setField(MultipleFields.BANNERS,bannerImages)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
