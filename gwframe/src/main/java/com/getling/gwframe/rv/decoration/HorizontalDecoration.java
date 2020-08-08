package com.getling.gwframe.rv.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AdaptScreenUtils;

import org.jetbrains.annotations.NotNull;

/**
 * @author getling
 * @date 2019/02/26
 * @description 水平分割线
 */
public class HorizontalDecoration extends RecyclerView.ItemDecoration {

    private int smallHeight;

    private Paint paint;

    public HorizontalDecoration() {
        this(8);
    }

    public HorizontalDecoration(int height) {
        smallHeight = AdaptScreenUtils.pt2Px(height);

        paint = new Paint();
        paint.setColor(Color.parseColor("#E2E2E2"));
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
     * 如果是最后一个条目就不绘制
     */
    @Override
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, RecyclerView parent, @NotNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        if (parent.getAdapter() != null) {
            int count = parent.getAdapter().getItemCount();
            if (position == count - 1) {
                outRect.bottom = 0;
            } else {
                outRect.bottom = smallHeight;
            }
        }
    }
}
