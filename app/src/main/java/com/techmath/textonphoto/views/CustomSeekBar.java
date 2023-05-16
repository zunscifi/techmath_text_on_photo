package com.techmath.textonphoto.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatSeekBar;

import com.techmath.textonphoto.R;

public class CustomSeekBar extends AppCompatSeekBar {
    private Paint paint;
    private Rect rect;
    private int seekbarHeight;

    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.rect = new Rect();
        this.paint = new Paint();
        this.seekbarHeight = 6;
    }

    public CustomSeekBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override
    public synchronized void onDraw(Canvas canvas) {
        this.rect.set(getThumbOffset(), (getHeight() / 2) - (this.seekbarHeight / 2), getWidth() - getThumbOffset(), (getHeight() / 2) + (this.seekbarHeight / 2));
        this.paint.setColor(this.getResources().getColor(R.color.unselected_color));
        canvas.drawRect(this.rect, this.paint);
        if (getProgress() > 50) {
            this.rect.set(getWidth() / 2, (getHeight() / 2) - (this.seekbarHeight / 2), (getWidth() / 2) + ((getWidth() / 100) * (getProgress() - 50)), (getHeight() / 2) + (this.seekbarHeight / 2));
            this.paint.setColor(this.getResources().getColor(R.color.button_color));
            canvas.drawRect(this.rect, this.paint);
        }
        if (getProgress() < 50) {
            this.rect.set((getWidth() / 2) - ((getWidth() / 100) * (50 - getProgress())), (getHeight() / 2) - (this.seekbarHeight / 2), getWidth() / 2, (getHeight() / 2) + (this.seekbarHeight / 2));
            this.paint.setColor(this.getResources().getColor(R.color.button_color));
            canvas.drawRect(this.rect, this.paint);
        }
        super.onDraw(canvas);
    }
}
