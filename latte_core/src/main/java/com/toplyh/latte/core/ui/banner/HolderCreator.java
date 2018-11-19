package com.toplyh.latte.core.ui.banner;

import android.content.Context;
import android.view.View;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.toplyh.latte.core.R;

import java.lang.ref.WeakReference;

public class HolderCreator implements CBViewHolderCreator {

    private Context mContext = null;

    public HolderCreator(Context context){
        mContext = context;
    }
    @Override
    public Holder createHolder(View itemView) {
        return new ImageHolder(itemView,mContext);
    }

    @Override
    public int getLayoutId() {
        return R.layout.holder_launcher;
    }
}
