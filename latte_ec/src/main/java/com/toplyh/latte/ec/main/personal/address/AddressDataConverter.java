package com.toplyh.latte.ec.main.personal.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.toplyh.latte.ui.recycler.DataConverter;
import com.toplyh.latte.ui.recycler.MultipleFields;
import com.toplyh.latte.ui.recycler.MultipleItemEntity;
import com.toplyh.latte.ui.recycler.MultipleItemEntityBuilder;

import java.util.ArrayList;

public class AddressDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");

        final int size = array.size();
        for (int i = 0;i<size;i++){
            final JSONObject data= array.getJSONObject(i);
            final int id = data.getInteger("id");
            final boolean def = data.getBoolean("default");
            final String name = data.getString("name");
            final String phone = data.getString("phone");
            final String address = data.getString("address");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,AddressItemType.ITEM_ADDRESS)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.NAME,name)
                    .setField(MultipleFields.TAG,def)
                    .setField(AddressFields.ADDRESS,address)
                    .setField(AddressFields.PHONE,phone)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
