package com.techmath.textonphoto.photoeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import java.util.Stack;

public class BrushDrawingView extends View {
    private static final float TOUCH_TOLERANCE = 4.0f;
    private boolean mBrushDrawMode;
    private float mBrushEraserSize;
    private float mBrushSize;
    private BrushViewChangeListener mBrushViewChangeListener;
    private Canvas mDrawCanvas;
    private Paint mDrawPaint;
    private final Stack<LinePath> mDrawnPaths;
    private int mOpacity;
    private Path mPath;
    private final Stack<LinePath> mRedoPaths;
    private float mTouchX;
    private float mTouchY;

    public BrushDrawingView(Context context) {
        this(context,  null);
    }

    public BrushDrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBrushSize = 25.0f;
        this.mBrushEraserSize = 50.0f;
        this.mOpacity = 255;
        this.mDrawnPaths = new Stack<>();
        this.mRedoPaths = new Stack<>();
        setupBrushDrawing();
    }

    public BrushDrawingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBrushSize = 25.0f;
        this.mBrushEraserSize = 50.0f;
        this.mOpacity = 255;
        this.mDrawnPaths = new Stack<>();
        this.mRedoPaths = new Stack<>();
        setupBrushDrawing();
    }

    @SuppressLint("WrongConstant")
    private void setupBrushDrawing() {
        setLayerType(2,  null);
        this.mDrawPaint = new Paint();
        this.mPath = new Path();
        this.mDrawPaint.setAntiAlias(true);
        this.mDrawPaint.setDither(true);
        this.mDrawPaint.setColor(-16777216);
        this.mDrawPaint.setStyle(Paint.Style.STROKE);
        this.mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mDrawPaint.setStrokeWidth(this.mBrushSize);
        this.mDrawPaint.setAlpha(this.mOpacity);
        this.mDrawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        setVisibility(View.GONE);
    }

    private void refreshBrushDrawing() {
        this.mBrushDrawMode = true;
        this.mPath = new Path();
        this.mDrawPaint.setAntiAlias(true);
        this.mDrawPaint.setDither(true);
        this.mDrawPaint.setStyle(Paint.Style.STROKE);
        this.mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        this.mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mDrawPaint.setStrokeWidth(this.mBrushSize);
        this.mDrawPaint.setAlpha(this.mOpacity);
        this.mDrawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
    }


    public void brushEraser() {
        this.mBrushDrawMode = true;
        this.mDrawPaint.setStrokeWidth(this.mBrushEraserSize);
        this.mDrawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }


    public void setBrushDrawingMode(boolean brushDrawMode) {
        this.mBrushDrawMode = brushDrawMode;
        if (brushDrawMode) {
            setVisibility(View.VISIBLE);
            refreshBrushDrawing();
        }
    }


    public void setOpacity(@IntRange(from = 0, to = 255) int opacity) {
        this.mOpacity = opacity;
        setBrushDrawingMode(true);
    }


    public boolean getBrushDrawingMode() {
        return this.mBrushDrawMode;
    }


    public void setBrushSize(float size) {
        this.mBrushSize = size;
        setBrushDrawingMode(true);
    }


    public void setBrushColor(@ColorInt int i) {
        this.mDrawPaint.setColor(i);
        setBrushDrawingMode(true);
    }


    public void setBrushEraserSize(float f) {
        this.mBrushEraserSize = f;
        setBrushDrawingMode(true);
    }


    public void setBrushEraserColor(@ColorInt int i) {
        this.mDrawPaint.setColor(i);
        setBrushDrawingMode(true);
    }


    public float getEraserSize() {
        return this.mBrushEraserSize;
    }


    public float getBrushSize() {
        return this.mBrushSize;
    }


    public int getBrushColor() {
        return this.mDrawPaint.getColor();
    }


    public void clearAll() {
        this.mDrawnPaths.clear();
        this.mRedoPaths.clear();
        Canvas canvas = this.mDrawCanvas;
        if (canvas != null) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        }
        invalidate();
    }


    public void setBrushViewChangeListener(BrushViewChangeListener brushViewChangeListener) {
        this.mBrushViewChangeListener = brushViewChangeListener;
    }

@Override
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mDrawCanvas = new Canvas(Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888));
    }

    @Override
    public void onDraw(Canvas canvas) {
        for (LinePath linePath : this.mDrawnPaths) {
            canvas.drawPath(linePath.getDrawPath(), linePath.getDrawPain());
        }
        canvas.drawPath(this.mPath, this.mDrawPaint);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (!this.mBrushDrawMode) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction()) {
            case 0:
                touchStart(x, y);
                break;
            case 1:
                touchUp();
                break;
            case 2:
                touchMove(x, y);
                break;
        }
        invalidate();
        return true;
    }

    private static class LinePath {
        private final Paint mDrawPaint;
        private final Path mDrawPath;

        LinePath(Path path, Paint paint) {
            this.mDrawPaint = new Paint(paint);
            this.mDrawPath = new Path(path);
        }


        public Paint getDrawPain() {
            return this.mDrawPaint;
        }


        public Path getDrawPath() {
            return this.mDrawPath;
        }
    }


    public boolean undo() {
        if (!this.mDrawnPaths.empty()) {
            this.mRedoPaths.push(this.mDrawnPaths.pop());
            invalidate();
        }
        BrushViewChangeListener brushViewChangeListener = this.mBrushViewChangeListener;
        if (brushViewChangeListener != null) {
            brushViewChangeListener.onViewRemoved(this);
        }
        return !this.mDrawnPaths.empty();
    }


    public boolean redo() {
        if (!this.mRedoPaths.empty()) {
            this.mDrawnPaths.push(this.mRedoPaths.pop());
            invalidate();
        }
        BrushViewChangeListener brushViewChangeListener = this.mBrushViewChangeListener;
        if (brushViewChangeListener != null) {
            brushViewChangeListener.onViewAdd(this);
        }
        return !this.mRedoPaths.empty();
    }

    private void touchStart(float f, float f2) {
        this.mRedoPaths.clear();
        this.mPath.reset();
        this.mPath.moveTo(f, f2);
        this.mTouchX = f;
        this.mTouchY = f2;
        BrushViewChangeListener brushViewChangeListener = this.mBrushViewChangeListener;
        if (brushViewChangeListener != null) {
            brushViewChangeListener.onStartDrawing();
        }
    }

    private void touchMove(float f, float f2) {
        float abs = Math.abs(f - this.mTouchX);
        float abs2 = Math.abs(f2 - this.mTouchY);
        if (abs >= TOUCH_TOLERANCE || abs2 >= TOUCH_TOLERANCE) {
            Path path = this.mPath;
            float f3 = this.mTouchX;
            float f4 = this.mTouchY;
            path.quadTo(f3, f4, (f + f3) / 2.0f, (f2 + f4) / 2.0f);
            this.mTouchX = f;
            this.mTouchY = f2;
        }
    }

    private void touchUp() {
        this.mPath.lineTo(this.mTouchX, this.mTouchY);
        this.mDrawCanvas.drawPath(this.mPath, this.mDrawPaint);
        this.mDrawnPaths.push(new LinePath(this.mPath, this.mDrawPaint));
        this.mPath = new Path();
        BrushViewChangeListener brushViewChangeListener = this.mBrushViewChangeListener;
        if (brushViewChangeListener != null) {
            brushViewChangeListener.onStopDrawing();
            this.mBrushViewChangeListener.onViewAdd(this);
        }
    }
}
