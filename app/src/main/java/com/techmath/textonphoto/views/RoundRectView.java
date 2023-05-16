package com.techmath.textonphoto.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.techmath.textonphoto.R;


public class RoundRectView extends ShapeOfView {
    @ColorInt
    private int borderColor = -1;
    private final Paint borderPaint = new Paint(1);
    private final Path borderPath = new Path();
    private final RectF borderRectF = new RectF();
    private float borderWidthPx = 0.0f;

    public float bottomLeftRadius = 0.0f;

    public float bottomRightRadius = 0.0f;

    public final RectF rectF = new RectF();

    public float topLeftRadius = 0.0f;

    public float topRightRadius = 0.0f;

    public RoundRectView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RoundRectView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public RoundRectView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    @SuppressLint("ResourceType")
    private void init(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            int[] RoundRectView = {R
                    .attr.shape_roundRect_borderColor, R.attr.shape_roundRect_borderWidth, R.attr.shape_roundRect_bottomLeftRadius, R.attr.shape_roundRect_bottomRightRadius, R.attr.shape_roundRect_topLeftRadius, R.attr.shape_roundRect_topRightRadius};
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, RoundRectView);
            this.topLeftRadius = (float) obtainStyledAttributes.getDimensionPixelSize(4, (int) this.topLeftRadius);
            this.topRightRadius = (float) obtainStyledAttributes.getDimensionPixelSize(5, (int) this.topRightRadius);
            this.bottomLeftRadius = (float) obtainStyledAttributes.getDimensionPixelSize(2, (int) this.bottomLeftRadius);
            this.bottomRightRadius = (float) obtainStyledAttributes.getDimensionPixelSize(3, (int) this.bottomRightRadius);
            this.borderColor = obtainStyledAttributes.getColor(0, this.borderColor);
            this.borderWidthPx = (float) obtainStyledAttributes.getDimensionPixelSize(1, (int) this.borderWidthPx);
            obtainStyledAttributes.recycle();
        }
        this.borderPaint.setStyle(Paint.Style.STROKE);
        super.setClipPathCreator(new ClipPathManager.ClipPathCreator() {
            public boolean requiresBitmap() {
                return false;
            }

            public Path createClipPath(int i, int i2) {
                float f = (float) i;
                float f2 = (float) i2;
                RoundRectView.this.rectF.set(0.0f, 0.0f, f, f2);
                RoundRectView roundRectView = RoundRectView.this;
                RoundRectView roundRectView2 = RoundRectView.this;
                float a2 = roundRectView2.min(roundRectView2.topLeftRadius, f, f2);
                RoundRectView roundRectView3 = RoundRectView.this;
                float a3 = roundRectView3.min(roundRectView3.topRightRadius, f, f2);
                RoundRectView roundRectView4 = RoundRectView.this;
                float a4 = roundRectView4.min(roundRectView4.bottomRightRadius, f, f2);
                RoundRectView roundRectView5 = RoundRectView.this;
                return roundRectView.generatePath(roundRectView.rectF, a2, a3, a4, roundRectView5.min(roundRectView5.bottomLeftRadius, f, f2));
            }
        });
    }


    public float min(float f, float f2, float f3) {
        return Math.min(f, Math.min(f2, f3));
    }

    public void requiresShapeUpdate() {
        float f = this.borderWidthPx;
        this.borderRectF.set(f / 2.0f, f / 2.0f, ((float) getWidth()) - (this.borderWidthPx / 2.0f), ((float) getHeight()) - (this.borderWidthPx / 2.0f));
        this.borderPath.set(generatePath(this.borderRectF, this.topLeftRadius, this.topRightRadius, this.bottomRightRadius, this.bottomLeftRadius));
        super.requiresShapeUpdate();
    }


    public void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        float f = this.borderWidthPx;
        if (f > 0.0f) {
            this.borderPaint.setStrokeWidth(f);
            this.borderPaint.setColor(this.borderColor);
            canvas.drawPath(this.borderPath, this.borderPaint);
        }
    }


    public Path generatePath(RectF rectF2, float f, float f2, float f3, float f4) {
        return generatePath(false, rectF2, f, f2, f3, f4);
    }

    private Path generatePath(boolean z, RectF rectF2, float f, float f2, float f3, float f4) {
        Path path = new Path();
        float f5 = rectF2.left;
        float f6 = rectF2.top;
        float f7 = rectF2.bottom;
        float f8 = rectF2.right;
        float min = Math.min(rectF2.width() / 2.0f, rectF2.height() / 2.0f);
        float abs = Math.abs(f);
        float abs2 = Math.abs(f2);
        float abs3 = Math.abs(f4);
        float abs4 = Math.abs(f3);
        if (abs > min) {
            abs = min;
        }
        if (abs2 > min) {
            abs2 = min;
        }
        if (abs3 > min) {
            abs3 = min;
        }
        if (abs4 > min) {
            abs4 = min;
        }
        float f9 = f5 + abs;
        path.moveTo(f9, f6);
        path.lineTo(f8 - abs2, f6);
        float f10 = 90.0f;
        if (z) {
            path.quadTo(f8, f6, f8, abs2 + f6);
        } else {
            float f11 = abs2 * 2.0f;
            path.arcTo(new RectF(f8 - f11, f6, f8, f11 + f6), -90.0f, f2 > 0.0f ? 90.0f : -270.0f);
        }
        path.lineTo(f8, f7 - abs4);
        if (z) {
            path.quadTo(f8, f7, f8 - abs4, f7);
        } else {
            float f12 = abs4 > 0.0f ? 90.0f : -270.0f;
            float f13 = abs4 * 2.0f;
            path.arcTo(new RectF(f8 - f13, f7 - f13, f8, f7), 0.0f, f12);
        }
        path.lineTo(f5 + abs3, f7);
        if (z) {
            path.quadTo(f5, f7, f5, f7 - abs3);
        } else {
            float f14 = abs3 > 0.0f ? 90.0f : -270.0f;
            float f15 = abs3 * 2.0f;
            path.arcTo(new RectF(f5, f7 - f15, f15 + f5, f7), 90.0f, f14);
        }
        path.lineTo(f5, f6 + abs);
        if (z) {
            path.quadTo(f5, f6, f9, f6);
        } else {
            if (abs <= 0.0f) {
                f10 = -270.0f;
            }
            float f16 = abs * 2.0f;
            path.arcTo(new RectF(f5, f6, f5 + f16, f16 + f6), 180.0f, f10);
        }
        path.close();
        return path;
    }


    public float getTopRightRadius() {
        return this.topRightRadius;
    }

    public void setTopRightRadius(float f) {
        this.topRightRadius = f;
        requiresShapeUpdate();
    }


    public float getBottomLeftRadius() {
        return this.bottomLeftRadius;
    }

    public void setBottomLeftRadius(float f) {
        this.bottomLeftRadius = f;
        requiresShapeUpdate();
    }


    public float getBorderColor() {
        return (float) this.borderColor;
    }

    public void setBorderColor(int i) {
        this.borderColor = i;
        requiresShapeUpdate();
    }


    public void setBorderWidth(float f) {
        this.borderWidthPx = f;
        requiresShapeUpdate();
    }


}
