package com.toplyh.latte.core.ui.scanner;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

public class LatteViewFinderView extends ViewFinderView {
    public LatteViewFinderView(Context context) {
        this(context, null);
    }

    public LatteViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mSquareViewFinder = true;
        setBorderColor(ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark));
        setLaserColor(ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark));
    }


}
