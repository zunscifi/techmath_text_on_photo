package com.techmath.textonphoto.photoeditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;

import androidx.core.view.InputDeviceCompat;

import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class ImageFilterView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private static final String TAG = "ImageFilterView";
    private boolean isSaveImage = false;
    private PhotoFilter mCurrentEffect;
    private CustomEffect mCustomEffect;
    private Effect mEffect;
    private EffectContext mEffectContext;
    private int mImageHeight;
    private int mImageWidth;
    private boolean mInitialized = false;

    public OnSaveBitmap mOnSaveBitmap;
    private Bitmap mSourceBitmap;
    private final TextureRenderer mTexRenderer = new TextureRenderer();
    private final int[] mTextures = new int[2];

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
    }

    public ImageFilterView(Context context) {
        super(context);
        init();
    }

    public ImageFilterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(0);
        setFilterEffect(PhotoFilter.NONE);
    }


    public void setSourceBitmap(Bitmap bitmap) {
        this.mSourceBitmap = bitmap;
        this.mInitialized = false;
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        if (this.mTexRenderer != null) {
            this.mTexRenderer.updateViewSize(i, i2);
        }
    }

    public void onDrawFrame(GL10 gl10) {
        if (!this.mInitialized) {
            this.mEffectContext = EffectContext.createWithCurrentGlContext();
            this.mTexRenderer.init();
            loadTextures();
            this.mInitialized = true;
        }
        if (!(this.mCurrentEffect == PhotoFilter.NONE && this.mCustomEffect == null)) {
            initEffect();
            applyEffect();
        }
        renderResult();
        if (this.isSaveImage) {
            final Bitmap a = BitmapUtil.createBitmapFromGLSurface(this, gl10);
            Log.e(TAG, "onDrawFrame: " + a);
            this.isSaveImage = false;
            if (this.mOnSaveBitmap != null) {
                new Handler(Looper.getMainLooper()).post(() -> ImageFilterView.this.mOnSaveBitmap.onBitmapReady(a));
            }
        }
    }


    public void setFilterEffect(PhotoFilter photoFilter) {
        this.mCurrentEffect = photoFilter;
        this.mCustomEffect = null;
        requestRender();
    }


    public void setFilterEffect(CustomEffect customEffect) {
        this.mCustomEffect = customEffect;
        requestRender();
    }


    public void saveBitmap(OnSaveBitmap onSaveBitmap) {
        this.mOnSaveBitmap = onSaveBitmap;
        this.isSaveImage = true;
        requestRender();
    }

    private void loadTextures() {
        GLES20.glGenTextures(2, this.mTextures, 0);
        Bitmap bitmap = this.mSourceBitmap;
        if (bitmap != null) {
            this.mImageWidth = bitmap.getWidth();
            this.mImageHeight = this.mSourceBitmap.getHeight();
            this.mTexRenderer.updateTextureSize(this.mImageWidth, this.mImageHeight);
            GLES20.glBindTexture(3553, this.mTextures[0]);
            GLUtils.texImage2D(3553, 0, this.mSourceBitmap, 0);

            GLToolbox.initTexParams();
        }
    }

    private void initEffect() {
        EffectFactory factory = this.mEffectContext.getFactory();
        Effect effect = this.mEffect;
        if (effect != null) {
            effect.release();
        }
        CustomEffect customEffect = this.mCustomEffect;
        if (customEffect != null) {
            this.mEffect = factory.createEffect(customEffect.getEffectName());
            for (Map.Entry next : this.mCustomEffect.getParameters().entrySet()) {
                this.mEffect.setParameter((String) next.getKey(), next.getValue());
            }
            return;
        }
        switch (this.mCurrentEffect) {
            case AUTO_FIX:
                this.mEffect = factory.createEffect("android.media.effect.effects.AutoFixEffect");
                this.mEffect.setParameter("scale", 0.5f);
                return;
            case BLACK_WHITE:
                this.mEffect = factory.createEffect("android.media.effect.effects.BlackWhiteEffect");
                this.mEffect.setParameter("black", 0.1f);
                this.mEffect.setParameter("white", 0.7f);
                return;
            case BRIGHTNESS:
                this.mEffect = factory.createEffect("android.media.effect.effects.BrightnessEffect");
                this.mEffect.setParameter("brightness", 2.0f);
                return;
            case CONTRAST:
                this.mEffect = factory.createEffect("android.media.effect.effects.ContrastEffect");
                this.mEffect.setParameter("contrast", 1.4f);
                return;
            case CROSS_PROCESS:
                this.mEffect = factory.createEffect("android.media.effect.effects.CrossProcessEffect");
                return;
            case DOCUMENTARY:
                this.mEffect = factory.createEffect("android.media.effect.effects.DocumentaryEffect");
                return;
            case DUE_TONE:
                this.mEffect = factory.createEffect("android.media.effect.effects.DuotoneEffect");
                this.mEffect.setParameter("first_color", InputDeviceCompat.SOURCE_ANY);
                this.mEffect.setParameter("second_color", -12303292);
                return;
            case FILL_LIGHT:
                this.mEffect = factory.createEffect("android.media.effect.effects.FillLightEffect");
                this.mEffect.setParameter("strength", 0.8f);
                return;
            case FISH_EYE:
                this.mEffect = factory.createEffect("android.media.effect.effects.FisheyeEffect");
                this.mEffect.setParameter("scale", 0.5f);
                return;
            case FLIP_HORIZONTAL:
                this.mEffect = factory.createEffect("android.media.effect.effects.FlipEffect");
                this.mEffect.setParameter("horizontal", true);
                return;
            case FLIP_VERTICAL:
                this.mEffect = factory.createEffect("android.media.effect.effects.FlipEffect");
                this.mEffect.setParameter("vertical", true);
                return;
            case GRAIN:
                this.mEffect = factory.createEffect("android.media.effect.effects.GrainEffect");
                this.mEffect.setParameter("strength", 1.0f);
                return;
            case GRAY_SCALE:
                this.mEffect = factory.createEffect("android.media.effect.effects.GrayscaleEffect");
                return;
            case LOMISH:
                this.mEffect = factory.createEffect("android.media.effect.effects.LomoishEffect");
                return;
            case NEGATIVE:
                this.mEffect = factory.createEffect("android.media.effect.effects.NegativeEffect");
                return;
            case POSTERIZE:
                this.mEffect = factory.createEffect("android.media.effect.effects.PosterizeEffect");
                return;
            case ROTATE:
                this.mEffect = factory.createEffect("android.media.effect.effects.RotateEffect");
                this.mEffect.setParameter("angle", 180);
                return;
            case SATURATE:
                this.mEffect = factory.createEffect("android.media.effect.effects.SaturateEffect");
                this.mEffect.setParameter("scale", 0.5f);
                return;
            case SEPIA:
                this.mEffect = factory.createEffect("android.media.effect.effects.SepiaEffect");
                return;
            case SHARPEN:
                this.mEffect = factory.createEffect("android.media.effect.effects.SharpenEffect");
                return;
            case TEMPERATURE:
                this.mEffect = factory.createEffect("android.media.effect.effects.ColorTemperatureEffect");
                this.mEffect.setParameter("scale", 0.9f);
                return;
            case TINT:
                this.mEffect = factory.createEffect("android.media.effect.effects.TintEffect");
                this.mEffect.setParameter("tint", -65281);
                return;
            case VIGNETTE:
                this.mEffect = factory.createEffect("android.media.effect.effects.VignetteEffect");
                this.mEffect.setParameter("scale", 0.5f);
                return;
            default:
        }
    }

    private void applyEffect() {
        Effect effect = this.mEffect;
        int[] iArr = this.mTextures;
        effect.apply(iArr[0], this.mImageWidth, this.mImageHeight, iArr[1]);
    }

    private void renderResult() {
        if (this.mCurrentEffect == PhotoFilter.NONE && this.mCustomEffect == null) {
            this.mTexRenderer.renderTexture(this.mTextures[0]);
        } else {
            this.mTexRenderer.renderTexture(this.mTextures[1]);
        }
    }
}
