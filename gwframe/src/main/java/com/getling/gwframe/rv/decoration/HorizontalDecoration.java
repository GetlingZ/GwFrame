package com.getling.gwframe.rv.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ColorUtils;
import com.getling.gwframe.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author getling
 * @date 2019/02/26
 * @description 水平分割线
 */
public class HorizontalDecoration extends RecyclerView.ItemDecoration {

    private int smallHeight;

    private Paint paint;

    private boolean drawLast = false;

    public HorizontalDecoration() {
        this(1);
    }

    public HorizontalDecoration(int height) {
        this(height, R.color.color_E2E2E2);
    }

    public HorizontalDecoration(int height, @ColorRes int color) {
        smallHeight = AdaptScreenUtils.pt2Px(height);

        paint = new Paint();
        paint.setColor(ColorUtils.getColor(color));
    }

    public void setDrawLast(boolean drawLast) {
        this.drawLast = drawLast;
    }

    @Override
    public void onDraw(@NotNull Canvas c, RecyclerView parent, @NotNull RecyclerView.State state) {
        int count = parent.getChildCount();
        for (int i = 0; i < count - 1; i++) {
            View view = parent.getChildAt(i);
            if (view == null) {
                continue;
            }
            float lineLeft = parent.getPaddingLeft();
            float right = view.getRight();
            float top = view.getBottom();
            float bottom = top + smallHeight;
            c.drawRect(lineLeft, top, right, bottom, paint);
        }
    }

    /**
     * 绘制每个条目底部
     * drawLast控制最后一个条目绘制
     */
    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, RecyclerView parent, @NotNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (parent.getAdapter() != null) {
            int count = parent.getAdapter().getItemCount();
            if (position == count - 1 && !drawLast) {
                outRect.bottom = 0;
            } else {
                outRect.bottom = smallHeight;
            }
        }
    }
}
