package com.techmath.textonphoto.fragments;


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
import com.techmath.textonphoto.interfaces.ColorFragmentListener;
import com.techmath.textonphoto.photoeditor.RoundFrameLayout;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.Objects;

public class ColorFragment extends Fragment implements ColorAdapter.ColorAdapterListener {


    RecyclerView recyclerView;
    ColorAdapter colorAdapter;
    RoundFrameLayout roundFrameLayout;
    SeekBar seekBar;
    ColorFragmentListener colorFragmentListener;

    public void setListener(ColorFragmentListener colorFragmentListener) {
        this.colorFragmentListener = colorFragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_text_color, viewGroup, false);
        this.seekBar = inflate.findViewById(R.id.sbTranparencyText);
        this.recyclerView = inflate.findViewById(R.id.recycler_color_text);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        this.colorAdapter = new ColorAdapter(getActivity(), this);
        this.recyclerView.setAdapter(this.colorAdapter);
        this.roundFrameLayout = inflate.findViewById(R.id.btn_picker_color_text);
        this.roundFrameLayout.setOnClickListener(view -> ColorPickerDialogBuilder.with(Objects.requireNonNull(ColorFragment.this.getActivity())).setTitle("Select color").wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setPositiveButton("OK", (dialogInterface, i, numArr) -> {
            if (ColorFragment.this.colorFragmentListener != null) {
                ColorFragment.this.colorFragmentListener.onColorSelected(i);
                ColorFragment.this.roundFrameLayout.getDelegate().setBackgroundColor(i);
                ColorFragment.this.roundFrameLayout.getDelegate().setStrokeColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.icChecked));
            }
        }).setNegativeButton("Cancel", (dialogInterface, i) -> {
        }).build().show());
        this.seekBar.setMax(255);
        this.seekBar.setProgress(255);
        this.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (ColorFragment.this.colorFragmentListener != null) {
                    ColorFragment.this.colorFragmentListener.onColorOpacityChangeListerner(i);
                }
            }
        });
        return inflate;
    }

    public void onColorItemSelected(int i) {

        if (colorFragmentListener != null) {
            colorFragmentListener.onColorSelected(i);
        }
    }
}
