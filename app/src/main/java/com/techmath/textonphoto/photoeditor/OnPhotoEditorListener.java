package com.techmath.textonphoto.photoeditor;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public interface OnPhotoEditorListener {
    void onAddViewListener(ViewType viewType, int i);

    void onAdded(StrokeTextView strokeTextView, RoundFrameLayout roundFrameLayout);

    void onClickGetBitmaoOverlay(Bitmap bitmap);

    void onClickGetEditTextChangeListener(StrokeTextView strokeTextView, RoundFrameLayout roundFrameLayout);

    void onClickGetGraphicViewListener(ImageView imageView, View view, View view2);

    void onClickGetImageViewListener(ImageView imageView, View view);

    void onEditTextChangeListener(View view, String str, int i);


    void onRemoveViewListener(int i);

    void onRemoveViewListener(ViewType viewType, int i);

    void onStartViewChangeListener(ViewType viewType);

    void onStopViewChangeListener(ViewType viewType);
}
