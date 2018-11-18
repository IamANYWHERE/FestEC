package com.toplyh.latte.core.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;
import com.toplyh.latte.core.R;
import com.toplyh.latte.core.R2;
import com.toplyh.latte.core.delegates.LatteDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {

    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomItemDelegate> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mClickedColor = Color.RED;
    private int mUnClickedColor = Color.GRAY;

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar = null;

    public abstract LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder);

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract int setClickedColor();

    @ColorInt
    public abstract int setUnClickedColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }
        if (setUnClickedColor() != 0) {
            mUnClickedColor = setUnClickedColor();
        }

        final ItemBuilder builder = new ItemBuilder();
        final LinkedHashMap<BottomTabBean, BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemDelegate> item : ITEMS.entrySet()) {
            final BottomTabBean bean = item.getKey();
            final BottomItemDelegate delegate = item.getValue();
            TAB_BEANS.add(bean);
            ITEM_DELEGATES.add(delegate);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_item;
    }

    /**
     * 这里解释一下inflate2个参数和三个参数的区别
     * 三个参数时，如果root不为null,那么当attachToRoot为true时自动将resource中的视图加入root中，
     * 如果attachToRoot为false时不会把resource视图加入root，如果需要加入，则自己手动加入。
     * 如果root为null时，那么此时resource布局中的layout_width和layout_height会失效，因为这些参数表示
     * 在父布局中自己的宽高，而此时没有父布局，所以失效。
     * 如果时2个参数，则会调用三个参数的inflate，第三个参数的值会根据root确定，为null则为false，不为null则为true
     *
     * @param savedInstanceState
     * @param rootView
     */
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemText = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = TAB_BEANS.get(i);
            //初始化事件
            itemIcon.setText(bean.getIcon());
            itemText.setText(bean.getTitle());
            if (i == mIndexDelegate) {
                itemIcon.setTextColor(mClickedColor);
                itemText.setTextColor(mClickedColor);
            }else {
                itemIcon.setTextColor(mUnClickedColor);
                itemText.setTextColor(mUnClickedColor);
            }
        }

        final SupportFragment[] delegateArray = ITEM_DELEGATES.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, delegateArray);
    }

    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
            final TextView itemText = (TextView) item.getChildAt(1);
            itemIcon.setTextColor(mUnClickedColor);
            itemText.setTextColor(mUnClickedColor);
        }
    }

    @Override
    public void onClick(View view) {
        final int tag = (int) view.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) view;
        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
        final TextView itemText = (TextView) item.getChildAt(1);
        itemIcon.setTextColor(mClickedColor);
        itemText.setTextColor(mClickedColor);
        showHideFragment(ITEM_DELEGATES.get(tag), ITEM_DELEGATES.get(mCurrentDelegate));
        //注意先后顺序
        mCurrentDelegate = tag;
    }
}
