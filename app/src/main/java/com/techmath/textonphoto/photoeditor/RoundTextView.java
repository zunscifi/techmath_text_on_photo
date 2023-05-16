package com.techmath.textonphoto.photoeditor;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class RoundTextView extends AppCompatTextView {
    private final RoundViewDelegate delegate;

    public RoundTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public RoundTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.delegate = new RoundViewDelegate(this, context, attributeSet);
    }

    public RoundViewDelegate getDelegate() {
        return this.delegate;
    }


    @Override
    public void onMeasure(int i, int i2) {
        if (!this.delegate.isWidthHeightEqual() || getWidth() <= 0 || getHeight() <= 0) {
            super.onMeasure(i, i2);
            return;
        }
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(Math.max(getWidth(), getHeight()), MeasureSpec.EXACTLY);
        super.onMeasure(makeMeasureSpec, makeMeasureSpec);
    }

    @Override
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.delegate.isRadiusHalfHeight()) {
            this.delegate.setCornerRadius(getHeight() / 2);
        } else {
            this.delegate.setBgSelector();
        }
    }
}
