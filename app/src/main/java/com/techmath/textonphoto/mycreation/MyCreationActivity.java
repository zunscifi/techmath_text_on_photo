package com.techmath.textonphoto.mycreation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ironsource.mediationsdk.IronSource;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.activities.ArtPreviewActivity;
import com.techmath.textonphoto.util.AdManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class MyCreationActivity extends AppCompatActivity {

    public static ArrayList<String> videoPath = new ArrayList<String>();

    RecyclerView videoListView;
    MyVideoAdapter videoAdapter;
    int FLAG_VIDEO = 21;
    ImageView backIV;
    RelativeLayout header;

    LinearLayout adContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_creation);

        header = (RelativeLayout) findViewById(R.id.header);
        videoLoader();

        backIV = (ImageView) findViewById(R.id.back);
        backIV.setOnClickListener(v -> {
            onBackPressed();
        });

        adContainer = findViewById(R.id.banner_container);
        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(MyCreationActivity.this);
            AdManager.loadBannerAd(MyCreationActivity.this, adContainer);
        } else {
            //ironSource + Fb banner Ads
            AdManager.inItIron(MyCreationActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AdManager.isloadFbAd) {
            AdManager.destroyIron();
            AdManager.ironBanner(MyCreationActivity.this, adContainer);
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

    public void videoLoader() {
        getFromStorage();
        videoListView = (RecyclerView) findViewById(R.id.recyclerView);
        videoAdapter = new MyVideoAdapter(videoPath, MyCreationActivity.this, (v, position) -> {

            Intent intent = new Intent(MyCreationActivity.this, ArtPreviewActivity.class);
            intent.setData(Uri.fromFile(new File(videoPath.get(position))));
            startActivity(intent);

        });

        videoListView.setLayoutManager(new GridLayoutManager(this, 2));
        videoListView.setItemAnimator(new DefaultItemAnimator());
        videoListView.setAdapter(videoAdapter);

    }

    public void getFromStorage() {
        String folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/TextPhoto";
        File file = new File(folder);
        videoPath = new ArrayList<String>();
        if (file.isDirectory()) {
            File[] listFile = file.listFiles();
            Arrays.sort(listFile, (o1, o2) -> Long.compare(o2.lastModified(), o1.lastModified()));
            for (int i = 0; i < listFile.length; i++) {
                videoPath.add(listFile[i].getAbsolutePath());
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_VIDEO) {
            videoAdapter.notifyDataSetChanged();
            videoLoader();
        }
    }


}
