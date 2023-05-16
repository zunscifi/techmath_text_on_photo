package com.techmath.textonphoto.fragments.textedit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.interfaces.FormatTextFragmentListener;
import com.techmath.textonphoto.views.ToggleImageButton;

public class FormatTextFragment extends Fragment implements View.OnClickListener {

    ImageView btnAlignLeft;
    ImageView btnAlignCenter;
    ImageView btnAlignRight;
    ToggleImageButton btnBold;
    ToggleImageButton btnItalic;
    ToggleImageButton btnAllCaps;
    SeekBar seekbarTextSize;
    SeekBar sbPadding;

    FormatTextFragmentListener formatTextFragmentListener;

    public void setListener(FormatTextFragmentListener formatTextFragmentListener) {
        this.formatTextFragmentListener = formatTextFragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_text_format, viewGroup, false);
        this.btnAlignLeft = inflate.findViewById(R.id.btn_align_left);
        this.btnAlignCenter = inflate.findViewById(R.id.btn_align_center);
        this.btnAlignRight = inflate.findViewById(R.id.btn_align_right);
        this.btnAlignLeft.setOnClickListener(this);
        this.btnAlignRight.setOnClickListener(this);
        this.btnAlignCenter.setOnClickListener(this);
        this.btnBold = inflate.findViewById(R.id.btn_bold);
        this.btnItalic = inflate.findViewById(R.id.btn_italic);
        this.btnAllCaps = inflate.findViewById(R.id.btn_all_caps);
        this.seekbarTextSize = inflate.findViewById(R.id.seebar_text_size);
        this.seekbarTextSize.setMax(60);
        this.seekbarTextSize.setProgress(15);
        this.seekbarTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (FormatTextFragment.this.formatTextFragmentListener != null) {
                    FormatTextFragment.this.formatTextFragmentListener.onTextSize(i);
                }
            }
        });
        this.sbPadding = inflate.findViewById(R.id.sbPadding);
        this.sbPadding.setMax(100);
        this.sbPadding.setProgress(5);
        this.sbPadding.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (FormatTextFragment.this.formatTextFragmentListener != null) {
                    FormatTextFragment.this.formatTextFragmentListener.onTextPadding(i);
                }
            }
        });
        this.btnBold.setOnCheckedChangeListener((compoundButton, z) -> {
            if (FormatTextFragment.this.formatTextFragmentListener == null) {
                return;
            }
            if (z) {
                if (FormatTextFragment.this.btnItalic.isChecked()) {
                    FormatTextFragment.this.formatTextFragmentListener.onTextStyle(1);
                } else {
                    FormatTextFragment.this.formatTextFragmentListener.onTextStyle(2);
                }
            } else if (FormatTextFragment.this.btnItalic.isChecked()) {
                FormatTextFragment.this.formatTextFragmentListener.onTextStyle(3);
            } else {
                FormatTextFragment.this.formatTextFragmentListener.onTextStyle(4);
            }
        });
        this.btnItalic.setOnCheckedChangeListener((compoundButton, z) -> {
            if (FormatTextFragment.this.formatTextFragmentListener == null) {
                return;
            }
            if (z) {
                if (FormatTextFragment.this.btnBold.isChecked()) {
                    FormatTextFragment.this.formatTextFragmentListener.onTextStyle(1);
                } else if (!FormatTextFragment.this.btnBold.isChecked()) {
                    FormatTextFragment.this.formatTextFragmentListener.onTextStyle(3);
                }
            } else if (FormatTextFragment.this.btnBold.isChecked()) {
                FormatTextFragment.this.formatTextFragmentListener.onTextStyle(2);
            } else if (!FormatTextFragment.this.btnBold.isChecked()) {
                FormatTextFragment.this.formatTextFragmentListener.onTextStyle(4);
            }
        });
        this.btnAllCaps.setOnCheckedChangeListener((compoundButton, z) -> {
            if (FormatTextFragment.this.formatTextFragmentListener == null) {
                return;
            }
            if (z) {
                FormatTextFragment.this.formatTextFragmentListener.onTextStyle(5);
            } else {
                FormatTextFragment.this.formatTextFragmentListener.onTextStyle(6);
            }
        });
        return inflate;
    }

    public void onClick(View view) {
        if (this.formatTextFragmentListener != null) {
            switch (view.getId()) {
                case R.id.btn_align_center:
                    this.formatTextFragmentListener.onTextAlign(2);
                    return;
                case R.id.btn_align_left:
                    this.formatTextFragmentListener.onTextAlign(1);
                    return;
                case R.id.btn_align_right:
                    this.formatTextFragmentListener.onTextAlign(3);
                    return;
                default:
            }
        }
    }
}
