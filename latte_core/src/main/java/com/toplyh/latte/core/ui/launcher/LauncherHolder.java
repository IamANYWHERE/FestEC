package com.toplyh.latte.core.ui.launcher;

import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

public class LauncherHolder extends Holder<Integer> {

    private AppCompatImageView mImageView = null;

    public LauncherHolder(View itemView) {
        super(itemView);
        mImageView = (AppCompatImageView) itemView;
    }

    @Override
    protected void initView(View itemView) {
    }

    @Override
    public void updateUI(Integer data) {
        if (mImageView != null) {
            mImageView.setBackgroundResource(data);
        }
    }
}
