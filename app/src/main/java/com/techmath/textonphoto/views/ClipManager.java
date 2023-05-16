package com.techmath.textonphoto.views;

import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ClipManager {
    @NonNull
    Path createMask(int i, int i2);

    Paint getPaint();

    @Nullable
    Path getShadowConvexPath();

    boolean requiresBitmap();

    void setupClipLayout(int i, int i2);
}
