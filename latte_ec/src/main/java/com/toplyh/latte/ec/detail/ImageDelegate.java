package com.toplyh.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.toplyh.latte.core.fragments.LatteDelegate;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;
import com.toplyh.latte.ui.recycler.ItemType;
import com.toplyh.latte.ui.recycler.MultipleFields;
import com.toplyh.latte.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

import butterknife.BindView;

public class ImageDelegate extends LatteDelegate {

    private static final String ARG_PICTURES = "ARG_PICTURES";

    @BindView(R2.id.rv_image_container)
    RecyclerView mRecyclerView = null;
    public static ImageDelegate create(ArrayList<String> pictures){
        final Bundle args = new Bundle();
        args.putStringArrayList(ARG_PICTURES,pictures);
        final ImageDelegate delegate= new ImageDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    private void initImage(){
        final ArrayList<String> pictures =
                getArguments().getStringArrayList(ARG_PICTURES);
        final ArrayList<MultipleItemEntity> entities = new ArrayList<>();
        final int size;
        if (pictures!=null){
            size = pictures.size();
            for (int i=0;i<size;i++){
                final String imageUrl = pictures.get(i);
                final MultipleItemEntity entity = MultipleItemEntity
                        .builder()
                        .setItemType(ItemType.SINGLE_BIG_IMAGE)
                        .setField(MultipleFields.IMAGE_URL,imageUrl)
                        .build();
                entities.add(entity);
            }
            final RecyclerImageAdapter adapter = new RecyclerImageAdapter(entities);
            mRecyclerView.setAdapter(adapter);
        }
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_page;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        initImage();
    }
}
