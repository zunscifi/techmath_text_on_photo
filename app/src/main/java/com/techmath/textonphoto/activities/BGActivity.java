package com.techmath.textonphoto.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ironsource.mediationsdk.IronSource;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.adapter.sample.BackgroundColorAdapter;
import com.techmath.textonphoto.adapter.sample.BackgroundImageAdapter;
import com.techmath.textonphoto.adapter.sample.BackgroundImageAdapter2;
import com.techmath.textonphoto.adapter.sample.GenDataBackGround;


import com.techmath.textonphoto.model.ImgModel;
import com.techmath.textonphoto.util.AdManager;


public class BGActivity extends AppCompatActivity {


    private RecyclerView recyclerColors;
    private RecyclerView recyclerAbstract, recyclerLove;
    private RecyclerView recyclerNature, recyclerVehicle;
    private RecyclerView recyclerAnimal, recyclerCartoon;

    LinearLayout adContainer;
    RelativeLayout rl_native_ad;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_bg);

        findViewById(R.id.back).setOnClickListener(v -> {onBackPressed();});
        this.recyclerColors = findViewById(R.id.recyclerColor);
        setRecyclerColors();
        this.recyclerNature = findViewById(R.id.recyclerNature);
        setRecyclerNature();
        this.recyclerAbstract = findViewById(R.id.recyclerAbstract);
        setRecyclerAbstract();
        this.recyclerLove = findViewById(R.id.recyclerLove);
        setRecyclerLove();
        this.recyclerAnimal = findViewById(R.id.recyclerAnimal);
        setRecyclerNightSky();
        this.recyclerCartoon = findViewById(R.id.recyclerCartoon);
        setRecyclerCartoon();
        this.recyclerVehicle = findViewById(R.id.recyclerVehicle);
        setRecyclerVehicle();

        adContainer = findViewById(R.id.banner_container);
        rl_native_ad = findViewById(R.id.rl_native_ad);
        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(BGActivity.this);
            AdManager.loadBannerAd(BGActivity.this, adContainer);
            AdManager.loadNativeAd(BGActivity.this, rl_native_ad);
        } else {
            //ironSource + Fb banner Ads
            AdManager.inItIron(BGActivity.this);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AdManager.isloadFbAd) {
            AdManager.destroyIron();
//            AdManager.ironBanner(BGActivity.this, adContainer);
            AdManager.ironLargeBanner(BGActivity.this, rl_native_ad);
            // call the IronSource onResume method
            IronSource.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (AdManager.isloadFbAd) {
            // call the IronSource onPause method
            IronSource.onPause(this);
        }
    }

    private void setRecyclerColors() {
        this.recyclerColors.setHasFixedSize(true);
        this.recyclerColors.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        BackgroundColorAdapter colorAdapter = new BackgroundColorAdapter(GenDataBackGround.colorList(), this, (view, i) -> BGActivity.this.sendData(GenDataBackGround.colorList().get(i)));
        this.recyclerColors.setAdapter(colorAdapter);
    }

    private void setRecyclerAbstract() {
        this.recyclerAbstract.setHasFixedSize(true);
        this.recyclerAbstract.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        BackgroundImageAdapter absSkyAdapter = new BackgroundImageAdapter(GenDataBackGround.abstractList(), this, (view, i) -> BGActivity.this.sendData(GenDataBackGround.abstractList().get(i)));
        this.recyclerAbstract.setAdapter(absSkyAdapter);
    }

    private void setRecyclerNature() {
        this.recyclerNature.setHasFixedSize(true);
        this.recyclerNature.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        BackgroundImageAdapter natureAdapter = new BackgroundImageAdapter(GenDataBackGround.natureList(), this, (view, i) -> BGActivity.this.sendData(GenDataBackGround.natureList().get(i)));
        this.recyclerNature.setAdapter(natureAdapter);
    }

    private void setRecyclerLove() {
        this.recyclerLove.setHasFixedSize(true);
        this.recyclerLove.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        BackgroundImageAdapter2 loveAdapter = new BackgroundImageAdapter2(GenDataBackGround.loveList(), this, (view, i) -> BGActivity.this.sendData(GenDataBackGround.loveList().get(i)));
        this.recyclerLove.setAdapter(loveAdapter);
    }

    private void setRecyclerNightSky() {
        this.recyclerAnimal.setHasFixedSize(true);
        this.recyclerAnimal.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        BackgroundImageAdapter animalAdapter = new BackgroundImageAdapter(GenDataBackGround.animalList(), this, (view, i) -> BGActivity.this.sendData(GenDataBackGround.animalList().get(i)));
        this.recyclerAnimal.setAdapter(animalAdapter);
    }

    private void setRecyclerCartoon() {
        this.recyclerCartoon.setHasFixedSize(true);
        this.recyclerCartoon.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        BackgroundImageAdapter2 cartoonAdapter = new BackgroundImageAdapter2(GenDataBackGround.cartoonList(), this, (view, i) -> BGActivity.this.sendData(GenDataBackGround.cartoonList().get(i)));
        this.recyclerCartoon.setAdapter(cartoonAdapter);
    }

    private void setRecyclerVehicle() {
        this.recyclerVehicle.setHasFixedSize(true);
        this.recyclerVehicle.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        BackgroundImageAdapter2 vehicleAdapter = new BackgroundImageAdapter2(GenDataBackGround.vehicleList(), this, (view, i) -> BGActivity.this.sendData(GenDataBackGround.vehicleList().get(i)));
        this.recyclerVehicle.setAdapter(vehicleAdapter);
    }






    public void sendData(ImgModel sample) {

        Intent intent = new Intent(this, TextArtActivity.class);
        intent.putExtra("SampleBackground", sample.getImgModel());
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
