package com.getling.gwframe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.getling.gwframe.R;

/**
 * @Author: getling
 * @CreateDate: 2020/8/6 14:11
 * @Description:
 */
public class DrawableTextView extends AppCompatTextView {
    public DrawableTextView(Context context) {
        super(context);
    }

    public DrawableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public DrawableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //设置方向
    private static final int LEFT = 1, TOP = 2, RIGHT = 3, BOTTOM = 4;

    //从attrs中获取期望的drawable的宽高，资源，上下左右4个位置
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DrawableTextView);
            int mWidth = a.getDimensionPixelSize
                    (R.styleable.DrawableTextView_drawable_width, 0);
            int mHeight = a.getDimensionPixelSize
                    (R.styleable.DrawableTextView_drawable_height, 0);
            Drawable mDrawable = a.getDrawable
                    (R.styleable.DrawableTextView_drawable_src);
            int mLocation = a.getInt
                    (R.styleable.DrawableTextView_drawable_location, LEFT);
            a.recycle();
            drawDrawable(mDrawable, mWidth, mHeight, mLocation);
        }
    }

    private void drawDrawable(Drawable mDrawable, int mWidth, int mHeight, int mLocation) {
        if (mDrawable != null) {
            if (mWidth != 0 && mHeight != 0) {
                mDrawable.setBounds(0, 0, mWidth, mHeight);
            }
            switch (mLocation) {
                case LEFT:
                    this.setCompoundDrawables(mDrawable, null,
                            null, null);
                    break;
                case TOP:
                    this.setCompoundDrawables(null, mDrawable,
                            null, null);
                    break;
                case RIGHT:
                    this.setCompoundDrawables(null, null,
                            mDrawable, null);
                    break;
                case BOTTOM:
                    this.setCompoundDrawables(null, null, null,
                            mDrawable);
                    break;
            }
        }
    }
}
