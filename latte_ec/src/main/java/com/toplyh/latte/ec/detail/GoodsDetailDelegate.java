package com.toplyh.latte.ec.detail;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.widget.IconTextView;
import com.toplyh.latte.core.fragments.LatteDelegate;
import com.toplyh.latte.core.image.ImageHelper;
import com.toplyh.latte.core.net.RestClient;
import com.toplyh.latte.core.net.callback.ISuccess;
import com.toplyh.latte.ec.R;
import com.toplyh.latte.ec.R2;
import com.toplyh.latte.ui.animation.BezierAnimation;
import com.toplyh.latte.ui.animation.BezierUtil;
import com.toplyh.latte.ui.banner.BannerCreator;
import com.toplyh.latte.ui.widget.CircleTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class GoodsDetailDelegate extends LatteDelegate implements
        AppBarLayout.OnOffsetChangedListener,
        BezierUtil.AnimationListener {

    private static final String ARG_GOODS_ID = "goodsId";
    private int mGoodsId = -1;

    @BindView(R2.id.goods_detail_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout = null;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager = null;
    @BindView(R2.id.detail_banner)
    ConvenientBanner<String> mBanner = null;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppbar = null;

    //底部
    @BindView(R2.id.icon_favor)
    IconTextView mIconFavor = null;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mCircleTextView = null;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRlAddShopCart = null;
    @BindView(R2.id.icon_shop_cart)
    IconTextView mIconShopCart = null;

    private String mGoodsThumbUrl = null;
    private int mShopCount = 0;


    @OnClick(R2.id.rl_add_shop_cart)
    void onClickShopCart() {
        final CircleImageView animImg = new CircleImageView(getContext());
        ImageHelper.LoadCacheAndOverride(getContext(), mGoodsThumbUrl, animImg);
        BezierAnimation.addCart(this, mRlAddShopCart, mIconShopCart, animImg, this);
    }

    private void setShopCartCount(JSONObject data) {
        mGoodsThumbUrl = data.getString("thumb");
        if (mShopCount == 0) {
            mCircleTextView.setVisibility(View.GONE);
        }
    }

    public static GoodsDetailDelegate create(int goodsId) {
        final Bundle args = new Bundle();
        args.putInt(ARG_GOODS_ID, goodsId);
        final GoodsDetailDelegate delegate = new GoodsDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mGoodsId = args.getInt(ARG_GOODS_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        mAppbar.addOnOffsetChangedListener(this);
        mCircleTextView.setCircleBackground(Color.RED);
        initData();
        initTabLayout();
        initToolbar();
    }

    private void initData() {
        RestClient.builder()
                .url("data/goods_detail_data_1.json")
                .params("goods_id", mGoodsId)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject data = JSON.parseObject(response).getJSONObject("data");
                        initBanner(data);
                        initGoodsData(data);
                        initViewPager(data);
                        setShopCartCount(data);
                    }
                })
                .build()
                .get();
    }

    private void initToolbar() {
        final IconDrawable chevron = new IconDrawable(getContext(), FontAwesomeIcons.fa_chevron_left);
        chevron.actionBarSize().color(ContextCompat.getColor(getContext(), R.color.app_main));
        mToolbar.setNavigationIcon(chevron);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    private void initViewPager(JSONObject data) {
        final GoodsPagerAdapter adapter = new GoodsPagerAdapter(getChildFragmentManager(), data);
        mViewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(
                ContextCompat.getColor(getContext(), R.color.app_main));
        mTabLayout.setTabTextColors(Color.BLACK, ContextCompat.getColor(getContext(), R.color.app_main));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
        //如果想要变化的Indicator添加 TabWidthHelper.setTabWidth(mTabLayout,0);
    }

    private void initGoodsData(JSONObject data) {
        final String goodsData = data.toJSONString();
        loadRootFragment(R.id.frame_goods_info, GoodsInfoDelegate.create(goodsData));
    }

    private void initBanner(JSONObject data) {
        final JSONArray array = data.getJSONArray("banners");
        final List<String> images = new ArrayList<>();
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            images.add(array.getString(i));
        }
        BannerCreator.setDefault(getContext(), mBanner, images, null);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {

    }

    @Override
    public void onAnimationEnd() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                Glider.glide(Skill.BackEaseInOut, 500, ObjectAnimator.ofFloat(
                        mIconShopCart,
                        "scaleX",
                        0.8f, 1f, 1.4f, 1.2f, 1f)),
                Glider.glide(Skill.BackEaseInOut, 500, ObjectAnimator.ofFloat(
                        mIconShopCart,
                        "scaleY",
                        0.8f, 1f, 1.4f, 1.2f, 1f))
        );
        set.setDuration(500);
        set.start();
        mShopCount++;
        mCircleTextView.setVisibility(View.VISIBLE);
        mCircleTextView.setText(String.valueOf(mShopCount));
        //TODO 通知服务器
    }
}
