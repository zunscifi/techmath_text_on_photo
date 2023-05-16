package com.techmath.textonphoto.photoeditor;

import android.content.Context;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.techmath.textonphoto.R;


public class MultiTouchListener implements View.OnTouchListener {

    private View alignView;
    private View deleteView;

    public boolean isRotateEnabled = true;

    public boolean isScaleEnabled = true;

    public boolean isTranslateEnabled = true;
    private final int[] location = new int[2];
    private int mActivePointerId = -1;

    public Context mContext;
    private final GestureDetector mGestureListener;

    public boolean mIsTextPinchZoomable;

    public OnGestureControl mOnGestureControl;
    private final OnPhotoEditorListener mOnPhotoEditorListener;
    private float mPrevRawX;
    private float mPrevRawY;
    private float mPrevX;
    private float mPrevY;
    private final ScaleGestureDetector mScaleGestureDetector;

    public float maximumScale = 10.0f;

    public float minimumScale = 0.1f;
    private OnMultiTouchListener onMultiTouchListener;
    private final Rect outRect;
    private final RelativeLayout parentView;
    private final ImageView photoEditImageView;
    private View zoomView;

    interface OnGestureControl {
        void onClick();

        void onDoubleClick();

        void onLongClick();

        void onSingleTap();
    }

    interface OnMultiTouchListener {
        void onEditTextClickListener(String str, int i);

        void onRemoveViewListener(View view);
    }

    private static float adjustAngle(float f) {
        return f > 180.0f ? f - 360.0f : f < -180.0f ? f + 360.0f : f;
    }

    MultiTouchListener(Context context, RelativeLayout relativeLayout, ImageView imageView, boolean z, OnPhotoEditorListener onPhotoEditorListener) {
        this.mContext = context;
        this.mIsTextPinchZoomable = z;
        this.mScaleGestureDetector = new ScaleGestureDetector(new ScaleGestureListener());
        this.mGestureListener = new GestureDetector(context, new GestureListener());
        this.parentView = relativeLayout;
        this.photoEditImageView = imageView;
        this.mOnPhotoEditorListener = onPhotoEditorListener;
        View view = this.deleteView;
        if (view != null) {
            this.outRect = new Rect(view.getLeft(), this.deleteView.getTop(), this.deleteView.getRight(), this.deleteView.getBottom());
        } else {
            this.outRect = new Rect(0, 0, 0, 0);
        }
    }


    public static void move(Context context, View view, TransformInfo transformInfo, StrokeTextView strokeTextView, int i) {
        computeRenderOffset(view, transformInfo.pivotX, transformInfo.pivotY);
        adjustTranslation(view, transformInfo.deltaX, transformInfo.deltaY);
        float max = Math.max(transformInfo.minimumScale, Math.min(transformInfo.maximumScale, view.getScaleX() * transformInfo.deltaScale));
        if (strokeTextView != null) {
            strokeTextView.setTextSize((float) ((int) ((((float) i) * max) / (((float) context.getResources().getDisplayMetrics().densityDpi) / 160.0f))));
        } else {
            view.setScaleX(max);
            view.setScaleY(max);
            View findViewById = view.findViewById(R.id.frmBorder);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) findViewById.getLayoutParams();
            int dimension = (int) (context.getResources().getDimension(R.dimen.frame_margin) / max);
            layoutParams.setMargins(dimension, dimension, dimension, dimension);
            findViewById.setLayoutParams(layoutParams);
            View findViewById2 = view.findViewById(R.id.imgPhotoEditorClose);
            findViewById2.setPivotX(0.0f);
            findViewById2.setPivotY(0.0f);
            float f = 1.0f / max;
            findViewById2.setScaleX(f);
            findViewById2.setScaleY(f);

            View findViewById4 = view.findViewById(R.id.imgPhotoEditorZoom);
            findViewById4.setPivotX((float) findViewById4.getWidth());
            findViewById4.setPivotY((float) findViewById4.getHeight());
            findViewById4.setScaleX(f);
            findViewById4.setScaleY(f);
        }
        view.setRotation(adjustAngle(view.getRotation() + transformInfo.deltaAngle));
    }

    private static void adjustTranslation(View view, float f, float f2) {
        float[] fArr = {f, f2};
        view.getMatrix().mapVectors(fArr);
        view.setTranslationX(view.getTranslationX() + fArr[0]);
        view.setTranslationY(view.getTranslationY() + fArr[1]);
    }

    private static void computeRenderOffset(View view, float f, float f2) {
        if (view.getPivotX() != f || view.getPivotY() != f2) {
            float[] fArr = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr);
            view.setPivotX(f);
            view.setPivotY(f2);
            float[] fArr2 = {0.0f, 0.0f};
            view.getMatrix().mapPoints(fArr2);
            float f3 = fArr2[0] - fArr[0];
            float f4 = fArr2[1] - fArr[1];
            view.setTranslationX(view.getTranslationX() - f3);
            view.setTranslationY(view.getTranslationY() - f4);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.mScaleGestureDetector.onTouchEvent(view, motionEvent);
        this.mGestureListener.onTouchEvent(motionEvent);
        if (!this.isTranslateEnabled) {
            return true;
        }
        int action = motionEvent.getAction();
        int rawX = (int) motionEvent.getRawX();
        int rawY = (int) motionEvent.getRawY();
        int actionMasked = motionEvent.getActionMasked() & action;
        int i = 0;
        if (actionMasked != 6) {
            switch (actionMasked) {
                case 0:
                    this.mPrevX = motionEvent.getX();
                    this.mPrevY = motionEvent.getY();
                    this.mPrevRawX = motionEvent.getRawX();
                    this.mPrevRawY = motionEvent.getRawY();
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    View view2 = this.deleteView;
                    if (view2 != null) {
                        view2.setVisibility(View.VISIBLE);
                    }
                    view.bringToFront();
                    firePhotoEditorSDKListener(view, true);
                    break;
                case 1:
                    this.mActivePointerId = -1;
                    View view3 = this.deleteView;
                    if (view3 != null && isViewInBounds(view3, rawX, rawY)) {
                        OnMultiTouchListener onMultiTouchListener2 = this.onMultiTouchListener;
                        if (onMultiTouchListener2 != null) {
                            onMultiTouchListener2.onRemoveViewListener(view);
                        }
                    } else if (!isViewInBounds(this.photoEditImageView, rawX, rawY)) {
                        view.animate().translationY(0.0f).translationY(0.0f);
                    }
                    firePhotoEditorSDKListener(view, false);
                    break;
                case 2:
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex != -1) {
                        float x = motionEvent.getX(findPointerIndex);
                        float y = motionEvent.getY(findPointerIndex);
                        if (!this.mScaleGestureDetector.isInProgress()) {
                            adjustTranslation(view, x - this.mPrevX, y - this.mPrevY);
                            break;
                        }
                    }
                    break;
                case 3:
                    this.mActivePointerId = -1;
                    break;
            }
        } else {
            int i2 = (65280 & action) >> 8;
            if (motionEvent.getPointerId(i2) == this.mActivePointerId) {
                if (i2 == 0) {
                    i = 1;
                }
                this.mPrevX = motionEvent.getX(i);
                this.mPrevY = motionEvent.getY(i);
                this.mActivePointerId = motionEvent.getPointerId(i);
            }
        }
        return true;
    }

    private void firePhotoEditorSDKListener(View view, boolean z) {
        Object tag = view.getTag();
        OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null && tag != null && (tag instanceof ViewType)) {
            if (z) {
                onPhotoEditorListener.onStartViewChangeListener((ViewType) view.getTag());
            } else {
                onPhotoEditorListener.onStopViewChangeListener((ViewType) view.getTag());
            }
        }
    }

    private boolean isViewInBounds(View view, int i, int i2) {
        view.getDrawingRect(this.outRect);
        view.getLocationOnScreen(this.location);
        Rect rect = this.outRect;
        int[] iArr = this.location;
        rect.offset(iArr[0], iArr[1]);
        return this.outRect.contains(i, i2);
    }


    public void setOnMultiTouchListener(OnMultiTouchListener onMultiTouchListener2) {
        this.onMultiTouchListener = onMultiTouchListener2;
    }

    private class ScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float mPivotX;
        private float mPivotY;
        private final Vector2D mPrevSpanVector;
        private StrokeTextView strokeTextView;
        private int textSize;

        private ScaleGestureListener() {
            this.mPrevSpanVector = new Vector2D();
            this.textSize = -1;
        }

        @Override
        public boolean onScaleBegin(View view, ScaleGestureDetector scaleGestureDetector) {
            View findViewById = view.findViewById(R.id.tvPhotoEditorText);
            if (findViewById != null && (findViewById instanceof StrokeTextView)) {
                this.strokeTextView = (StrokeTextView) findViewById;
                this.textSize = (int) this.strokeTextView.getTextSize();
            }
            this.mPivotX = scaleGestureDetector.getFocusX();
            this.mPivotY = scaleGestureDetector.getFocusY();
            this.mPrevSpanVector.set(scaleGestureDetector.getCurrentSpanVector());
            return MultiTouchListener.this.mIsTextPinchZoomable;
        }

        @Override
        public boolean onScale(View view, ScaleGestureDetector scaleGestureDetector) {
            TransformInfo transformInfo = new TransformInfo();
            transformInfo.deltaScale = MultiTouchListener.this.isScaleEnabled ? scaleGestureDetector.getScaleFactor() : 1.0f;
            float f = 0.0f;
            transformInfo.deltaAngle = MultiTouchListener.this.isRotateEnabled ? Vector2D.getAngle(this.mPrevSpanVector, scaleGestureDetector.getCurrentSpanVector()) : 0.0f;
            transformInfo.deltaX = MultiTouchListener.this.isTranslateEnabled ? scaleGestureDetector.getFocusX() - this.mPivotX : 0.0f;
            if (MultiTouchListener.this.isTranslateEnabled) {
                f = scaleGestureDetector.getFocusY() - this.mPivotY;
            }
            transformInfo.deltaY = f;
            transformInfo.pivotX = this.mPivotX;
            transformInfo.pivotY = this.mPivotY;
            transformInfo.minimumScale = MultiTouchListener.this.minimumScale;
            transformInfo.maximumScale = MultiTouchListener.this.maximumScale;
            MultiTouchListener.move(MultiTouchListener.this.mContext, view, transformInfo, this.strokeTextView, this.textSize);
            return !MultiTouchListener.this.mIsTextPinchZoomable;
        }

        @Override
        public void onScaleEnd(View view, ScaleGestureDetector scaleGestureDetector) {
            super.onScaleEnd(view, scaleGestureDetector);
            this.textSize = -1;
            this.strokeTextView = null;
        }
    }

    private class TransformInfo {

        float deltaX;

        float deltaY;

        float deltaScale;

        float deltaAngle;

        float pivotX;

        float pivotY;

        float minimumScale;

        float maximumScale;

        private TransformInfo() {
        }
    }


    public void setOnGestureControl(OnGestureControl onGestureControl) {
        this.mOnGestureControl = onGestureControl;
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private GestureListener() {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (MultiTouchListener.this.mOnGestureControl == null) {
                return true;
            }
            MultiTouchListener.this.mOnGestureControl.onClick();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
            if (MultiTouchListener.this.mOnGestureControl != null) {
                MultiTouchListener.this.mOnGestureControl.onLongClick();
            }
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (MultiTouchListener.this.mOnGestureControl == null) {
                return true;
            }
            MultiTouchListener.this.mOnGestureControl.onDoubleClick();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            if (MultiTouchListener.this.mOnGestureControl != null) {
                MultiTouchListener.this.mOnGestureControl.onSingleTap();
            }
            return super.onDown(motionEvent);
        }
    }
}
