package com.techmath.textonphoto.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.ironsource.mediationsdk.IronSource;
import com.techmath.textonphoto.R;
import com.techmath.textonphoto.adapter.ColorAdapter;
import com.techmath.textonphoto.adapter.ViewPagerAdapter;
import com.techmath.textonphoto.dialog.ExitDialog;
import com.techmath.textonphoto.dialog.LoadingDialog;
import com.techmath.textonphoto.dialog.TextEditorDialog;
import com.techmath.textonphoto.fragments.ColorFragment;
import com.techmath.textonphoto.fragments.TextEditorFragment;
import com.techmath.textonphoto.fragments.photoedit.EmojiFragment;
import com.techmath.textonphoto.fragments.photoedit.OverlaysFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.ChristmasFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.EmoticonsFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.FitnessFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.FoodFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.GeometryFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.HalloweenFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.LoveFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.MotivationFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.NativeFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.PhrasesFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.SayingsFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.SummerFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.TravelFragment;
import com.techmath.textonphoto.fragments.photoedit.stickertext.WinterFragment;
import com.techmath.textonphoto.fragments.photoedit.PhotoFragment;
import com.techmath.textonphoto.fragments.photoedit.StickerFragment;
import com.techmath.textonphoto.fragments.photoedit.TuneFragment;
import com.techmath.textonphoto.fragments.textedit.FontStyleFragment;
import com.techmath.textonphoto.fragments.textedit.FormatTextFragment;
import com.techmath.textonphoto.fragments.textedit.HightLightTextFragment;
import com.techmath.textonphoto.fragments.textedit.ShadowTextFragment;
import com.techmath.textonphoto.fragments.textedit.SpacingTextFragment;
import com.techmath.textonphoto.interfaces.ColorFragmentListener;
import com.techmath.textonphoto.interfaces.FontFragmentListener;
import com.techmath.textonphoto.interfaces.FormatTextFragmentListener;
import com.techmath.textonphoto.interfaces.HightLightFragmentListener;
import com.techmath.textonphoto.interfaces.OverlaysFragmentListener;
import com.techmath.textonphoto.interfaces.OverplayListener;
import com.techmath.textonphoto.interfaces.ShadowFragmentListener;
import com.techmath.textonphoto.interfaces.SpacingFragmentListener;
import com.techmath.textonphoto.interfaces.StrokeFragmentListener;
import com.techmath.textonphoto.photoeditor.OnPhotoEditorListener;
import com.techmath.textonphoto.photoeditor.PhotoEditor;
import com.techmath.textonphoto.photoeditor.PhotoEditorView;
import com.techmath.textonphoto.photoeditor.RoundFrameLayout;
import com.techmath.textonphoto.photoeditor.RoundViewDelegate;
import com.techmath.textonphoto.photoeditor.SaveSettings;
import com.techmath.textonphoto.photoeditor.StrokeTextView;
import com.techmath.textonphoto.photoeditor.ViewType;
import com.techmath.textonphoto.unit.BitmapProcess;
import com.techmath.textonphoto.unit.ColorFilterGenerator;
import com.techmath.textonphoto.unit.ViewAnimation;
import com.bumptech.glide.Glide;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.techmath.textonphoto.util.AdManager;
import com.techmath.textonphoto.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TextArtActivity extends AppCompatActivity implements View.OnClickListener, TextEditorFragment.TextFragmentListener, EmojiFragment.EmojiListener, PhotoFragment.PhotoListener, StickerFragment.StickerFragmentListener, TuneFragment.TuneFragmentListener, ColorFragmentListener, FontFragmentListener, FormatTextFragmentListener, HightLightFragmentListener, OverlaysFragmentListener, OverplayListener, ShadowFragmentListener, SpacingFragmentListener, StrokeFragmentListener, OnPhotoEditorListener {
    private RoundFrameLayout border;
    private int brightnessFinal = 0;
    private RoundFrameLayout btnColorPicker;
    public ImageView btnSave;
    public ImageView btnRedo;
    public ImageView btnUndo;
    private int colorBackground;
    private int colorTextShadow = Color.parseColor("#000000");
    private int constrantFinal = 1;
    private int countMain = 0;
    private int countOverplay = 0;
    private int countPhoto = 0;
    private int countText = 0;
    public int currentTabTool = 0;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private int hueFinal = 1;
    public ImageView imageViewMain;
    private PhotoEditorView imgPhotoEditor;
    private ChristmasFragment mChristmasFragment;
    private EmoticonsFragment mEmoticonsFragment;
    private FitnessFragment mFitnessFragment;
    private FoodFragment mFoodFragment;
    private GeometryFragment mGeometryFragment;
    private HalloweenFragment mHalloweenFragment;
    private LoveFragment mLoveFragment;
    private MotivationFragment mMotivationFragment;
    private NativeFragment mNativeFragment;
    public PhotoEditor mPhotoEditor;
    private PhrasesFragment mPhrasesFragment;
    private SayingsFragment mSayingFragment;
    private SummerFragment mSummerFragment;
    private TravelFragment mTravelFragment;
    private WinterFragment mWinterFragment;
    public int numberAddedView;
    private String opticalBackground = "66";
    private int opticalText = 255;
    public ProgressBar progressBarLoading;
    private int rRadius = 0;
    private int rY = 0;
    private RecyclerView recyclerPhotoColor;
    public Bitmap resourceGraphic;
    private RelativeLayout rlColorPhoto;
    public RelativeLayout rlMainTool;
    private RelativeLayout rlPhotoTools;
    private RelativeLayout rlTextTool;
    private int saturationFinal = 1;
    private SeekBar sbRotatePhoto;
    private SeekBar sbTranparencyPhoto;
    private int styleText;
    private TabLayout tabLayoutTextTools;
    public TabLayout tablayoutImageTools;
    private StrokeTextView textViewMain;
    public View viewMain;
    private ViewPager viewPagerImageTools;
    private ViewPager viewPagerTextTools;
    ExitDialog discardDialog;

    public void onStartViewChangeListener(ViewType viewType) {
    }

    public void onStopViewChangeListener(ViewType viewType) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_text_art);

        addControls();
        addPhotoColor();
        setImageTranparency();


        this.mPhotoEditor = new PhotoEditor.Builder(this, this.imgPhotoEditor).setPinchTextScalable(true).setDefaultEmojiTypeface(Typeface.createFromAsset(getAssets(), "font/font5.TTF")).build();
        this.mPhotoEditor.setOnPhotoEditorListener(this);


        getData();


        setupViewPager(this.viewPagerTextTools);
        this.tabLayoutTextTools.setupWithViewPager(this.viewPagerTextTools);
        setupViewPagerImageTools(this.viewPagerImageTools);
        this.tablayoutImageTools.setupWithViewPager(this.viewPagerImageTools);


        setupTabIconsTextTool();
        setupTabIconsImageTool();

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.initAd(TextArtActivity.this);
            AdManager.loadInterAd(TextArtActivity.this);
        } else {
            //ironSource + Fb banner Ads
            AdManager.inItIron(TextArtActivity.this);
            AdManager.ironInterstitial(TextArtActivity.this);
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

    private void setImageTranparency() {
        this.sbTranparencyPhoto.setMax(255);
        this.sbTranparencyPhoto.setProgress(255);
        this.sbTranparencyPhoto.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (TextArtActivity.this.imageViewMain != null) {
                    TextArtActivity.this.imageViewMain.setImageAlpha(i);
                }
            }
        });
        this.sbRotatePhoto.setMax(360);
        this.sbRotatePhoto.setProgress(0);
        this.sbRotatePhoto.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (TextArtActivity.this.viewMain != null) {
                    TextArtActivity.this.viewMain.setRotation((float) i);
                }
            }
        });
    }

    private void addPhotoColor() {
        this.recyclerPhotoColor.setHasFixedSize(true);
        this.recyclerPhotoColor.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        ColorAdapter colorAdapter = new ColorAdapter(this, i -> BitmapProcess.changeBitmapColor(TextArtActivity.this.resourceGraphic, TextArtActivity.this.imageViewMain, i));
        this.recyclerPhotoColor.setAdapter(colorAdapter);
        this.btnColorPicker.setOnClickListener(view -> ColorPickerDialogBuilder.with(TextArtActivity.this).setTitle("Select color").wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setPositiveButton("OK", (dialogInterface, i, numArr) -> BitmapProcess.changeBitmapColor(TextArtActivity.this.resourceGraphic, TextArtActivity.this.imageViewMain, i)).setNegativeButton("Cancel", (dialogInterface, i) -> {
        }).build().show());
    }

    private void setupTabIconsTextTool() {
        TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        textView.setText("Font");
        textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_font_style, 0, 0);
        Objects.requireNonNull(this.tabLayoutTextTools.getTabAt(0)).setCustomView(textView);
        TextView textView2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        textView2.setText("Format");
        textView2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_font_format, 0, 0);
        Objects.requireNonNull(this.tabLayoutTextTools.getTabAt(1)).setCustomView(textView2);
        TextView textView3 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        textView3.setText("Color");
        textView3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_font_color, 0, 0);
        Objects.requireNonNull(this.tabLayoutTextTools.getTabAt(2)).setCustomView(textView3);


        TextView textView5 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        textView5.setText("Highlight");
        textView5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_font_bg, 0, 0);
        Objects.requireNonNull(this.tabLayoutTextTools.getTabAt(3)).setCustomView(textView5);
        TextView textView6 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        textView6.setText("Shadow");
        textView6.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_font_shadow, 0, 0);
        Objects.requireNonNull(this.tabLayoutTextTools.getTabAt(4)).setCustomView(textView6);
        if (Build.VERSION.SDK_INT >= 21) {
            TextView textView7 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            textView7.setText("Spacing");
            textView7.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_font_spacing, 0, 0);
            Objects.requireNonNull(this.tabLayoutTextTools.getTabAt(5)).setCustomView(textView7);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        FontStyleFragment fontFragment = new FontStyleFragment();
        fontFragment.setListener(this);
        viewPagerAdapter.addFrag(fontFragment, "Font");
        FormatTextFragment formatTextFragment = new FormatTextFragment();
        viewPagerAdapter.addFrag(formatTextFragment, "Format");
        formatTextFragment.setListener(this);
        ColorFragment colorFragment = new ColorFragment();
        viewPagerAdapter.addFrag(colorFragment, "Color");
        colorFragment.setListener(this);

        HightLightTextFragment hightLightTextFragment = new HightLightTextFragment();
        viewPagerAdapter.addFrag(hightLightTextFragment, "Highlight");
        hightLightTextFragment.setListener(this);
        ShadowTextFragment shadowTextFragment = new ShadowTextFragment();
        viewPagerAdapter.addFrag(shadowTextFragment, "Shadow");
        shadowTextFragment.setListener(this);
        if (Build.VERSION.SDK_INT >= 21) {
            SpacingTextFragment spacingTextFragment = new SpacingTextFragment();
            viewPagerAdapter.addFrag(spacingTextFragment, "Spacing");
            spacingTextFragment.setListener(this);
        }
        viewPager.setAdapter(viewPagerAdapter);
        if (Build.VERSION.SDK_INT >= 21) {
            viewPager.setOffscreenPageLimit(7);
        } else {
            viewPager.setOffscreenPageLimit(6);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setupTabIconsImageTool() {
        TextView textView = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        textView.setText("Add");
        textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_add_text, 0, 0);
        Objects.requireNonNull(this.tablayoutImageTools.getTabAt(0)).setCustomView(textView);
        TextView textView2 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        textView2.setText("Sticker");
        textView2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_add_sticker, 0, 0);
        Objects.requireNonNull(this.tablayoutImageTools.getTabAt(1)).setCustomView(textView2);
        TextView textView3 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        textView3.setText("Overlays");
        textView3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_add_overlay, 0, 0);
        Objects.requireNonNull(this.tablayoutImageTools.getTabAt(2)).setCustomView(textView3);
        int i = 3;
        if (Build.VERSION.SDK_INT >= 21) {
            TextView textView4 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            textView4.setText("Emoji");
            textView4.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_add_emoji, 0, 0);
            Objects.requireNonNull(this.tablayoutImageTools.getTabAt(3)).setCustomView(textView4);
            i = 4;
        }
        TextView textView5 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        textView5.setText("Photo");
        textView5.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_add_photo, 0, 0);
        Objects.requireNonNull(this.tablayoutImageTools.getTabAt(i)).setCustomView(textView5);
        TextView textView6 = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        textView6.setText("Tune");
        textView6.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_add_tune, 0, 0);
        Objects.requireNonNull(this.tablayoutImageTools.getTabAt(i + 1)).setCustomView(textView6);
        if ((getResources().getConfiguration().screenLayout & 15) == 4) {

            this.tablayoutImageTools.setTabMode(TabLayout.MODE_FIXED);
        }
        this.tablayoutImageTools.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @SuppressLint("ResourceType")
            public void onTabSelected(TabLayout.Tab tab) {
                TextArtActivity.this.currentTabTool = tab.getPosition();
                if (tab.getPosition() != 0) {
                    tab.getPosition();
                } else if (TextArtActivity.this.numberAddedView < 6) {
                    TextArtActivity.this.mPhotoEditor.addText(TextArtActivity.this.getString(R.string.double_tap), ContextCompat.getColor(TextArtActivity.this, 17170443));
                } else {
                    Toast.makeText(TextArtActivity.this, R.string.max_item, Toast.LENGTH_SHORT).show();
                }
            }

            @SuppressLint("ResourceType")
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() != 0) {
                    return;
                }
                if (TextArtActivity.this.numberAddedView < 6) {
                    TextArtActivity.this.mPhotoEditor.addText(TextArtActivity.this.getString(R.string.double_tap), ContextCompat.getColor(TextArtActivity.this, 17170443));
                } else {
                    Toast.makeText(TextArtActivity.this, R.string.max_item, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupViewPagerImageTools(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new Fragment(), "Add");
        StickerFragment stickerFragment = new StickerFragment();
        viewPagerAdapter.addFrag(stickerFragment, "Sticker");
        stickerFragment.setStickerFragmentListener(this);
        OverlaysFragment overlaysFragment = new OverlaysFragment();
        viewPagerAdapter.addFrag(overlaysFragment, "Overlays");
        overlaysFragment.setListener(this);
        if (Build.VERSION.SDK_INT >= 21) {
            EmojiFragment emojiFragment = new EmojiFragment();
            viewPagerAdapter.addFrag(emojiFragment, "Emoji");
            emojiFragment.setEmojiListener(this);
        }
        PhotoFragment photoFragment = new PhotoFragment();
        viewPagerAdapter.addFrag(photoFragment, "Photo");
        photoFragment.setPhotoListener(this);
        TuneFragment tuneFragment = new TuneFragment();
        viewPagerAdapter.addFrag(tuneFragment, "Tunes");
        tuneFragment.setTuneFragmentListener(this);
        viewPager.setAdapter(viewPagerAdapter);
        Log.d("@@", "setupViewPagerImageTools " + Objects.requireNonNull(viewPager.getAdapter()).getCount());
    }

    @SuppressLint("ResourceType")
    private void getData() {
        SystemClock.currentThreadTimeMillis();
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            try {
                Bitmap mainBitmap = BitmapProcess.handleSamplingAndRotationBitmap(this, data);
                Glide.with(this).load(mainBitmap).into(this.imgPhotoEditor.getSource());
                this.mPhotoEditor.addText(getString(R.string.double_tap), ContextCompat.getColor(TextArtActivity.this, 17170443));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int intExtra = intent.getIntExtra("SampleBackground", 0);
        if (intExtra != 0) {
            Glide.with(this).load(intExtra).into(this.imgPhotoEditor.getSource());
            this.mPhotoEditor.addText(getString(R.string.double_tap), ContextCompat.getColor(TextArtActivity.this, 17170443));
        }

    }


    private void addControls() {
        this.btnUndo = findViewById(R.id.btnUndo);
        ImageView btnBack = findViewById(R.id.btnBack);
        this.btnRedo = findViewById(R.id.btnRedo);
        this.btnSave = findViewById(R.id.btnSave);
        ImageView btnBackTextTools = findViewById(R.id.btnBackTextTools);
        ImageView btnAddText = findViewById(R.id.btnAddText_toolbar);
        this.rlTextTool = findViewById(R.id.rl_text_tool);
        this.rlMainTool = findViewById(R.id.rl_main_tool);
        this.rlPhotoTools = findViewById(R.id.rl_photo_tool);
        this.rlColorPhoto = findViewById(R.id.rl_color_photo);

        this.imgPhotoEditor = findViewById(R.id.imgPhotoEditor);
        this.viewPagerTextTools = findViewById(R.id.viewpagerTextTools);
        this.tabLayoutTextTools = findViewById(R.id.tablayoutTextTools);
        this.viewPagerImageTools = findViewById(R.id.viewpagerImageTools);
        this.tablayoutImageTools = findViewById(R.id.tablayoutImageTools);
        this.progressBarLoading = findViewById(R.id.progress_circular_loading);
        this.mChristmasFragment = new ChristmasFragment();
        this.mChristmasFragment.setOverplayListener(this);
        this.mEmoticonsFragment = new EmoticonsFragment();
        this.mEmoticonsFragment.setOverplayListener(this);
        this.mFitnessFragment = new FitnessFragment();
        this.mFitnessFragment.setOverplayListener(this);
        this.mFoodFragment = new FoodFragment();
        this.mFoodFragment.setOverplayListener(this);
        this.mGeometryFragment = new GeometryFragment();
        this.mGeometryFragment.setOverplayListener(this);
        this.mHalloweenFragment = new HalloweenFragment();
        this.mHalloweenFragment.setOverplayListener(this);
        this.mLoveFragment = new LoveFragment();
        this.mLoveFragment.setOverplayListener(this);
        this.mMotivationFragment = new MotivationFragment();
        this.mMotivationFragment.setOverplayListener(this);
        this.mNativeFragment = new NativeFragment();
        this.mNativeFragment.setOverplayListener(this);
        this.mPhrasesFragment = new PhrasesFragment();
        this.mPhrasesFragment.setOverplayListener(this);
        this.mSayingFragment = new SayingsFragment();
        this.mSayingFragment.setOverplayListener(this);
        this.mSummerFragment = new SummerFragment();
        this.mSummerFragment.setOverplayListener(this);
        this.mTravelFragment = new TravelFragment();
        this.mTravelFragment.setOverplayListener(this);
        this.mWinterFragment = new WinterFragment();
        this.mWinterFragment.setOverplayListener(this);
        btnBack.setOnClickListener(this);
        btnBackTextTools.setOnClickListener(this);
        this.btnUndo.setOnClickListener(this);
        this.btnRedo.setOnClickListener(this);
        this.btnSave.setOnClickListener(this);
        this.imgPhotoEditor.setOnClickListener(this);
        btnAddText.setOnClickListener(this);
        this.recyclerPhotoColor = findViewById(R.id.recycler_color_photo);
        this.sbTranparencyPhoto = findViewById(R.id.seekbar_photo_transparency);
        this.sbRotatePhoto = findViewById(R.id.seekbar_rotate);
        this.btnColorPicker = findViewById(R.id.btn_picker_color_photo);
    }

    @SuppressLint("ResourceType")
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddText_toolbar:
                if (this.numberAddedView < 6) {
                    this.mPhotoEditor.addText(getString(R.string.double_tap), ContextCompat.getColor(TextArtActivity.this, 17170443));
                } else {
                    Toast.makeText(this, R.string.max_item, Toast.LENGTH_SHORT).show();
                }
                return;
            case R.id.btnBack:
                discardDialog = new ExitDialog(this);
                Objects.requireNonNull(discardDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
                try {
                    discardDialog.show();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case R.id.btnBackTextTools:
                if (this.countMain == 0) {
                    ViewAnimation.animationView(this.rlMainTool);
                    this.rlTextTool.setVisibility(View.GONE);
                    this.rlPhotoTools.setVisibility(View.GONE);
                    Objects.requireNonNull(this.tablayoutImageTools.getTabAt(1)).select();
                    this.countText = 0;
                    this.countMain++;
                    this.countOverplay = 0;
                    this.countPhoto = 0;

                }
                return;

            case R.id.btnSave:
                saveTextArt();
                return;
            case R.id.btnRedo:
                this.mPhotoEditor.redo();
                return;
            case R.id.btnUndo:
                this.mPhotoEditor.undo();
                return;
            case R.id.imgPhotoEditor:
                if (this.countMain == 0) {
                    this.mPhotoEditor.clearHelperBox();
                    ViewAnimation.animationView(this.rlMainTool);
                    this.rlTextTool.setVisibility(View.GONE);
                    this.rlPhotoTools.setVisibility(View.GONE);
                    if (this.currentTabTool == 0) {
                        this.currentTabTool = 1;
                    }
                    Objects.requireNonNull(this.tablayoutImageTools.getTabAt(this.currentTabTool)).select();
                    this.countText = 0;
                    this.countMain++;
                    this.countPhoto = 0;
                    this.countOverplay = 0;

                }
                return;

            default:
        }
    }

    private void saveTextArt() {
        LoadingDialog dialog = new LoadingDialog(this);
        dialog.show();

        final File outputMediaFile = Utils.getOutputMediaFile();
        try {
            if (outputMediaFile != null) {
                Boolean b = outputMediaFile.createNewFile();
                Log.d("b", b + "");
            }
            SaveSettings build = new SaveSettings.Builder().setClearViewsEnabled(false).setTransparencyEnabled(true).build();
            if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && outputMediaFile != null) {

                this.mPhotoEditor.saveAsFile(outputMediaFile.getAbsolutePath(), build, new PhotoEditor.OnSaveListener() {
                    public void onFailure(@NonNull Exception exc) {
                        dialog.dismiss();
                    }

                    public void onSuccess(@NonNull String str) {
                        dialog.dismiss();
                        Uri fromFile = Uri.fromFile(new File(str));
                        Intent intent = new Intent(TextArtActivity.this, ArtPreviewActivity.class);
                        intent.setData(fromFile);
                        MediaScannerConnection.scanFile(TextArtActivity.this, new String[]{outputMediaFile.toString()}, null, (str1, uri) -> Log.i("ExternalStorage", "Scanned" + str1 + ":"));

//                        TextArtActivity.this.startActivity(intent);

                        AdManager.adCounter =  AdManager.adDisplayCounter;
                        if (AdManager.isloadFbAd){
                            AdManager.ironShowInterstitial(TextArtActivity.this, intent, 0);
                        }else {
                            AdManager.showInterAd(TextArtActivity.this, intent, 0);
                        }
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
            dialog.dismiss();
        }
    }


    public void onEditTextChangeListener(View view, String str, int i) {
        showTextEditorFragment(str);
    }

    private void showTextEditorFragment(String str) {
        TextEditorDialog textEditorDialog = new TextEditorDialog(this, str);
        textEditorDialog.setOnDismissListener(dialogInterface -> {
            View currentFocus = TextArtActivity.this.getCurrentFocus();
            if (currentFocus != null) {
                ((InputMethodManager) TextArtActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
            if (TextArtActivity.this.rlMainTool != null && TextArtActivity.this.rlMainTool.getVisibility() == View.VISIBLE) {
                Objects.requireNonNull(TextArtActivity.this.tablayoutImageTools.getTabAt(1)).select();
            }
        });
        textEditorDialog.setTextListener(this);
        textEditorDialog.show();
    }

    public void onAdded(StrokeTextView strokeTextView, RoundFrameLayout roundFrameLayout) {
        this.textViewMain = strokeTextView;
        this.border = roundFrameLayout;
    }

    public void onClickGetEditTextChangeListener(StrokeTextView
                                                         strokeTextView, RoundFrameLayout roundFrameLayout) {
        this.textViewMain = strokeTextView;
        this.border = roundFrameLayout;
        if (this.countText == 0) {
            ViewAnimation.animationView(this.rlTextTool);
            this.rlMainTool.setVisibility(View.GONE);
            this.rlPhotoTools.setVisibility(View.GONE);
            this.countMain = 0;
            this.countText++;
            this.countPhoto = 0;
            this.countOverplay = 0;
        }
    }

    public void onClickGetImageViewListener(ImageView imageView, View view) {
        this.imageViewMain = imageView;
        this.viewMain = view;
        if (this.countPhoto == 0) {
            ViewAnimation.animationView(this.rlPhotoTools);
            this.rlMainTool.setVisibility(View.GONE);
            this.rlTextTool.setVisibility(View.GONE);
            this.rlColorPhoto.setVisibility(View.GONE);
            this.countPhoto++;
            this.countMain = 0;
            this.countText = 0;
            this.countOverplay = 0;
        }
    }

    public void onClickGetGraphicViewListener(ImageView imageView, View view, View view2) {
        this.imageViewMain = imageView;
        this.viewMain = view;
        if (this.countOverplay == 0) {
            ViewAnimation.animationView(this.rlPhotoTools);
            this.rlMainTool.setVisibility(View.GONE);
            this.rlTextTool.setVisibility(View.GONE);
            this.rlColorPhoto.setVisibility(View.VISIBLE);
            this.countPhoto = 0;
            this.countOverplay++;
            this.countMain = 0;
            this.countText = 0;
        }
    }

    public void onClickGetBitmaoOverlay(Bitmap bitmap) {
        this.resourceGraphic = bitmap;
    }

    public void onAddViewListener(ViewType viewType, int i) {
        this.numberAddedView = i;
    }

    public void onRemoveViewListener(int i) {
        this.numberAddedView = i;
    }

    public void onRemoveViewListener(ViewType viewType, int i) {
        ViewAnimation.animationView(this.rlMainTool);
        this.rlColorPhoto.setVisibility(View.GONE);
        this.rlTextTool.setVisibility(View.GONE);
        this.rlPhotoTools.setVisibility(View.GONE);
        if (this.currentTabTool == 0) {
            this.currentTabTool = 1;
        }
        Objects.requireNonNull(this.tablayoutImageTools.getTabAt(this.currentTabTool)).select();
        this.countText = 0;
        this.countMain++;
        this.countOverplay = 0;
        this.countPhoto = 0;
    }

    @Override
    public void onBackPressed() {
        if ((getSupportFragmentManager().findFragmentByTag("QUOTES")) != null) {
            getSupportFragmentManager().popBackStack("EDIT", 0);
        } else if ((getSupportFragmentManager().findFragmentByTag("EDIT")) != null) {
            this.fragmentManager.popBackStack(null, 1);
        } else {
            ExitDialog dialog = new ExitDialog(this);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(0));
            dialog.show();
        }
    }

    @SuppressLint("WrongConstant")
    public void onTextAlign(int i) {
        switch (i) {
            case 1:
                this.textViewMain.setGravity(3);
                return;
            case 2:
                this.textViewMain.setGravity(17);
                return;
            case 3:
                this.textViewMain.setGravity(5);
                StrokeTextView strokeTextView = this.textViewMain;
                strokeTextView.setTypeface(strokeTextView.getTypeface(), 2);
                return;
            default:
        }
    }

    @SuppressLint("WrongConstant")
    public void onTextStyle(int i) {
        switch (i) {
            case 1:
                StrokeTextView strokeTextView = this.textViewMain;
                strokeTextView.setTypeface(strokeTextView.getTypeface(), 3);
                this.styleText = i;
                return;
            case 2:
                StrokeTextView strokeTextView2 = this.textViewMain;
                strokeTextView2.setTypeface(strokeTextView2.getTypeface(), 1);
                this.styleText = i;
                return;
            case 3:
                StrokeTextView strokeTextView3 = this.textViewMain;
                strokeTextView3.setTypeface(strokeTextView3.getTypeface(), 2);
                this.styleText = i;
                return;
            case 4:
                StrokeTextView strokeTextView4 = this.textViewMain;
                strokeTextView4.setTypeface(Typeface.create(strokeTextView4.getTypeface(), 0));
                this.styleText = i;
                return;
            case 5:
                this.textViewMain.setAllCaps(true);
                return;
            case 6:
                this.textViewMain.setAllCaps(false);
                return;
            default:
        }
    }

    public void onTextSize(int i) {
        this.textViewMain.setTextSize((float) i);
        Log.d("TEXTTTTT", "onTextSize " + i);
    }

    public void onTextPadding(int i) {
        this.border.setPadding(i, i, i, i);
    }

    @SuppressLint("WrongConstant")
    public void onFontSelected(String str) {
        AssetManager assets = getAssets();
        Typeface typeface = Typeface.createFromAsset(assets, "font/" + str);
        switch (this.styleText) {
            case 1:
                this.textViewMain.setTypeface(typeface, 3);
                return;
            case 2:
                this.textViewMain.setTypeface(typeface, 1);
                return;
            case 3:
                this.textViewMain.setTypeface(typeface, 2);
                return;
            case 4:
            default:
                this.textViewMain.setTypeface(typeface, 0);
        }
    }

    public void onHightLightColorSelected(int i) {
        this.colorBackground = i;
        String format = String.format("%06X", this.colorBackground & 16777215);
        RoundViewDelegate delegate = this.border.getDelegate();
        delegate.setBackgroundColor(Color.parseColor("#" + this.opticalBackground + format));
    }

    public void onHightLightColorOpacityChangeListerner(String str) {
        String format = String.format("%06X", this.colorBackground & 16777215);
        this.opticalBackground = str;
        RoundViewDelegate delegate = this.border.getDelegate();
        delegate.setBackgroundColor(Color.parseColor("#" + this.opticalBackground + format));
    }

    public void onHighLightRadius(int i) {
        this.border.getDelegate().setCornerRadius(i);
    }

    public void onColorSelected(int i) {
        this.colorTextShadow = i;
        this.textViewMain.getPaint().setShader(null);
        this.textViewMain.setTextColor(i);
        StrokeTextView strokeTextView = this.textViewMain;
        strokeTextView.setTextColor(strokeTextView.getTextColors().withAlpha(this.opticalText));
    }

    public void onColorOpacityChangeListerner(int i) {
        this.opticalText = i;
        StrokeTextView strokeTextView = this.textViewMain;
        strokeTextView.setTextColor(strokeTextView.getTextColors().withAlpha(i));
    }

    public void onLineHeight(int i) {
        setLineGeight(this.textViewMain, i);
    }

    private void setLineGeight(TextView textView, int i) {
        textView.setLineSpacing((float) (dpToPixel((float) i) - textView.getPaint().getFontMetricsInt(null)), 1.0f);
    }

    public int dpToPixel(float f) {
        return (int) (f * (((float) getResources().getDisplayMetrics().densityDpi) / 160.0f));
    }

    @RequiresApi(api = 21)
    @SuppressLint({"NewApi"})
    public void onSpacingLetter(float f) {
        this.textViewMain.setLetterSpacing(f);
    }

    public void onStrokeColorSelected(int i) {
        this.textViewMain.setStrokeColor(i);
    }

    public void onStrokeWidthChangeListener(int i) {

        this.textViewMain.setStrokeWidth(i);
    }

    public void onDyShadowChangeListener(int i) {
        this.rY = i;
        this.textViewMain.setStrokeWidth(0);
        float f = (float) i;
        this.textViewMain.setShadowLayer((float) this.rRadius, f, f, this.colorTextShadow);
    }

    public void onRadiusChangeListener(int i) {
        this.rRadius = i;
        this.textViewMain.setStrokeWidth(0);
        int i2 = this.rY;
        this.textViewMain.setShadowLayer((float) this.rRadius, (float) i2, (float) i2, this.colorTextShadow);
    }

    public void onShadowColorSelected(int i) {
        this.colorTextShadow = i;
        this.textViewMain.setStrokeWidth(0);
        int i2 = this.rY;
        this.textViewMain.setShadowLayer((float) this.rRadius, (float) i2, (float) i2, this.colorTextShadow);
    }

    public void onEmojiClick(String str) {
        if (this.numberAddedView < 6) {
            this.mPhotoEditor.addEmoji(str);
        } else {
            Toast.makeText(this, R.string.max_item, Toast.LENGTH_SHORT).show();
        }
    }

    public void onPhotoClick(String str) {
        if (this.numberAddedView < 6) {
            this.mPhotoEditor.addImage(BitmapProcess.getBitmapFromLocalPath(str, 4));
            return;
        }
        Toast.makeText(this, R.string.max_item, Toast.LENGTH_SHORT).show();
    }

    public void onStickerFragmentClick(Bitmap bitmap) {
        if (this.numberAddedView < 6) {
            this.mPhotoEditor.addImage(bitmap);
        } else {
            Toast.makeText(this, R.string.max_item, Toast.LENGTH_SHORT).show();
        }
    }

    public void onOverplayClick(Bitmap bitmap) {
        int i;
        int i2;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width > height) {
            i2 = (int) ((300.0f / ((float) width)) * ((float) height));
            i = (int) 300.0f;
        } else {
            i = (int) ((300.0f / ((float) height)) * ((float) width));
            i2 = (int) 300.0f;
        }
        if (this.numberAddedView < 6) {
            this.mPhotoEditor.addImage(Bitmap.createScaledBitmap(bitmap, i, i2, false));
        } else {
            Toast.makeText(this, R.string.max_item, Toast.LENGTH_SHORT).show();
        }
    }

    public void onPhrases() {
        if (!this.mPhrasesFragment.isAdded()) {
            this.mPhrasesFragment.show(getSupportFragmentManager(), this.mPhrasesFragment.getTag());
        }
    }

    public void onFood() {
        if (!this.mFoodFragment.isAdded()) {
            this.mFoodFragment.show(getSupportFragmentManager(), this.mFoodFragment.getTag());
        }
    }

    public void onLove() {
        if (!this.mLoveFragment.isAdded()) {
            this.mLoveFragment.show(getSupportFragmentManager(), this.mLoveFragment.getTag());
        }
    }

    public void onChristmas() {
        if (!this.mChristmasFragment.isAdded()) {
            this.mChristmasFragment.show(getSupportFragmentManager(), this.mChristmasFragment.getTag());
        }
    }

    public void onSayings() {
        if (!this.mSayingFragment.isAdded()) {
            this.mSayingFragment.show(getSupportFragmentManager(), this.mSayingFragment.getTag());
        }
    }

    public void onNative() {
        if (!this.mNativeFragment.isAdded()) {
            this.mNativeFragment.show(getSupportFragmentManager(), this.mNativeFragment.getTag());
        }
    }

    public void onSummer() {
        if (!this.mSummerFragment.isAdded()) {
            this.mSummerFragment.show(getSupportFragmentManager(), this.mSummerFragment.getTag());
        }
    }

    public void onWinter() {
        if (!this.mWinterFragment.isAdded()) {
            this.mWinterFragment.show(getSupportFragmentManager(), this.mWinterFragment.getTag());
        }
    }

    public void onTravel() {
        if (!this.mTravelFragment.isAdded()) {
            this.mTravelFragment.show(getSupportFragmentManager(), this.mTravelFragment.getTag());
        }
    }

    public void onEmoticons() {
        if (!this.mEmoticonsFragment.isAdded()) {
            this.mEmoticonsFragment.show(getSupportFragmentManager(), this.mEmoticonsFragment.getTag());
        }
    }

    public void onMotivation() {
        if (!this.mMotivationFragment.isAdded()) {
            this.mMotivationFragment.show(getSupportFragmentManager(), this.mMotivationFragment.getTag());
        }
    }

    public void onFitness() {
        if (!this.mFitnessFragment.isAdded()) {
            this.mFitnessFragment.show(getSupportFragmentManager(), this.mFitnessFragment.getTag());
        }
    }

    public void onGeometry() {
        if (!this.mGeometryFragment.isAdded()) {
            this.mGeometryFragment.show(getSupportFragmentManager(), this.mGeometryFragment.getTag());
        }
    }

    public void onHalloween() {
        if (!this.mHalloweenFragment.isAdded()) {
            this.mHalloweenFragment.show(getSupportFragmentManager(), this.mHalloweenFragment.getTag());
        }
    }

    public void onBrightnessChosse(int i) {
        this.brightnessFinal = i;
        this.imgPhotoEditor.getSource().setColorFilter(ColorFilterGenerator.adjustColor(this.brightnessFinal, this.saturationFinal, this.constrantFinal, this.hueFinal));
    }

    public void onConstrastChosse(int i) {
        this.constrantFinal = i;
        this.imgPhotoEditor.getSource().setColorFilter(ColorFilterGenerator.adjustColor(this.brightnessFinal, this.saturationFinal, this.constrantFinal, this.hueFinal));
    }

    public void onHueChosee(int i) {
        this.hueFinal = i;
        this.imgPhotoEditor.getSource().setColorFilter(ColorFilterGenerator.adjustColor(this.brightnessFinal, this.saturationFinal, this.constrantFinal, this.hueFinal));
    }

    public void onSaturationChosse(int i) {
        this.saturationFinal = i;
        this.imgPhotoEditor.getSource().setColorFilter(ColorFilterGenerator.adjustColor(this.brightnessFinal, this.saturationFinal, this.constrantFinal, this.hueFinal));
    }

    public void onText(String str) {
        this.textViewMain.setText(str);
        if (this.countText == 0) {
            ViewAnimation.animationView(this.rlTextTool);
            this.rlMainTool.setVisibility(View.GONE);
            this.rlPhotoTools.setVisibility(View.GONE);
            this.countMain = 0;
            this.countText++;
            this.countPhoto = 0;
            this.countOverplay = 0;
        }
    }

    @Override
    protected void onDestroy() {
        if (discardDialog != null) {
            discardDialog.dismiss();
        }
        super.onDestroy();
    }
}
