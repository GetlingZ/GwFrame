package com.getling.gwframe.imagepreview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ScreenUtils;

/**
 * @Author: getling
 * @CreateDate: 2020/1/16 16:46
 * @Description:
 */
public class MyProgressView extends View {

    public MyProgressView(Context context) {
        super(context);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int progress = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        float x = ScreenUtils.getScreenWidth() / 2;
        float y = ScreenUtils.getScreenHeight() / 2;
        float outRadius = 40;
        float inRadius = 35;
        float angle = 360 * progress / 100;
        canvas.drawCircle(x, y, outRadius, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(x - inRadius, y - inRadius, x + inRadius, y + inRadius, -90, angle, true, paint);
    }

    public void setProgress(int progress) {
        if (progress < 0) {
            progress = 0;
        }
        if (progress > 100) {
            progress = 100;
        }
        this.progress = progress;
        invalidate();
    }
}
