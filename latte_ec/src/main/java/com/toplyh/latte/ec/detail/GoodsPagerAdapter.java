package com.toplyh.latte.ec.detail;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

/**
 * 使用fragmentstate是为了不保存每一个pager的状态
 * 应为对于商品详情页面来说是一种用户快速浏览消费的界面
 * 如果保存状态，有可能出现数据重复或者得不到更新
 */
public class GoodsPagerAdapter extends FragmentStatePagerAdapter {


    private final ArrayList<String> TAB_TITLES = new ArrayList<>();
    private final ArrayList<ArrayList<String>> PICTURES = new ArrayList<>();

    public GoodsPagerAdapter(FragmentManager fm, JSONObject data) {
        super(fm);
        final JSONArray tabs = data.getJSONArray("tabs");
        final int size = tabs.size();
        for (int i = 0; i < size; i++) {
            final JSONObject tab = tabs.getJSONObject(i);
            final String title = tab.getString("name");
            final JSONArray array = tab.getJSONArray("pictures");
            final ArrayList<String> list = new ArrayList<>();
            final int pictureSize = array.size();
            for (int j = 0;j<pictureSize;j++){
                list.add(array.getString(j));
            }
            TAB_TITLES.add(title);
            PICTURES.add(list);
        }
    }

    @Override
    public Fragment getItem(int i) {
        return ImageDelegate.create(PICTURES.get(i));
    }

    @Override
    public int getCount() {
        return TAB_TITLES.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position);
    }
}
