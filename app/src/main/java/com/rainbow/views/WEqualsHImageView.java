package com.rainbow.views;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class WEqualsHImageView extends AppCompatImageView {
    public WEqualsHImageView(Context context) {
        super(context);
    }

    public WEqualsHImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WEqualsHImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);

    }
}
