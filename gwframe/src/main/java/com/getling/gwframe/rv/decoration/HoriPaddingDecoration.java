package com.getling.gwframe.rv.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;

/**
 * @Author: getling
 * @CreateDate: 2019/6/28 8:40
 * @Description:
 */
public class HoriPaddingDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;
    private int dividerLeftPadding;

    private Paint paint;


    public HoriPaddingDecoration() {
        dividerHeight = AdaptScreenUtils.pt2Px(1);
        dividerLeftPadding = AdaptScreenUtils.pt2Px(16);
        paint = new Paint();
        paint.setColor(Color.parseColor("#E2E2E2"));
    }

    @Override
    public void onDraw(Canvas c, @NonNull RecyclerView parent, RecyclerView.State state) {
        RecyclerView.Adapter adapter = parent.getAdapter();
        int count;
        if (adapter != null) {
            count = adapter.getItemCount();
        } else {
            count = 0;
        }
        for (int i = 0; i < count; i++) {
            View view = parent.getChildAt(i);
            if (view == null) {
                continue;
            }
            int index = parent.getChildAdapterPosition(view);
            float left = parent.getPaddingLeft() + dividerLeftPadding;
            float right = view.getRight();
            float top = view.getBottom();
            float bottom = top + dividerHeight;
            c.drawRect(left, top, right, bottom, paint);
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
