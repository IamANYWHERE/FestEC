package com.toplyh.latte.core.util.tab;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

public class TabWidthHelper {

    public static void setTabWidth(final TabLayout tabLayout, final int padding) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    final LinearLayout tabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    final int size = tabStrip.getChildCount();
                    for (int i = 0; i < size; i++) {
                        View tabView = tabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性,tab的数字不固定一定用反射取mTextView
                        Field textViewField = tabView.getClass().getDeclaredField("mTextView");
                        textViewField.setAccessible(true);

                        TextView textView = (TextView) textViewField.get(tabView);

                        tabView.setPadding(0,0,0,0);

                        //字多宽，线就多长，所以测量mTextView的宽度
                        int width = 0;
                        width = textView.getWidth();
                        if (width == 0){
                            textView.measure(0,0);
                            width = textView.getMeasuredWidth();
                        }

                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = padding;
                        params.rightMargin = padding;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
