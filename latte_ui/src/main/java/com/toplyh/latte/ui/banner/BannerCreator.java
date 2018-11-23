package com.toplyh.latte.ui.banner;

import android.content.Context;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.toplyh.latte.core.R;

import java.util.ArrayList;

public class BannerCreator {

    public static void setDefault(Context context,
                                  ConvenientBanner<String> convenientBanner,
                                  ArrayList<String> banners,
                                  OnItemClickListener listener){
        convenientBanner.setPages(new HolderCreator(context),banners)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(listener)
                .startTurning(3000)
                .setCanLoop(true);
    }
}
