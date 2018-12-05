package com.toplyh.latte.ec.main.personal.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.toplyh.latte.core.fragments.LatteDelegate;
import com.toplyh.latte.core.util.callback.CallbackManager;
import com.toplyh.latte.core.util.callback.CallbackType;
import com.toplyh.latte.core.util.callback.IGlobalCallback;
import com.toplyh.latte.core.util.log.LatteLogger;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;
import com.toplyh.latte.ui.widget.AutoPhotoLayout;
import com.toplyh.latte.ui.widget.StarLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderCommentDelegate extends LatteDelegate {

    @BindView(R2.id.custom_star_layout)
    StarLayout mStarLayout = null;
    @BindView(R2.id.custom_auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;

    @OnClick(R2.id.top_tv_comment_submit)
    void onClickSubmit(){
        LatteLogger.d("评分："+mStarLayout.getStarCount());
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance().addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
            @Override
            public void execute(@Nullable Uri args) {
                mAutoPhotoLayout.onCropTarget(args);
            }
        });
    }
}
