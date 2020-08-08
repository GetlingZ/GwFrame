package com.getling.gwframe.rv.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.getling.gwframe.R;

/**
 * @Author: getling
 * @CreateDate: 2019/6/3 11:00
 * @Description:
 */
public class GridDecoration extends RecyclerView.ItemDecoration {
    private int dividerWidth;
    private Paint paint;
    private int spanCount;

    public GridDecoration(Context context, int spanCount) {
        this.spanCount = spanCount;
        dividerWidth = AdaptScreenUtils.pt2Px(1);

        paint = new Paint();
        paint.setColor(ContextCompat.getColor(context, R.color.color_E2E2E2));
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
            if (index % spanCount != spanCount - 1) {
                float r_left = view.getRight();
                float r_right = r_left + dividerWidth;
                float r_top = view.getTop();
                float r_bottom = view.getBottom() + dividerWidth;
                c.drawRect(r_left, r_top, r_right, r_bottom, paint);
            }
            float b_left = view.getLeft();
            float b_top = view.getBottom();
            float b_right = view.getRight();
            float b_bottom = b_top + dividerWidth;
            c.drawRect(b_left, b_top, b_right, b_bottom, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (position % spanCount != spanCount - 1) {
            outRect.right = dividerWidth;
        }
        outRect.bottom = dividerWidth;
    }
}
