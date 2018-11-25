package com.toplyh.latte.ec.main.personal.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.latte.ui.recycler.DataConverter;
import com.toplyh.latte.ui.recycler.MultipleFields;
import com.toplyh.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class OderListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = array.getJSONObject(i);
            final int id = data.getInteger("id");
            final String thumb = data.getString("thumb");
            final double price = data.getDouble("price");
            final String title = data.getString("title");
            final String time = data.getString("time");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,OrderListItemType.ITEM_ORDER_LIST)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.IMAGE_URL,thumb)
                    .setField(MultipleFields.TITLE,title)
                    .setField(OderItemFields.PRICE,price)
                    .setField(OderItemFields.TIME,time)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
