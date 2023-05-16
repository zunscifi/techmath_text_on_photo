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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.adapter.ColorAdapter;
import com.techmath.textonphoto.interfaces.HightLightFragmentListener;
import com.techmath.textonphoto.photoeditor.RoundFrameLayout;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.Objects;

public class HightLightTextFragment extends Fragment implements ColorAdapter.ColorAdapterListener {

    RecyclerView recyclerColorHighlight;
    ColorAdapter colorAdapter;
    RoundFrameLayout roundFrameLayout;
    HightLightFragmentListener hightLightFragmentListener;
    String e = "28";
    SeekBar sbtranparencyhighlight;
    SeekBar sbRadius;

    public String changeprogress(int i) {
        switch (i) {
            case 0:
                return "00";
            case 1:
                return "01";
            case 2:
                return "02";
            case 3:
                return "03";
            case 4:
                return "04";
            case 5:
                return "05";
            case 6:
                return "06";
            case 7:
                return "07";
            case 8:
                return "08";
            case 9:
                return "09";
            case 10:
                return "0A";
            case 11:
                return "0B";
            case 12:
                return "0C";
            case 13:
                return "0D";
            case 14:
                return "0E";
            case 15:
                return "0F";
            default:
                return null;
        }
    }

    public void setListener(HightLightFragmentListener hightLightFragmentListener) {
        this.hightLightFragmentListener = hightLightFragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_text_highlight, viewGroup, false);
        this.sbtranparencyhighlight = inflate.findViewById(R.id.sbTranparencyHighlight);
        this.sbRadius = inflate.findViewById(R.id.sbRadius);
        this.recyclerColorHighlight = inflate.findViewById(R.id.recycler_color_highlight);
        this.recyclerColorHighlight.setHasFixedSize(true);
        this.recyclerColorHighlight.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        this.colorAdapter = new ColorAdapter(getActivity(), this);
        this.recyclerColorHighlight.setAdapter(this.colorAdapter);
        this.sbtranparencyhighlight.setProgress(0);
        this.sbtranparencyhighlight.setMax(255);
        this.sbtranparencyhighlight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (i < 16) {
                    HightLightTextFragment hightLightTextFragment = HightLightTextFragment.this;
                    hightLightTextFragment.e = hightLightTextFragment.changeprogress(i);
                } else {
                    HightLightTextFragment.this.e = Integer.toHexString(i);
                }
                if (HightLightTextFragment.this.hightLightFragmentListener != null) {
                    HightLightTextFragment.this.hightLightFragmentListener.onHightLightColorOpacityChangeListerner(HightLightTextFragment.this.e);
                }
            }
        });
        this.sbRadius.setMax(100);
        this.sbRadius.setProgress(0);
        this.sbRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (HightLightTextFragment.this.hightLightFragmentListener != null) {
                    HightLightTextFragment.this.hightLightFragmentListener.onHighLightRadius(i);
                }
            }
        });
        this.roundFrameLayout = inflate.findViewById(R.id.btn_picker_color_highlight);
        this.roundFrameLayout.setOnClickListener(view -> ColorPickerDialogBuilder.with(Objects.requireNonNull(HightLightTextFragment.this.getActivity())).setTitle("Select color").wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setPositiveButton("OK", (dialogInterface, i, numArr) -> {
            if (HightLightTextFragment.this.hightLightFragmentListener != null) {
                HightLightTextFragment.this.hightLightFragmentListener.onHightLightColorSelected(i);
                HightLightTextFragment.this.roundFrameLayout.getDelegate().setBackgroundColor(i);
                HightLightTextFragment.this.roundFrameLayout.getDelegate().setStrokeColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.icChecked));
            }
        }).setNegativeButton("Cancel", (dialogInterface, i) -> {
        }).build().show());
        return inflate;
    }

    public void onColorItemSelected(int i) {

        if (hightLightFragmentListener != null) {
            hightLightFragmentListener.onHightLightColorSelected(i);
        }
    }
}
