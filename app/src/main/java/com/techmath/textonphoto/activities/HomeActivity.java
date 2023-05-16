package com.techmath.textonphoto.activities;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.ironsource.mediationsdk.IronSource;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.util.Utils;
import com.techmath.textonphoto.mycreation.MyCreationActivity;
import com.techmath.textonphoto.util.AdManager;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "HomeActivity";

    CardView btn_start, btn_sample,btnCreation;
    public int requestMode = 1;


    ImageView img_logo, mBg;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_home);

        img_logo = findViewById(R.id.img_logo);
        Glide.with(this)
                .load(R.drawable.main_logo)
                .into(img_logo);

        mBg = findViewById(R.id.mBg);
        Glide.with(this)
                .load(R.drawable.main_bg_ac)
                .into(mBg);

        initViews();


        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(HomeActivity.this);
            AdManager.loadInterAd(HomeActivity.this);
        } else {
            //ironSource + Fb banner Ads
            AdManager.inItIron(HomeActivity.this);
            AdManager.ironInterstitial(HomeActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AdManager.isloadFbAd) {
            AdManager.destroyIron();
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

    private void initViews() {
        this.btn_sample = findViewById(R.id.btnTemplate);
        this.btn_start = findViewById(R.id.btnTextArt);
        this.btnCreation = findViewById(R.id.btnCreation);

        this.btn_start.setOnClickListener(this);
        this.btn_sample.setOnClickListener(this);
        this.btnCreation.setOnClickListener(this);
        findViewById(R.id.bt_share).setOnClickListener(this);
        findViewById(R.id.bt_rate).setOnClickListener(this);
        findViewById(R.id.bt_more).setOnClickListener(this);
        findViewById(R.id.bt_privacy).setOnClickListener(this);

    }


    @SuppressLint("NonConstantResourceId")
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.bt_rate:
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                }
                return;

            case R.id.bt_share:
                shareApp();
                return;

            case R.id.btnTextArt:
                pickFromGalery();
                return;

            case R.id.btnCreation:
                AdManager.adCounter =  AdManager.adDisplayCounter-1;
                if (Utils.hasPermissions(HomeActivity.this, Utils.permissions)) {
                    ActivityCompat.requestPermissions(HomeActivity.this, Utils.permissions, Utils.perRequest);
                }else {
                    navigate(new Intent(HomeActivity.this, MyCreationActivity.class));
                }
                return;

            case R.id.btnTemplate:
                AdManager.adCounter =  AdManager.adDisplayCounter-1;
                if (Utils.hasPermissions(HomeActivity.this, Utils.permissions)) {
                    ActivityCompat.requestPermissions(HomeActivity.this, Utils.permissions, Utils.perRequest);
                }else {
                    navigate(new Intent(HomeActivity.this, BGActivity.class));
                }
                return;

            case R.id.bt_more:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=7081479513420377164&hl=en")));
                return;

            case R.id.bt_privacy:
                startActivity(new Intent(HomeActivity.this, PrivacyActivity.class));
                return;

            default:
        }
    }

    void navigate(Intent intent) {
        AdManager.adCounter++;
//        Log.e("navigate: ", ""+AdManager.adCounter);
        if (AdManager.isloadFbAd){
            AdManager.ironShowInterstitial(HomeActivity.this, intent, 0);
        }else {
            AdManager.showInterAd(HomeActivity.this, intent, 0);
        }
    }

    private void shareApp() {
        Intent myapp = new Intent(Intent.ACTION_SEND);
        myapp.setType("text/plain");
        myapp.putExtra(Intent.EXTRA_TEXT, "Download this awesome app\n https://play.google.com/store/apps/details?id=" + getPackageName() + " \n");
        startActivity(myapp);
    }


    public void pickFromGalery() {
        Log.i(TAG, "pickFromGalery");
        if (Utils.hasPermissions(HomeActivity.this, Utils.permissions)) {
            ActivityCompat.requestPermissions(HomeActivity.this, Utils.permissions, Utils.perRequest);
        }else {
            Intent addCategory = new Intent("android.intent.action.GET_CONTENT").setType("image/*").addCategory("android.intent.category.OPENABLE");
            addCategory.putExtra("android.intent.extra.MIME_TYPES", new String[]{"image/jpeg", "image/png"});
            startActivityForResult(Intent.createChooser(addCategory, "Select Picture"), HomeActivity.this.requestMode);
        }
    }


    @Override
    public void onActivityResult(int i, int i2, @Nullable Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            if (i == this.requestMode) {
                Uri uri = null;
                if (intent != null) {
                    uri = intent.getData();
                }
                if (uri != null) {
                    Log.i(TAG, "startCrop Image");
                    startCrop(uri);
                } else {
                    Toast.makeText(this, "Select image again", Toast.LENGTH_SHORT).show();
                }
            }  else if (i == 69) {
                if (intent != null) {
                    handleCropResult(intent);
                } else {
                    return;
                }
            }
        }
        if (i2 == 96 && intent != null) {
            handleCropError(intent);
        }
    }

    private void handleCropError(Intent intent) {
        Throwable error = UCrop.getError(intent);
        if (error != null) {
            Log.e(TAG, "handleCropError: ", error);
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this, "Unexpected error", Toast.LENGTH_SHORT).show();
    }

    private void handleCropResult(Intent intent) {
        Uri output = UCrop.getOutput(intent);
        if (output != null) {
            Intent intent2 = new Intent(this, TextArtActivity.class);
            intent2.setData(output);
            AdManager.adCounter =  AdManager.adDisplayCounter-1;
            navigate(intent2);
            return;
        }
        Toast.makeText(this, "Cannot retrieve cropped image", Toast.LENGTH_SHORT).show();
    }

    private void startCrop(Uri uri) {
        String sampleCropImage = "SampleCropImage";
        UCrop of = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), sampleCropImage)));
        of.useSourceImageAspectRatio();
        of.useSourceImageAspectRatio();
        UCrop.Options options = new UCrop.Options();
        options.setActiveControlsWidgetColor(ActivityCompat.getColor(HomeActivity.this, R.color.button_color));
        options.setToolbarTitle("Crop Your Image");
        options.setToolbarColor(getResources().getColor(R.color.main_bg));
        options.setToolbarWidgetColor(getResources().getColor(R.color.white));
        options.setRootViewBackgroundColor(getResources().getColor(R.color.main_color));
        options.setStatusBarColor(getResources().getColor(R.color.main_bg));
        options.setLogoColor(getResources().getColor(R.color.white));
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setFreeStyleCropEnabled(true);
        of.withOptions(options);
        of.start(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
