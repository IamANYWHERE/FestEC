package com.toplyh.latte.core.delegates.bottom;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.toplyh.latte.core.R;
import com.toplyh.latte.core.delegates.LatteDelegate;

/**
 * This will stop working if you have a child view within the fragment that can gain focus.
 * For example if you have a "list state" and "edit state" in the fragment,
 * you go into the "edit state" that has an EditText within it, select the EditText,
 * the main view will lose focus and the onKey() will stop functioning unless you manually regain focus of the base view.
 */
public abstract class BottomItemDelegate extends LatteDelegate {

    private long mExitTime = 0;
    private static final long EXIT_TIME = 2000;

    /**
     * 每次回来的时候需要把focus重新request
     */
//    @Override
//    public void onResume() {
//        super.onResume();
//        final View rootView = getView();
//        if (rootView != null) {
//            rootView.setFocusableInTouchMode(true);
//            rootView.requestFocus();
//            rootView.setOnKeyListener(this);
//        }
//    }
//
//    @Override
//    public boolean onKey(View view, int i, KeyEvent keyEvent) {
//        if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
//            if (System.currentTimeMillis() - mExitTime > EXIT_TIME) {
//                Toast.makeText(getContext(), "双击退出" + getString(R.string.app_name), Toast.LENGTH_SHORT).show();
//                mExitTime = System.currentTimeMillis();
//            } else {
//                _mActivity.finish();
//                if (mExitTime != 0) {
//                    mExitTime = 0;
//                }
//            }
//            return true;
//        }
//        return false;
//    }

    /**为true则消费掉该事件
     * 新版解决掉了回退无效事件
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - mExitTime > EXIT_TIME) {
            mExitTime = System.currentTimeMillis();
            Toast.makeText(getContext(), "双击退出" + getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        } else {
            _mActivity.finish();
        }
        return true;
    }
}
