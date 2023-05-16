package com.techmath.textonphoto.photoeditor;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

class FilterImageView extends AppCompatImageView {
    private OnImageChangedListener mOnImageChangedListener;

    interface OnImageChangedListener {
        void onBitmapLoaded(@Nullable Bitmap bitmap);
    }

    public FilterImageView(Context context) {
        super(context);
    }

    public FilterImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public FilterImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setOnImageChangedListener(OnImageChangedListener onImageChangedListener) {
        this.mOnImageChangedListener = onImageChangedListener;
    }
    @Override
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        OnImageChangedListener onImageChangedListener = this.mOnImageChangedListener;
        if (onImageChangedListener != null) {
            onImageChangedListener.onBitmapLoaded(getBitmap());
        }
    }
    @Override
    public void setImageIcon(@Nullable Icon icon) {
        super.setImageIcon(icon);
        OnImageChangedListener onImageChangedListener = this.mOnImageChangedListener;
        if (onImageChangedListener != null) {
            onImageChangedListener.onBitmapLoaded(getBitmap());
        }
    }
    @Override
    public void setImageMatrix(Matrix matrix) {
        super.setImageMatrix(matrix);
        OnImageChangedListener onImageChangedListener = this.mOnImageChangedListener;
        if (onImageChangedListener != null) {
            onImageChangedListener.onBitmapLoaded(getBitmap());
        }
    }
    @Override
    public void setImageState(int[] iArr, boolean z) {
        super.setImageState(iArr, z);
        OnImageChangedListener onImageChangedListener = this.mOnImageChangedListener;
        if (onImageChangedListener != null) {
            onImageChangedListener.onBitmapLoaded(getBitmap());
        }
    }
    @Override
    public void setImageTintList(@Nullable ColorStateList colorStateList) {
        super.setImageTintList(colorStateList);
        OnImageChangedListener onImageChangedListener = this.mOnImageChangedListener;
        if (onImageChangedListener != null) {
            onImageChangedListener.onBitmapLoaded(getBitmap());
        }
    }
    @Override
    public void setImageTintMode(@Nullable PorterDuff.Mode mode) {
        super.setImageTintMode(mode);
        OnImageChangedListener onImageChangedListener = this.mOnImageChangedListener;
        if (onImageChangedListener != null) {
            onImageChangedListener.onBitmapLoaded(getBitmap());
        }
    }
    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        OnImageChangedListener onImageChangedListener = this.mOnImageChangedListener;
        if (onImageChangedListener != null) {
            onImageChangedListener.onBitmapLoaded(getBitmap());
        }
    }
    @Override
    public void setImageResource(int i) {
        super.setImageResource(i);
        OnImageChangedListener onImageChangedListener = this.mOnImageChangedListener;
        if (onImageChangedListener != null) {
            onImageChangedListener.onBitmapLoaded(getBitmap());
        }
    }
    @Override
    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        OnImageChangedListener onImageChangedListener = this.mOnImageChangedListener;
        if (onImageChangedListener != null) {
            onImageChangedListener.onBitmapLoaded(getBitmap());
        }
    }
    @Override
    public void setImageLevel(int i) {
        super.setImageLevel(i);
        OnImageChangedListener onImageChangedListener = this.mOnImageChangedListener;
        if (onImageChangedListener != null) {
            onImageChangedListener.onBitmapLoaded(getBitmap());
        }
    }


    @Nullable

    public Bitmap getBitmap() {
        if (getDrawable() != null) {
            return ((BitmapDrawable) getDrawable()).getBitmap();
        }
        return null;
    }
}
