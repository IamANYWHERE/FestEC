package com.toplyh.latte.core.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

public class MultipleItemEntity implements MultiItemEntity {
    //在recyclerview中数据太过于庞大的时，很可能造成内存溢出，所以这里使用SoftReference或者WeakReference，当内存不够时进行释放
    private final ReferenceQueue<LinkedHashMap<Object,Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object,Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object,Object>> FIELDS_REFERENCE =
            new SoftReference<>(MULTIPLE_FIELDS,ITEM_QUEUE);


    public static MultipleItemEntityBuilder builder(){
        return new MultipleItemEntityBuilder();
    }

    MultipleItemEntity(LinkedHashMap<Object,Object> fields) {
        FIELDS_REFERENCE.get().putAll(fields);
    }

    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    @SuppressWarnings("unchecked")
    public final <T> T getField(Object key){
        return (T)FIELDS_REFERENCE.get().get(key);
    }

    public final LinkedHashMap<?,?> getFields(){
        return FIELDS_REFERENCE.get();
    }
}
