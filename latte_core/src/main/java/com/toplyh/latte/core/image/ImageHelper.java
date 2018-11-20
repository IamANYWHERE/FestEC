package com.toplyh.latte.core.image;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.toplyh.latte.core.R;

public final class ImageHelper {

    private static final RequestOptions REQUEST_OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    public static void LoadCacheAll(Context context, String url, ImageView view){
        Glide.with(context)
                .load(url)
                .apply(REQUEST_OPTIONS)
                .into(view);
    }
}
