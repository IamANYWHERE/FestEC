package com.toplyh.latte.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.latte.ui.recycler.DataConverter;
import com.toplyh.latte.ui.recycler.ItemType;
import com.toplyh.latte.ui.recycler.MultipleFields;
import com.toplyh.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

public final class VerticalListDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");

        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");
            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID, id)
                    .setField(MultipleFields.NAME, name)
                    .setField(MultipleFields.TAG, false)
                    .build();

            ENTITIES.add(entity);
            //设置第一个被选中
            ENTITIES.get(0).setField(MultipleFields.TAG, true);
        }
        return ENTITIES;
    }
}
