package com.toplyh.latte.core.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.toplyh.latte.core.util.storage.LattePreference;

public final class ImageHelper {

    public static final String SIGNATURE="avatar";

    private static final RequestOptions REQUEST_OPTIONS = new RequestOptions()
            .centerCrop()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    @SuppressLint("CheckResult")
    public static void LoadCacheAll(Context context, String url, ImageView view){
        final String signature = LattePreference.getCustomAppProfile(SIGNATURE);
        final ObjectKey key = new ObjectKey(signature);
        REQUEST_OPTIONS.signature(key);
        Glide.with(context)
                .load(url)
                .apply(REQUEST_OPTIONS)
                .into(view);
    }

    public static void LoadNoCache(Fragment fragment, Uri uri, ImageView view){
        GlideApp.with(fragment)
                .load(uri)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view);
    }
    public static void LoadOnlyNoCache(Context context,String url,ImageView view){
        GlideApp.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view);
    }

    public static void LoadCacheAndOverride(Context context,String url,ImageView view){
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .override(100,100)
                .into(view);
    }
}
