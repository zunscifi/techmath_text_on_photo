package com.techmath.textonphoto.unit;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;


import android.net.Uri;
import android.widget.ImageView;
import androidx.exifinterface.media.ExifInterface;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class BitmapProcess {
    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri uri) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream openInputStream = context.getContentResolver().openInputStream(uri);
        BitmapFactory.decodeStream(openInputStream, null, options);
        openInputStream.close();
        options.inSampleSize = calculateInSampleSize(options, 1024, 1024);
        options.inJustDecodeBounds = false;
        return rotateImageIfRequired(BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, options), uri);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int i, int i2) {
        int i3 = options.outHeight;
        int i4 = options.outWidth;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            int i6 = i3 / 2;
            int i7 = i4 / 2;
            while (i6 / i5 >= i2 && i7 / i5 >= i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    private static Bitmap rotateImageIfRequired(Bitmap bitmap, Uri uri) throws IOException {
        int attributeInt = new ExifInterface(Objects.requireNonNull(uri.getPath())).getAttributeInt("Orientation", 1);
        if (attributeInt == 3) {
            return rotateImage(bitmap, 180);
        }
        if (attributeInt == 6) {
            return rotateImage(bitmap, 90);
        }
        if (attributeInt != 8) {
            return bitmap;
        }
        return rotateImage(bitmap, 270);
    }

    private static Bitmap rotateImage(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        return createBitmap;
    }


    public static Bitmap getBitmapFromLocalPath(String str, int i) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = i;
            return BitmapFactory.decodeFile(str, options);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void changeBitmapColor(Bitmap bitmap, ImageView imageView, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() - 1, bitmap.getHeight() - 1);
        Paint paint = new Paint();
        paint.setColorFilter(new LightingColorFilter(i, 1));
        imageView.setImageBitmap(createBitmap);
        new Canvas(createBitmap).drawBitmap(createBitmap, 0.0f, 0.0f, paint);
    }
}
