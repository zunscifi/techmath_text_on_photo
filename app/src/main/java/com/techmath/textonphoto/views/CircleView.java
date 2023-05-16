package com.techmath.textonphoto.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.techmath.textonphoto.R;


public class CircleView extends ShapeOfView {
    @ColorInt
    private int borderColor = -1;
    private final Paint borderPaint = new Paint(1);
    private float borderWidthPx = 0.0f;

    public CircleView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public CircleView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public CircleView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    @SuppressLint("ResourceType")
    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            int[] circleView = {R.attr.shape_circle_borderColor, R.attr.shape_circle_borderWidth};
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, circleView);
            this.borderWidthPx = (float) obtainStyledAttributes.getDimensionPixelSize(1, (int) this.borderWidthPx);
            this.borderColor = obtainStyledAttributes.getColor(0, this.borderColor);
            obtainStyledAttributes.recycle();
        }
        this.borderPaint.setAntiAlias(true);
        this.borderPaint.setStyle(Paint.Style.STROKE);
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            public boolean requiresBitmap() {
                return false;
            }

            public Path createClipPath(int i, int i2) {
                Path path = new Path();
                float f = ((float) i) / 2.0f;
                float f2 = ((float) i2) / 2.0f;
                path.addCircle(f, f2, Math.min(f, f2), Path.Direction.CW);
                return path;
            }
        });
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        float f = this.borderWidthPx;
        if (f > 0.0f) {
            this.borderPaint.setStrokeWidth(f);
            this.borderPaint.setColor(this.borderColor);
            canvas.drawCircle(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f, Math.min((((float) getWidth()) - this.borderWidthPx) / 2.0f, (((float) getHeight()) - this.borderWidthPx) / 2.0f), this.borderPaint);
        }
    }

}
