package com.toplyh.latte.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * 这种类型的entity可以被当作多种类型的entity使用
 * 因为里面的成员变量时通过map来存储的，所以每个entity存储的数据可以是不一样的
 */
public class MultipleItemEntity implements MultiItemEntity {
    //在recyclerview中数据太过于庞大的时，很可能造成内存溢出，所以这里使用SoftReference或者WeakReference，当内存不够时进行释放
    private final ReferenceQueue<LinkedHashMap<Object,Object>> ITEM_QUEUE = new ReferenceQueue<>();
    //private LinkedHashMap<Object,Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object,Object>> FIELDS_REFERENCE =
            new SoftReference<>(new LinkedHashMap<Object, Object>(),ITEM_QUEUE);


    public static MultipleItemEntityBuilder builder(){
        return new MultipleItemEntityBuilder();
    }

    //对象初始化时，会把所有成员变量的初始化都搬到构造函数中，并按照所写时候的
    //初始化顺序排序，在构造函数的初始化会被放到构造函数的最后
    MultipleItemEntity(LinkedHashMap<Object,Object> fields) {
        FIELDS_REFERENCE.get().putAll(fields);
        //MULTIPLE_FIELDS = null;
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

    public final void setField(Object key,Object value){
        FIELDS_REFERENCE.get().put(key,value);
    }
}
