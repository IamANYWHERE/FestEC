package com.toplyh.latte.core.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.toplyh.latte.core.image.ImageHelper;

import java.lang.ref.WeakReference;

public class ImageHolder extends Holder<String> {

    private Context mContext = null;
    private AppCompatImageView mImageView = null;

    ImageHolder(View itemView, Context context) {
        super(itemView);
        mImageView = (AppCompatImageView) itemView;
        mContext = context;
    }

    @Override
    protected void initView(View itemView) {

    }

    @Override
    public void updateUI(String data) {
        ImageHelper.LoadCacheAll(mContext,data,mImageView);
    }
}
