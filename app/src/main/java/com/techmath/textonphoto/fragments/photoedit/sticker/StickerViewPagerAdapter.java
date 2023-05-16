package com.techmath.textonphoto.fragments.photoedit.sticker;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.techmath.textonphoto.interfaces.StickerListener;

public class StickerViewPagerAdapter extends FragmentPagerAdapter implements StickerListener {


    StickerViewPagerAdapterListener stickerViewPagerAdapterListener;
    private final Fragment[] fragments;


    public interface StickerViewPagerAdapterListener {
        void onSticker(int i);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int i) {
        switch (i) {
            case 0:
                return "Cute";
            case 1:
                return "Love";
            case 2:
                return "Rainbow";
            case 3:
                return "Romantic";
            case 4:
                return "Heart";
            case 5:
                return "Birthday";
            case 6:
                return "Christmas";
            case 7:
                return "Flower";
            default:
                return "";
        }
    }

    public StickerViewPagerAdapter(FragmentManager fragmentManager, StickerViewPagerAdapterListener stickerViewPagerAdapterListener) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        CuteFragment cuteFragment = new CuteFragment();
        cuteFragment.setStickerListener(this);

        LoveFragment loveFragment = new LoveFragment();
        loveFragment.setStickerListener(this);

        RainbowFragment rainbowFragment = new RainbowFragment();
        rainbowFragment.setStickerListener(this);

        RomanticFragment romanticFragment = new RomanticFragment();
        romanticFragment.setStickerListener(this);

        HeartFragment heartFragment = new HeartFragment();
        heartFragment.setStickerListener(this);

        BirthdayFragment birthdayFragment = new BirthdayFragment();
        birthdayFragment.setStickerListener(this);

        ChristFragment christFragment = new ChristFragment();
        christFragment.setStickerListener(this);

        FlowersFragment flowersFragment = new FlowersFragment();
        flowersFragment.setStickerListener(this);

        this.fragments = new Fragment[]{ cuteFragment, loveFragment, rainbowFragment, romanticFragment, heartFragment, birthdayFragment, christFragment, flowersFragment};

        this.stickerViewPagerAdapterListener = stickerViewPagerAdapterListener;
    }


    @NonNull
    public Fragment getItem(int i) {
        return this.fragments[i];
    }

    public int getCount() {
        return this.fragments.length;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup viewGroup, int i, @NonNull Object obj) {
        super.setPrimaryItem(viewGroup, i, obj);
    }

    public void onStickerClick(int i) {

        if (stickerViewPagerAdapterListener != null) {
            stickerViewPagerAdapterListener.onSticker(i);
        }
    }
}
