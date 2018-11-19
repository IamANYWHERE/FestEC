package com.toplyh.latte.core.image;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.toplyh.latte.core.R;

public final class ImageHelper {

    public static void LoadCacheAll(Context context, String url, ImageView view){
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .into(view);
    }
}
