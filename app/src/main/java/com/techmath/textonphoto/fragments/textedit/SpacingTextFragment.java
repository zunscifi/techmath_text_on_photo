package com.techmath.textonphoto.fragments.textedit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.interfaces.SpacingFragmentListener;

public class SpacingTextFragment extends Fragment {

    SeekBar sbLineHeight;
    SeekBar sbLetterSpacing;
    SpacingFragmentListener spacingFragmentListener;

    public float getConvertedValue(int i) {
        return (((float) i) * 0.5f) / 10.0f;
    }

    public void setListener(SpacingFragmentListener spacingFragmentListener) {
        this.spacingFragmentListener = spacingFragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_text_spacing, viewGroup, false);
        this.sbLetterSpacing =  inflate.findViewById(R.id.sbLetterSpacing);
        this.sbLineHeight =  inflate.findViewById(R.id.sbLineHeight);
        this.sbLetterSpacing.setMax(90);
        this.sbLetterSpacing.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (SpacingTextFragment.this.spacingFragmentListener != null) {
                    SpacingTextFragment.this.spacingFragmentListener.onSpacingLetter(SpacingTextFragment.this.getConvertedValue(i));
                }
            }
        });
        this.sbLineHeight.setMax(ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        this.sbLineHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (SpacingTextFragment.this.spacingFragmentListener != null) {
                    SpacingTextFragment.this.spacingFragmentListener.onLineHeight(i);
                }
            }
        });
        return inflate;
    }
}
