package com.techmath.textonphoto.fragments.photoedit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.techmath.textonphoto.R;
import com.techmath.textonphoto.interfaces.OverlaysFragmentListener;

public class OverlaysFragment extends Fragment implements View.OnClickListener {


    LinearLayout icPhrases;
    LinearLayout icFood;
    LinearLayout icLove;
    LinearLayout icChristmas;
    LinearLayout icSayings;
    LinearLayout icNative;
    LinearLayout icSummer;
    LinearLayout icWinter;
    LinearLayout icTravel;
    LinearLayout icEmoticons;
    LinearLayout icMotivation;
    LinearLayout icFitness;
    LinearLayout icGeometry;
    LinearLayout icHalloween;


    OverlaysFragmentListener overlaysFragmentListener;

    public void setListener(OverlaysFragmentListener overlaysFragmentListener) {
        this.overlaysFragmentListener = overlaysFragmentListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_image_overlays, viewGroup, false);
        this.icPhrases = inflate.findViewById(R.id.ic_phrases);
        this.icFood = inflate.findViewById(R.id.ic_food);
        this.icLove = inflate.findViewById(R.id.ic_love);
        this.icChristmas = inflate.findViewById(R.id.ic_christmas);
        this.icSayings = inflate.findViewById(R.id.ic_sayings);
        this.icNative = inflate.findViewById(R.id.ic_native);
        this.icSummer = inflate.findViewById(R.id.ic_summer);
        this.icWinter = inflate.findViewById(R.id.ic_winter);
        this.icTravel = inflate.findViewById(R.id.ic_travel);
        this.icEmoticons = inflate.findViewById(R.id.ic_emoticons);
        this.icMotivation = inflate.findViewById(R.id.ic_motivation);
        this.icFitness = inflate.findViewById(R.id.ic_fitness);
        this.icGeometry = inflate.findViewById(R.id.ic_geometry);
        this.icHalloween = inflate.findViewById(R.id.ic_halloween);
        this.icPhrases.setOnClickListener(this);
        this.icFood.setOnClickListener(this);
        this.icLove.setOnClickListener(this);
        this.icChristmas.setOnClickListener(this);
        this.icSayings.setOnClickListener(this);
        this.icNative.setOnClickListener(this);
        this.icSummer.setOnClickListener(this);
        this.icWinter.setOnClickListener(this);
        this.icTravel.setOnClickListener(this);
        this.icEmoticons.setOnClickListener(this);
        this.icMotivation.setOnClickListener(this);
        this.icFitness.setOnClickListener(this);
        this.icGeometry.setOnClickListener(this);
        this.icHalloween.setOnClickListener(this);
        return inflate;
    }

    public void onClick(View view) {
        if (this.overlaysFragmentListener != null) {
            switch (view.getId()) {
                case R.id.ic_christmas:
                    this.overlaysFragmentListener.onChristmas();
                    return;
                case R.id.ic_emoticons:
                    this.overlaysFragmentListener.onEmoticons();
                    return;
                case R.id.ic_fitness:
                    this.overlaysFragmentListener.onFitness();
                    return;
                case R.id.ic_food:
                    this.overlaysFragmentListener.onFood();
                    return;
                case R.id.ic_geometry:
                    this.overlaysFragmentListener.onGeometry();
                    return;
                case R.id.ic_halloween:
                    this.overlaysFragmentListener.onHalloween();
                    return;
                case R.id.ic_love:
                    this.overlaysFragmentListener.onLove();
                    return;
                case R.id.ic_motivation:
                    this.overlaysFragmentListener.onMotivation();
                    return;
                case R.id.ic_native:
                    this.overlaysFragmentListener.onNative();
                    return;
                case R.id.ic_phrases:
                    this.overlaysFragmentListener.onPhrases();
                    return;
                case R.id.ic_sayings:
                    this.overlaysFragmentListener.onSayings();
                    return;
                case R.id.ic_summer:
                    this.overlaysFragmentListener.onSummer();
                    return;
                case R.id.ic_travel:
                    this.overlaysFragmentListener.onTravel();
                    return;
                case R.id.ic_winter:
                    this.overlaysFragmentListener.onWinter();
                    return;
                default:
            }
        }
    }
}
