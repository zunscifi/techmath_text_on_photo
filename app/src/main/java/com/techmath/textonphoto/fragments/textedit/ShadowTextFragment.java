package com.techmath.textonphoto.fragments.textedit;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.adapter.ColorAdapter;
import com.techmath.textonphoto.interfaces.ShadowFragmentListener;
import com.techmath.textonphoto.photoeditor.RoundFrameLayout;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.Objects;

public class ShadowTextFragment extends Fragment implements ColorAdapter.ColorAdapterListener {


    SeekBar sbDyShadow;

    SeekBar sbDRadiusShadow;

    RoundFrameLayout btnPickerColorShadow;

    ShadowFragmentListener shadowFragmentListener;

    public void setListener(ShadowFragmentListener shadowFragmentListener) {
        this.shadowFragmentListener = shadowFragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_text_shadow, viewGroup, false);
        this.sbDyShadow = inflate.findViewById(R.id.sb_Dy_shadow);
        this.sbDRadiusShadow = inflate.findViewById(R.id.sb_dRadius_shadow);
        this.btnPickerColorShadow = inflate.findViewById(R.id.btn_picker_color_shadow);
        this.btnPickerColorShadow.setOnClickListener(view -> ColorPickerDialogBuilder.with(Objects.requireNonNull(ShadowTextFragment.this.getActivity())).setTitle("Select color").wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setPositiveButton("OK", (dialogInterface, i, numArr) -> {
            if (ShadowTextFragment.this.shadowFragmentListener != null) {
                ShadowTextFragment.this.shadowFragmentListener.onShadowColorSelected(i);
                ShadowTextFragment.this.btnPickerColorShadow.getDelegate().setBackgroundColor(i);
                ShadowTextFragment.this.btnPickerColorShadow.getDelegate().setStrokeColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.icChecked));
            }
        }).setNegativeButton("Cancel", (dialogInterface, i) -> {
        }).build().show());
        this.sbDRadiusShadow.setMax(100);
        this.sbDRadiusShadow.setProgress(0);
        this.sbDRadiusShadow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (ShadowTextFragment.this.shadowFragmentListener != null) {
                    ShadowTextFragment.this.shadowFragmentListener.onRadiusChangeListener(i / 10);
                }
            }
        });
        this.sbDyShadow.setMax(150);
        this.sbDyShadow.setProgress(0);
        this.sbDyShadow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (ShadowTextFragment.this.shadowFragmentListener != null) {
                    ShadowTextFragment.this.shadowFragmentListener.onDyShadowChangeListener(i / 10);
                }
            }
        });
        return inflate;
    }

    public void onColorItemSelected(int i) {

        if (shadowFragmentListener != null) {
            shadowFragmentListener.onShadowColorSelected(i);
        }
    }
}
