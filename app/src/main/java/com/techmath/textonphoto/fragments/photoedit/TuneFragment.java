package com.techmath.textonphoto.fragments.photoedit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.techmath.textonphoto.R;
import com.techmath.textonphoto.views.CustomSeekBar;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class TuneFragment extends Fragment {

    TabLayout tabLayout;
    TuneFragmentListener tuneFragmentListener;
    public int currentBright;
    public int currentContrast;
    public int currentHue;
    public int currentSaturation;
    public CustomSeekBar seekBar;
    public int tabSelected = 0;
    public TextView tvProgress;

    public interface TuneFragmentListener {
        void onBrightnessChosse(int i);

        void onConstrastChosse(int i);

        void onHueChosee(int i);

        void onSaturationChosse(int i);
    }

    public void setTuneFragmentListener(TuneFragmentListener tuneFragmentListener) {
        this.tuneFragmentListener = tuneFragmentListener;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {

        super.onSaveInstanceState(bundle);
        if (tabLayout != null) {
            bundle.putInt("selected", tabLayout.getSelectedTabPosition());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_image_tune, viewGroup, false);
        this.seekBar = inflate.findViewById(R.id.seekbar_tune);
        this.tvProgress = inflate.findViewById(R.id.tvTuneProgress);
        SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (TuneFragment.this.tuneFragmentListener != null) {
                    if (TuneFragment.this.tabSelected == 0) {
                        TuneFragment.this.currentBright = i - 50;
                        TuneFragment.this.tvProgress.setText(String.valueOf(TuneFragment.this.currentBright));
                        TuneFragment.this.tuneFragmentListener.onBrightnessChosse(TuneFragment.this.currentBright);
                    } else if (TuneFragment.this.tabSelected == 1) {
                        int i2 = i - 50;
                        TuneFragment.this.currentContrast = i2;
                        TuneFragment.this.tvProgress.setText(String.valueOf(i2));
                        TuneFragment.this.tuneFragmentListener.onConstrastChosse(i2);
                    } else if (TuneFragment.this.tabSelected == 2) {
                        int i3 = i - 50;
                        TuneFragment.this.currentHue = i3;
                        TuneFragment.this.tvProgress.setText(String.valueOf(i3));
                        TuneFragment.this.tuneFragmentListener.onHueChosee(i3);
                    } else {
                        int i4 = i - 50;
                        TuneFragment.this.currentSaturation = i4;
                        TuneFragment.this.tvProgress.setText(String.valueOf(i4));
                        TuneFragment.this.tuneFragmentListener.onSaturationChosse(i4);
                    }
                }
            }
        };
        this.seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        this.tabLayout = inflate.findViewById(R.id.tablayoutTune);
        TabLayout tabLayout1 = this.tabLayout;
        tabLayout1.addTab(tabLayout1.newTab().setText("Brightness"));
        TabLayout tabLayout2 = this.tabLayout;
        tabLayout2.addTab(tabLayout2.newTab().setText("Contrast"));
        TabLayout tabLayout3 = this.tabLayout;
        tabLayout3.addTab(tabLayout3.newTab().setText("Hue"));
        TabLayout tabLayout4 = this.tabLayout;
        tabLayout4.addTab(tabLayout4.newTab().setText("Saturation"));
        Objects.requireNonNull(this.tabLayout.getTabAt(this.tabSelected)).select();
        TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            public void onTabSelected(TabLayout.Tab tab) {
                TuneFragment.this.tabSelected = tab.getPosition();
                Log.d("XXXXXXXX", "onTabSelected " + TuneFragment.this.tabSelected + " " + TuneFragment.this.currentBright);
                if (TuneFragment.this.tabSelected == 0) {
                    TuneFragment.this.seekBar.setProgress(TuneFragment.this.currentBright + 50);
                } else if (TuneFragment.this.tabSelected == 1) {
                    TuneFragment.this.seekBar.setProgress(TuneFragment.this.currentContrast + 50);
                } else if (TuneFragment.this.tabSelected == 2) {
                    TuneFragment.this.seekBar.setProgress(TuneFragment.this.currentHue + 50);
                } else {
                    TuneFragment.this.seekBar.setProgress(TuneFragment.this.currentSaturation + 50);
                }
            }

            public void onTabReselected(TabLayout.Tab tab) {
                TuneFragment.this.tabSelected = tab.getPosition();
                Log.d("XXXXXXXX", "onTabReselected " + TuneFragment.this.tabSelected + " " + TuneFragment.this.currentBright);
                if (TuneFragment.this.tabSelected == 0) {
                    TuneFragment.this.seekBar.setProgress(TuneFragment.this.currentBright + 50);
                } else if (TuneFragment.this.tabSelected == 1) {
                    TuneFragment.this.seekBar.setProgress(TuneFragment.this.currentContrast + 50);
                } else if (TuneFragment.this.tabSelected == 2) {
                    TuneFragment.this.seekBar.setProgress(TuneFragment.this.currentHue + 50);
                } else {
                    TuneFragment.this.seekBar.setProgress(TuneFragment.this.currentSaturation + 50);
                }
            }
        };
        this.tabLayout.addOnTabSelectedListener(onTabSelectedListener);
        return inflate;
    }
}
