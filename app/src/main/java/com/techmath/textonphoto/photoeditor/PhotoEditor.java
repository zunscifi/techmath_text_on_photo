package com.techmath.textonphoto.photoeditor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.annotation.UiThread;


import com.techmath.textonphoto.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PhotoEditor implements BrushViewChangeListener {
    private static final String TAG = "PhotoEditor";


    Handler handler;
    private final List<View> addedViews;
    private final View alignView;
    private final BrushDrawingView brushDrawingView;
    private final Context context;
    private final View deleteView;
    private RoundFrameLayout frBorder;
    private final ImageView imageView;
    private final boolean isTextPinchZoomable;
    private final Typeface mDefaultEmojiTypeface;
    private final Typeface mDefaultTextTypeface;
    private final LayoutInflater mLayoutInflater;

    public OnPhotoEditorListener mOnPhotoEditorListener;

    public PointF midPoint;

    public PhotoEditorView parentView;
    private final List<View> redoViews;
    private final PointF startPoint;
    private final View zoomView;

    public interface OnSaveListener {
        void onFailure(@NonNull Exception exc);

        void onSuccess(@NonNull String str);
    }

    private PhotoEditor(Builder builder) {
        this.handler = new Handler();
        this.midPoint = new PointF();
        this.startPoint = new PointF();
        this.context = builder.context;
        this.parentView = builder.parentView;
        this.imageView = builder.imageView;
        this.deleteView = builder.deleteView;
        this.alignView = builder.alignView;
        this.zoomView = builder.zoomView;
        this.brushDrawingView = builder.brushDrawingView;
        this.isTextPinchZoomable = builder.isTextPinchZoomable;
        this.mDefaultTextTypeface = builder.textTypeface;
        this.mDefaultEmojiTypeface = builder.emojiTypeface;
        this.mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.brushDrawingView.setBrushViewChangeListener((BrushViewChangeListener) this);
        this.addedViews = new ArrayList<>();
        this.redoViews = new ArrayList<>();
    }

    public void addImage(Bitmap bitmap) {
        final View layout = getLayout(ViewType.IMAGE);
        final ImageView imgEditorImage = (ImageView) layout.findViewById(R.id.imgPhotoEditorImage);
        final FrameLayout frmBorder = (FrameLayout) layout.findViewById(R.id.frmBorder);
        final View imgClose = layout.findViewById(R.id.imgPhotoEditorClose);

        final View imgZoom = layout.findViewById(R.id.imgPhotoEditorZoom);
        imgEditorImage.setImageBitmap(bitmap);


        final Runnable runnable = new Runnable() {
            public void run() {
                imgClose.setVisibility(View.GONE);

                imgZoom.setVisibility(View.GONE);
                frmBorder.setBackgroundResource(0);
            }
        };
        imgClose.setVisibility(View.VISIBLE);

        imgZoom.setVisibility(View.VISIBLE);
        frmBorder.setBackgroundResource(R.drawable.rounded_border_tv);
        this.handler.postDelayed(runnable, 2500);
        MultiTouchListener multiTouchListener = getMultiTouchListener();

        multiTouchListener.setOnGestureControl((MultiTouchListener.OnGestureControl) new MultiTouchListener.OnGestureControl() {
            public void onDoubleClick() {
            }

            public void onLongClick() {
            }

            public void onClick() {
                imgClose.setVisibility(View.VISIBLE);

                imgZoom.setVisibility(View.VISIBLE);
                frmBorder.setBackgroundResource(R.drawable.rounded_border_tv);
                PhotoEditor.this.handler.removeCallbacks(runnable);
                PhotoEditor.this.handler.postDelayed(runnable, 2500);
            }

            public void onSingleTap() {
                imgClose.setVisibility(View.VISIBLE);

                imgZoom.setVisibility(View.VISIBLE);
                frmBorder.setBackgroundResource(R.drawable.rounded_border_tv);
                PhotoEditor.this.handler.removeCallbacks(runnable);
                PhotoEditor.this.handler.postDelayed(runnable, 2500);
                if (PhotoEditor.this.mOnPhotoEditorListener != null) {
                    PhotoEditor.this.mOnPhotoEditorListener.onClickGetImageViewListener(imgEditorImage, layout);
                }
            }
        });
        layout.setOnTouchListener(multiTouchListener);
        addViewToParent(layout, ViewType.IMAGE);
    }


    @SuppressLint({"ClickableViewAccessibility"})
    public void addText(String str, int i) {
        addText((Typeface) null, str, i);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void addText(@Nullable Typeface typeface, String str, int i) {
        Typeface typeface2 = typeface;
        this.brushDrawingView.setBrushDrawingMode(false);
        View textRootView = getLayout(ViewType.TEXT);
        StrokeTextView strokeTextView = (StrokeTextView) textRootView.findViewById(R.id.tvPhotoEditorText);
        View findViewById = textRootView.findViewById(R.id.imgPhotoEditorClose);

        View findViewById3 = textRootView.findViewById(R.id.imgPhotoEditorZoom);
        RoundFrameLayout roundFrameLayout = (RoundFrameLayout) textRootView.findViewById(R.id.frmBorder_highlight);
        FrameLayout frameLayout = (FrameLayout) textRootView.findViewById(R.id.frmBorder);
        strokeTextView.setText(str);
        strokeTextView.setTextColor(i);
        final View view = findViewById;

        final View view3 = findViewById3;

        final FrameLayout frameLayout2 = frameLayout;
        final Runnable runnable = new Runnable() {
            public void run() {
                view.setVisibility(View.GONE);

                view3.setVisibility(View.GONE);
                frameLayout2.setBackgroundResource(0);
            }
        };
        findViewById.setVisibility(View.VISIBLE);

        findViewById3.setVisibility(View.VISIBLE);
        frameLayout.setBackgroundResource(R.drawable.rounded_border_tv);
        this.handler.removeCallbacks(runnable);
        this.handler.postDelayed(runnable, 2500);
        if (typeface2 != null) {
            strokeTextView.setTypeface(typeface2);
        }
        final View view4 = findViewById;

        final View view6 = findViewById3;
        final FrameLayout frameLayout3 = frameLayout;
        MultiTouchListener multiTouchListener = getMultiTouchListener();
        final StrokeTextView strokeTextView2 = strokeTextView;
        final View view7 = textRootView;
        final RoundFrameLayout roundFrameLayout2 = roundFrameLayout;
        MultiTouchListener.OnGestureControl onGestureControl = new MultiTouchListener.OnGestureControl() {
            public void onLongClick() {
            }

            public void onClick() {
                view4.setVisibility(View.VISIBLE);

                view6.setVisibility(View.VISIBLE);
                frameLayout3.setBackgroundResource(R.drawable.rounded_border_tv);
                PhotoEditor.this.handler.removeCallbacks(runnable);
                PhotoEditor.this.handler.postDelayed(runnable, 2500);
            }

            public void onDoubleClick() {
                String charSequence = strokeTextView2.getText().toString();
                int currentTextColor = strokeTextView2.getCurrentTextColor();
                if (PhotoEditor.this.mOnPhotoEditorListener != null) {
                    PhotoEditor.this.mOnPhotoEditorListener.onEditTextChangeListener(view7, charSequence, currentTextColor);
                }
            }

            public void onSingleTap() {
                view4.setVisibility(View.VISIBLE);

                view6.setVisibility(View.VISIBLE);
                frameLayout3.setBackgroundResource(R.drawable.rounded_border_tv);
                PhotoEditor.this.handler.removeCallbacks(runnable);
                PhotoEditor.this.handler.postDelayed(runnable, 2500);
                if (PhotoEditor.this.mOnPhotoEditorListener != null) {
                    PhotoEditor.this.mOnPhotoEditorListener.onClickGetEditTextChangeListener(strokeTextView2, roundFrameLayout2);
                }
            }
        };
        multiTouchListener.setOnGestureControl(onGestureControl);
        textRootView.setOnTouchListener(multiTouchListener);
        addViewToParent(textRootView, ViewType.TEXT);
        this.mOnPhotoEditorListener.onAdded(strokeTextView, roundFrameLayout);
        String charSequence = strokeTextView.getText().toString();
        int currentTextColor = strokeTextView.getCurrentTextColor();
        OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onEditTextChangeListener(textRootView, charSequence, currentTextColor);
        }
    }

    public void editText(View view, String str, int i) {
        editText(view, (Typeface) null, str, i);
    }

    public void editText(View view, Typeface typeface, String str, int i) {
        TextView textView = (TextView) view.findViewById(R.id.tvPhotoEditorText);
        if (textView != null && this.addedViews.contains(view) && !TextUtils.isEmpty(str)) {
            textView.setText(str);
            if (typeface != null) {
                textView.setTypeface(typeface);
            }
            textView.setTextColor(i);
            this.parentView.updateViewLayout(view, view.getLayoutParams());
            int indexOf = this.addedViews.indexOf(view);
            if (indexOf > -1) {
                this.addedViews.set(indexOf, view);
            }
        }
    }

    public void addEmoji(String str) {
        addEmoji((Typeface) null, str);
    }

    public void addEmoji(Typeface typeface, String str) {
        this.brushDrawingView.setBrushDrawingMode(false);
        View layout = getLayout(ViewType.EMOJI);
        TextView textView = (TextView) layout.findViewById(R.id.tvPhotoEditorText);
        FrameLayout frameLayout = (FrameLayout) layout.findViewById(R.id.frmBorder);
        View findViewById = layout.findViewById(R.id.imgPhotoEditorClose);

        View findViewById3 = layout.findViewById(R.id.imgPhotoEditorZoom);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
        final View view = findViewById;

        final View view3 = findViewById3;
        final FrameLayout frameLayout2 = frameLayout;
        final Runnable runnable = new Runnable() {
            public void run() {
                view.setVisibility(View.GONE);

                view3.setVisibility(View.GONE);
                frameLayout2.setBackgroundResource(0);
            }
        };
        findViewById.setVisibility(View.VISIBLE);

        findViewById3.setVisibility(View.VISIBLE);
        frameLayout.setBackgroundResource(R.drawable.rounded_border_tv);
        this.handler.postDelayed(runnable, 2500);
        textView.setTextSize(56.0f);
        textView.setText(str);
        MultiTouchListener multiTouchListener = getMultiTouchListener();

        multiTouchListener.setOnGestureControl((MultiTouchListener.OnGestureControl) new MultiTouchListener.OnGestureControl() {
            public void onDoubleClick() {
            }

            public void onLongClick() {
            }

            public void onClick() {
                view.setVisibility(View.VISIBLE);

                view3.setVisibility(View.VISIBLE);
                frameLayout2.setBackgroundResource(R.drawable.rounded_border_tv);
                PhotoEditor.this.handler.postDelayed(runnable, 2500);
            }

            public void onSingleTap() {
                view.setVisibility(View.VISIBLE);

                view3.setVisibility(View.VISIBLE);
                frameLayout2.setBackgroundResource(R.drawable.rounded_border_tv);
                PhotoEditor.this.handler.postDelayed(runnable, 2500);
            }
        });
        layout.setOnTouchListener(multiTouchListener);
        addViewToParent(layout, ViewType.EMOJI);
    }

    private void addViewToParent(View view, ViewType viewType) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(13, -1);
        this.parentView.addView(view, layoutParams);
        this.addedViews.add(view);
        OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onAddViewListener(viewType, this.addedViews.size());
        }
    }

    @NonNull
    private MultiTouchListener getMultiTouchListener() {
        return new MultiTouchListener(this.context, this.parentView, this.imageView, this.isTextPinchZoomable, this.mOnPhotoEditorListener);
    }


    @SuppressLint("WrongConstant")
    private View getLayout(final ViewType viewType) {
        View view = null;
        switch (viewType) {
            case TEXT:
                view = this.mLayoutInflater.inflate(R.layout.view_photo_editor_text, (ViewGroup) null);
                TextView textView = (TextView) view.findViewById(R.id.tvPhotoEditorText);
                if (!(textView == null || this.mDefaultTextTypeface == null)) {
                    textView.setGravity(17);
                    if (this.mDefaultEmojiTypeface != null) {
                        textView.setTypeface(this.mDefaultTextTypeface);
                        break;
                    }
                }
                break;
            case IMAGE:
                view = this.mLayoutInflater.inflate(R.layout.view_photo_editor_image, (ViewGroup) null);
                break;
            case EMOJI:
                View inflate = this.mLayoutInflater.inflate(R.layout.view_photo_editor_emoji, (ViewGroup) null);
                TextView textView2 = (TextView) inflate.findViewById(R.id.tvPhotoEditorText);
                if (textView2 != null) {
                    Typeface typeface = this.mDefaultEmojiTypeface;
                    if (typeface != null) {
                        textView2.setTypeface(typeface);
                    }
                    textView2.setGravity(17);
                    textView2.setLayerType(1, (Paint) null);
                }
                view = inflate;
                break;
        }
        if (view != null) {
            view.setTag(viewType);
            final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.frmBorder);
            final View findViewById = view.findViewById(R.id.imgPhotoEditorClose);
            if (findViewById != null) {
                final View finalView = view;
                findViewById.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        PhotoEditor.this.viewUndo(finalView, viewType);
                    }
                });
            }


            View findViewById3 = view.findViewById(R.id.imgPhotoEditorZoom);
            if (findViewById3 != null) {
                final View view2 = findViewById3;
                findViewById3.setOnTouchListener(new View.OnTouchListener() {


                    float scaleX = 1.0f;


                    float rotation = 0.0f;

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        Log.d("XXXXXXXX", "setOnTouchListener " + motionEvent.getRawX() + " " + motionEvent.getRawY());
                        switch (motionEvent.getAction()) {
                            case 0:
                                this.scaleX = ((View) view.getParent()).getScaleX();
                                this.rotation = ((View) view.getParent()).getRotation();
                                PhotoEditor.this.getPointF(view, motionEvent);
                                Log.d("XXXXXXXX", "ACTION_DOWN " + this.scaleX + " " + this.rotation + " mid " + PhotoEditor.this.midPoint.x + " " + PhotoEditor.this.midPoint.y);
                                return true;
                            case 1:
                            case 2:
                                PhotoEditor.this.zoomAndRotateSticker((View) view.getParent(), motionEvent, frameLayout, findViewById, view2, this.scaleX, this.rotation);
                                return false;
                            default:

                                return false;
                        }
                    }
                });
            }
        }
        return view;
    }

    @NonNull

    public PointF getPointF(@Nullable View view, MotionEvent motionEvent) {
        View view2 = (View) view.getParent();
        this.startPoint.set(motionEvent.getRawX(), motionEvent.getRawY());
        this.midPoint.set(view2.getX() + ((float) (view2.getWidth() / 2)), view2.getY() + ((float) (view2.getHeight() / 2)));
        return this.midPoint;
    }

    public void zoomAndRotateSticker(@Nullable View view, @NonNull MotionEvent motionEvent, FrameLayout frameLayout, View view2, View view4, float f, float f2) {
        if (view != null) {
            float a = (getFloatA(this.midPoint.x, this.midPoint.y, motionEvent.getRawX(), motionEvent.getRawY()) / getFloatA(this.startPoint.x, this.startPoint.y, this.midPoint.x, this.midPoint.y)) * f;
            view.setPivotX((float) (view.getWidth() / 2));
            view.setPivotY((float) (view.getHeight() / 2));
            view.setScaleX(a);
            view.setScaleY(a);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) frameLayout.getLayoutParams();
            int dimension = (int) (this.context.getResources().getDimension(R.dimen.frame_margin) / a);
            layoutParams.setMargins(dimension, dimension, dimension, dimension);
            frameLayout.setLayoutParams(layoutParams);
            view2.setPivotX(0.0f);
            view2.setPivotY(0.0f);

            view4.setPivotX((float) view4.getWidth());
            view4.setPivotY((float) view4.getHeight());
            float f3 = 1.0f / a;
            view2.setScaleX(f3);
            view2.setScaleY(f3);

            view4.setScaleX(f3);
            view4.setScaleY(f3);

            float degrees = f2 + ((float) Math.toDegrees(Math.atan2((double) (motionEvent.getRawY() - this.midPoint.y), (double) (motionEvent.getRawX() - this.midPoint.x)) - Math.atan2((double) (this.startPoint.y - this.midPoint.y), (double) (this.startPoint.x - this.midPoint.x))));
            view.setRotation(degrees);
            view.requestLayout();

            Log.d("XXXXXXXX", "ACTION_MOVE  " + a + " " + degrees + " " + motionEvent.getRawX() + " " + motionEvent.getRawY());
        }
    }


    public float getFloatA(float f, float f2, float f3, float f4) {
        double d = (double) (f - f3);
        double d2 = (double) (f2 - f4);
        return (float) Math.sqrt((d * d) + (d2 * d2));
    }

    public void setBrushDrawingMode(boolean z) {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        if (brushDrawingView2 != null) {
            brushDrawingView2.setBrushDrawingMode(z);
        }
    }

    public Boolean getBrushDrawableMode() {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        return Boolean.valueOf(brushDrawingView2 != null && brushDrawingView2.getBrushDrawingMode());
    }

    public void setBrushSize(float f) {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        if (brushDrawingView2 != null) {
            brushDrawingView2.setBrushSize(f);
        }
    }

    public void setOpacity(@IntRange(from = 0, to = 100) int i) {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        if (brushDrawingView2 != null) {
            brushDrawingView2.setOpacity((int) ((((double) i) / 100.0d) * 255.0d));
        }
    }

    public void setBrushColor(@ColorInt int i) {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        if (brushDrawingView2 != null) {
            brushDrawingView2.setBrushColor(i);
        }
    }

    public void setBrushEraserSize(float f) {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        if (brushDrawingView2 != null) {
            brushDrawingView2.setBrushEraserSize(f);
        }
    }


    public float getEraserSize() {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        if (brushDrawingView2 != null) {
            return brushDrawingView2.getEraserSize();
        }
        return 0.0f;
    }

    public float getBrushSize() {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        if (brushDrawingView2 != null) {
            return brushDrawingView2.getBrushSize();
        }
        return 0.0f;
    }

    public int getBrushColor() {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        if (brushDrawingView2 != null) {
            return brushDrawingView2.getBrushColor();
        }
        return 0;
    }

    public void brushEraser() {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        if (brushDrawingView2 != null) {
            brushDrawingView2.brushEraser();
        }
    }


    public void viewUndo(View view, ViewType viewType) {
        if (this.addedViews.size() > 0 && this.addedViews.contains(view)) {
            this.parentView.removeView(view);
            this.addedViews.remove(view);
            this.redoViews.add(view);
            OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
            if (onPhotoEditorListener != null) {
                onPhotoEditorListener.onRemoveViewListener(this.addedViews.size());
                this.mOnPhotoEditorListener.onRemoveViewListener(viewType, this.addedViews.size());
            }
        }
    }

    public boolean undo() {
        if (this.addedViews.size() > 0) {
            List<View> list = this.addedViews;
            View view = list.get(list.size() - 1);
            if (view instanceof BrushDrawingView) {
                BrushDrawingView brushDrawingView2 = this.brushDrawingView;
                return brushDrawingView2 != null && brushDrawingView2.undo();
            }
            List<View> list2 = this.addedViews;
            list2.remove(list2.size() - 1);
            this.parentView.removeView(view);
            this.redoViews.add(view);
            OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
            if (onPhotoEditorListener != null) {
                onPhotoEditorListener.onRemoveViewListener(this.addedViews.size());
                Object tag = view.getTag();
                if (tag != null && (tag instanceof ViewType)) {
                    this.mOnPhotoEditorListener.onRemoveViewListener((ViewType) tag, this.addedViews.size());
                }
            }
        }
        return this.addedViews.size() != 0;
    }

    public boolean redo() {
        if (this.redoViews.size() > 0) {
            List<View> list = this.redoViews;
            View view = list.get(list.size() - 1);
            if (view instanceof BrushDrawingView) {
                BrushDrawingView brushDrawingView2 = this.brushDrawingView;
                return brushDrawingView2 != null && brushDrawingView2.redo();
            }
            List<View> list2 = this.redoViews;
            list2.remove(list2.size() - 1);
            this.parentView.addView(view);
            this.addedViews.add(view);
            Object tag = view.getTag();
            OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
            if (!(onPhotoEditorListener == null || tag == null || !(tag instanceof ViewType))) {
                onPhotoEditorListener.onAddViewListener((ViewType) tag, this.addedViews.size());
            }
        }
        return this.redoViews.size() != 0;
    }

    private void clearBrushAllViews() {
        BrushDrawingView brushDrawingView2 = this.brushDrawingView;
        if (brushDrawingView2 != null) {
            brushDrawingView2.clearAll();
        }
    }

    public void clearAllViews() {
        for (int i = 0; i < this.addedViews.size(); i++) {
            this.parentView.removeView(this.addedViews.get(i));
        }
        if (this.addedViews.contains(this.brushDrawingView)) {
            this.parentView.addView(this.brushDrawingView);
        }
        this.addedViews.clear();
        this.redoViews.clear();
        clearBrushAllViews();
    }

    @UiThread
    public void clearHelperBox() {
        for (int i = 0; i < this.parentView.getChildCount(); i++) {
            View childAt = this.parentView.getChildAt(i);
            FrameLayout frameLayout = (FrameLayout) childAt.findViewById(R.id.frmBorder);
            if (frameLayout != null) {
                frameLayout.setBackgroundResource(0);
                View findViewById = childAt.findViewById(R.id.imgPhotoEditorClose);
                if (findViewById != null) {
                    findViewById.setVisibility(View.GONE);
                }


                View findViewById3 = childAt.findViewById(R.id.imgPhotoEditorZoom);
                if (findViewById3 != null) {
                    findViewById3.setVisibility(View.GONE);
                }
            }
        }
    }

    public void setFilterEffect(CustomEffect customEffect) {
        this.parentView.setFilterEffect(customEffect);
    }

    public void setFilterEffect(PhotoFilter photoFilter) {
        this.parentView.setFilterEffect(photoFilter);
    }

    @RequiresPermission(allOf = {"android.permission.WRITE_EXTERNAL_STORAGE"})
    @SuppressLint({"StaticFieldLeak"})

    public void saveImage(@NonNull String str, @NonNull OnSaveListener onSaveListener) {
        saveAsFile(str, onSaveListener);
    }

    @RequiresPermission(allOf = {"android.permission.WRITE_EXTERNAL_STORAGE"})
    public void saveAsFile(@NonNull String str, @NonNull OnSaveListener onSaveListener) {
        saveAsFile(str, new SaveSettings.Builder().build(), onSaveListener);
    }

    @RequiresPermission(allOf = {"android.permission.WRITE_EXTERNAL_STORAGE"})
    @SuppressLint({"StaticFieldLeak"})
    public void saveAsFile(@NonNull final String str, @NonNull final SaveSettings saveSettings, @NonNull final OnSaveListener onSaveListener) {
        Log.d(TAG, "Image Path: " + str);
        this.parentView.saveFilter((OnSaveBitmap) new OnSaveBitmap() {
            @Override
            public void onBitmapReady(Bitmap bitmap) {
                new AsyncTask<String, String, Exception>() {
                    @Override
                    public void onPreExecute() {
                        super.onPreExecute();
                        PhotoEditor.this.clearHelperBox();
                        PhotoEditor.this.parentView.setDrawingCacheEnabled(false);
                    }


                    @SuppressLint({"MissingPermission"})

                    public Exception doInBackground(String... strArr) {
                        Bitmap bitmap;
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(new File(str), false);
                            if (PhotoEditor.this.parentView != null) {
                                PhotoEditor.this.parentView.setDrawingCacheEnabled(true);
                                if (saveSettings.isTransparencyEnabled()) {
                                    bitmap = BitmapUtil.removeTransparency(PhotoEditor.this.parentView.getDrawingCache());
                                } else {
                                    bitmap = PhotoEditor.this.parentView.getDrawingCache();
                                }
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                            }
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            Log.d(PhotoEditor.TAG, "Filed Saved Successfully");
                            return null;
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(PhotoEditor.TAG, "Failed to save File");
                            return e;
                        }
                    }

                    @Override
                    public void onPostExecute(Exception exc) {
                        super.onPostExecute(exc);
                        if (exc == null) {
                            if (saveSettings.isClearViewsEnabled()) {
                                PhotoEditor.this.clearAllViews();
                            }
                            onSaveListener.onSuccess(str);
                            return;
                        }
                        onSaveListener.onFailure(exc);
                    }
                }.execute();
            }

            public void onFailure(Exception exc) {
                onSaveListener.onFailure(exc);
            }
        });
    }

    @SuppressLint({"StaticFieldLeak"})
    public void saveAsBitmap(@NonNull OnSaveBitmap onSaveBitmap) {
        saveAsBitmap(new SaveSettings.Builder().build(), onSaveBitmap);
    }

    @SuppressLint({"StaticFieldLeak"})
    public void saveAsBitmap(@NonNull final SaveSettings saveSettings, @NonNull final OnSaveBitmap onSaveBitmap) {
        this.parentView.saveFilter((OnSaveBitmap) new OnSaveBitmap() {
            public void onBitmapReady(Bitmap bitmap) {
                new AsyncTask<String, String, Bitmap>() {
                    @Override
                    public void onPreExecute() {
                        super.onPreExecute();
                        PhotoEditor.this.clearHelperBox();
                        PhotoEditor.this.parentView.setDrawingCacheEnabled(false);
                    }


                    public Bitmap doInBackground(String... strArr) {
                        if (PhotoEditor.this.parentView == null) {
                            return null;
                        }
                        PhotoEditor.this.parentView.setDrawingCacheEnabled(true);
                        if (saveSettings.isTransparencyEnabled()) {
                            return BitmapUtil.removeTransparency(PhotoEditor.this.parentView.getDrawingCache());
                        }
                        return PhotoEditor.this.parentView.getDrawingCache();
                    }

                    @Override
                    public void onPostExecute(Bitmap bitmap) {
                        super.onPostExecute(bitmap);
                        if (bitmap != null) {
                            if (saveSettings.isClearViewsEnabled()) {
                                PhotoEditor.this.clearAllViews();
                            }
                            onSaveBitmap.onBitmapReady(bitmap);
                            return;
                        }
                        onSaveBitmap.onFailure(new Exception("Failed to load the bitmap"));
                    }
                }.execute();
            }

            public void onFailure(Exception exc) {
                onSaveBitmap.onFailure(exc);
            }
        });
    }

    private static String convertEmoji(String str) {
        try {
            return new String(Character.toChars(Integer.parseInt(str.substring(2), 16)));
        } catch (NumberFormatException unused) {
            return "";
        }
    }

    public void setOnPhotoEditorListener(@NonNull OnPhotoEditorListener onPhotoEditorListener) {
        this.mOnPhotoEditorListener = onPhotoEditorListener;
    }

    public boolean isCacheEmpty() {
        return this.addedViews.size() == 0 && this.redoViews.size() == 0;
    }

    public void onViewAdd(BrushDrawingView brushDrawingView2) {
        if (this.redoViews.size() > 0) {
            List<View> list = this.redoViews;
            list.remove(list.size() - 1);
        }
        this.addedViews.add(brushDrawingView2);
        OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onAddViewListener(ViewType.BRUSH_DRAWING, this.addedViews.size());
        }
    }

    public void onViewRemoved(BrushDrawingView brushDrawingView2) {
        if (this.addedViews.size() > 0) {
            List<View> list = this.addedViews;
            View remove = list.remove(list.size() - 1);
            if (!(remove instanceof BrushDrawingView)) {
                this.parentView.removeView(remove);
            }
            this.redoViews.add(remove);
        }
        OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onRemoveViewListener(this.addedViews.size());
            this.mOnPhotoEditorListener.onRemoveViewListener(ViewType.BRUSH_DRAWING, this.addedViews.size());
        }
    }

    public void onStartDrawing() {
        OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onStartViewChangeListener(ViewType.BRUSH_DRAWING);
        }
    }

    public void onStopDrawing() {
        OnPhotoEditorListener onPhotoEditorListener = this.mOnPhotoEditorListener;
        if (onPhotoEditorListener != null) {
            onPhotoEditorListener.onStopViewChangeListener(ViewType.BRUSH_DRAWING);
        }
    }

    public static class Builder {

        public View alignView;

        public BrushDrawingView brushDrawingView;

        public Context context;

        public View deleteView;

        public Typeface emojiTypeface;

        public ImageView imageView;

        public boolean isTextPinchZoomable = true;

        public PhotoEditorView parentView;

        public Typeface textTypeface;

        public View zoomView;

        public Builder(Context context2, PhotoEditorView photoEditorView) {
            this.context = context2;
            this.parentView = photoEditorView;
            this.imageView = photoEditorView.getSource();
            this.brushDrawingView = photoEditorView.getBrushDrawingView();
        }


        public Builder setDefaultTextTypeface(Typeface typeface) {
            this.textTypeface = typeface;
            return this;
        }

        public Builder setDefaultEmojiTypeface(Typeface typeface) {
            this.emojiTypeface = typeface;
            return this;
        }

        public Builder setPinchTextScalable(boolean z) {
            this.isTextPinchZoomable = z;
            return this;
        }

        public PhotoEditor build() {
            return new PhotoEditor(this);
        }
    }

    public static List<String> getEmojis(Context context2) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String convertEmoji : context2.getResources().getStringArray(R.array.photo_editor_emoji)) {
            arrayList.add(convertEmoji(convertEmoji));
        }
        return arrayList;
    }
}
