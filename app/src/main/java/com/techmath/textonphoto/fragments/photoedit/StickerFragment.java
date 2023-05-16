package com.techmath.textonphoto.fragments.photoedit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.fragments.photoedit.sticker.StickerViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class StickerFragment extends Fragment {


    TabLayout tabLayout;

    ViewPager viewPager;

    StickerFragmentListener stickerFragmentListener;

    public interface StickerFragmentListener {
        void onStickerFragmentClick(Bitmap bitmap);
    }

    public void setStickerFragmentListener(StickerFragmentListener stickerFragmentListener) {
        this.stickerFragmentListener = stickerFragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_image_sticker, viewGroup, false);
        this.tabLayout = inflate.findViewById(R.id.tablayoutSticker);
        this.viewPager = inflate.findViewById(R.id.viewpagerSticker);
        this.viewPager.setAdapter(new StickerViewPagerAdapter(getChildFragmentManager(), i -> {
            if (StickerFragment.this.stickerFragmentListener != null) {
                StickerFragment.this.stickerFragmentListener.onStickerFragmentClick(BitmapFactory.decodeResource(StickerFragment.this.getResources(), i));
            }
        }));
        this.tabLayout.setupWithViewPager(this.viewPager);
        return inflate;
    }


}
