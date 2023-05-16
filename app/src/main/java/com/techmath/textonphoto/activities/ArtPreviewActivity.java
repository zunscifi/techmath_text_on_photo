package com.techmath.textonphoto.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ironsource.mediationsdk.IronSource;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.util.PacList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.techmath.textonphoto.util.AdManager;

public class ArtPreviewActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    Bitmap bitmap = null;
    RelativeLayout relativeLayout;
    Uri uri;
    RelativeLayout rl_native_ad;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_art_preview);
        inItUI();

        this.uri = getIntent().getData();
        if (this.uri != null) {
            Glide.with(this).asBitmap().load(this.uri).into(new CustomTarget<Bitmap>() {
                public void onLoadCleared(@Nullable Drawable drawable) {
                }

                public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                    ArtPreviewActivity.this.imageView.setImageBitmap(bitmap);
                    ArtPreviewActivity.this.bitmap = bitmap;
                }
            });

        }


        rl_native_ad = findViewById(R.id.rl_native_ad);
        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(ArtPreviewActivity.this);
            AdManager.loadNativeAd(ArtPreviewActivity.this, rl_native_ad);
        } else {
            //ironSource + Fb banner Ads
            AdManager.inItIron(ArtPreviewActivity.this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AdManager.isloadFbAd) {
            AdManager.destroyIron();
            AdManager.ironLargeBanner(ArtPreviewActivity.this, rl_native_ad);
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

    private void inItUI() {
        this.imageView = findViewById(R.id.img_final);
        findViewById(R.id.btnMessage).setOnClickListener(this);
        this.relativeLayout = findViewById(R.id.relativeShare);
        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btn_home).setOnClickListener(this);
        findViewById(R.id.btnShareMore).setOnClickListener(this);
        findViewById(R.id.btnInstagram).setOnClickListener(this);
        findViewById(R.id.btnFacebook).setOnClickListener(this);
        findViewById(R.id.btnTelegram).setOnClickListener(this);
        findViewById(R.id.btnGmail).setOnClickListener(this);
        findViewById(R.id.btnWhatsApp).setOnClickListener(this);
        findViewById(R.id.btnTwitter).setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.btnBack:
                    super.onBackPressed();
                    return;
                case R.id.btnFacebook:
                    sharePhoto(PacList.FACE);
                    return;
                case R.id.btnGmail:
                    sharePhoto(PacList.GMAIL);
                    return;
                case R.id.btnInstagram:
                    sharePhoto(PacList.INSTA);
                    return;
                case R.id.btnTelegram:
                    sharePhoto(PacList.TELEGRAM);
                    return;
                case R.id.btnShareMore:
                    shareMore(uri.getPath());
                    return;
                case R.id.btnTwitter:
                    sharePhoto(PacList.TWITTER);
                    return;
                case R.id.btnMessage:
                    sharePhoto(PacList.MESSAGE);
                    return;
                case R.id.btnWhatsApp:
                    sharePhoto(PacList.WHATSAPP);
                    return;
                case R.id.btn_home:
                    Intent intent3 = new Intent(this, HomeActivity.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent3);
                    return;
                default:
            }
        }
    }

    public void shareMore(String uriPath) {
        MediaScannerConnection.scanFile(this, new String[]{uriPath}, (String[]) null, (str, uri) -> {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType("image/*");
            intent.putExtra("android.intent.extra.SUBJECT", str);
            intent.putExtra("android.intent.extra.TITLE", str);
            intent.putExtra("android.intent.extra.STREAM", uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);

            ArtPreviewActivity finalActivity = ArtPreviewActivity.this;
            finalActivity.startActivity(Intent.createChooser(intent, "Share with Friends"));
        });
    }

    public void sharePhoto(String str2) {
        if (isPackageInstalled(this, str2)) {
            MediaScannerConnection.scanFile(this, new String[]{uri.getPath()}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String str, Uri uri) {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("image/*");
                    intent.putExtra("android.intent.extra.SUBJECT", str);
                    intent.putExtra("android.intent.extra.TITLE", str);
                    intent.putExtra("android.intent.extra.STREAM", uri);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                    intent.setPackage(str2);
                    startActivity(intent);
                }
            });
            return;

        }
        Toast.makeText(this, "Can't find this App, please download and try it again", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent("android.intent.action.VIEW");
        intent2.setData(Uri.parse("market://details?id=" + str2));
        startActivity(intent2);
    }

    public static boolean isPackageInstalled(Context context, String str) {
        try {
            context.getPackageManager().getPackageInfo(str, PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }



}
