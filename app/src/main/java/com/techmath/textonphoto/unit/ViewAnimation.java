package com.techmath.textonphoto.unit;

import android.animation.Animator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;


public class ViewAnimation {

    public static void animationView(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            int width = view.getWidth() / 2;
            int height = view.getHeight() / 2;
            Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(view, width, height, 0.0f, (float) Math.hypot( width,  height));
            view.setVisibility(View.VISIBLE);
            createCircularReveal.start();
            return;
        }
        view.setVisibility(View.VISIBLE);
    }
}
