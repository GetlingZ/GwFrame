package com.getling.gwframe.rv.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;

/**
 * @Author: getling
 * @CreateDate: 2019/6/19 14:29
 * @Description:
 */
public class DividerDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;

    private Paint paint;

    public DividerDecoration() {
        dividerHeight = AdaptScreenUtils.pt2Px(1);

        paint = new Paint();
        paint.setColor(Color.parseColor("#f0f0f0"));
    }

    public DividerDecoration(int dividerHeight) {
        this.dividerHeight = dividerHeight;

        paint = new Paint();
        paint.setColor(Color.parseColor("#f0f0f0"));
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            if (view == null) {
                continue;
            }
            int index = parent.getChildAdapterPosition(view);
            float left = parent.getPaddingLeft();
            float right = view.getRight();
            float top = view.getBottom();
            float bottom = top + dividerHeight;
            if (index != parent.getAdapter().getItemCount() - 1) {
                c.drawRect(left, top, right, bottom, paint);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
//        if (parent.getAdapter() != null) {
//            int count = parent.getAdapter().getItemCount();
//            if (position == count - 1) {
//                outRect.bottom = dividerHeight;
//            } else {
//                outRect.bottom = 0;
//            }
//        }
        outRect.bottom = dividerHeight;
    }
}
