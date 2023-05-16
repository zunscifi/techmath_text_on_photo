package com.techmath.textonphoto.views;

import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ClipPathManager implements ClipManager {


    protected final Path path = new Path();
    private ClipPathCreator createClipPath = null;
    private final Paint paint = new Paint(1);

    public interface ClipPathCreator {
        Path createClipPath(int i, int i2);

        boolean requiresBitmap();
    }

    public ClipPathManager() {
        this.paint.setColor(-16777216);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setAntiAlias(true);
        this.paint.setStrokeWidth(1.0f);
    }

    public Paint getPaint() {
        return this.paint;
    }

    public boolean requiresBitmap() {
        ClipPathCreator clipPathCreator = this.createClipPath;
        return clipPathCreator != null && clipPathCreator.requiresBitmap();
    }


    @Nullable

    public final Path getPath(int i, int i2) {
        ClipPathCreator clipPathCreator = this.createClipPath;
        if (clipPathCreator != null) {
            return clipPathCreator.createClipPath(i, i2);
        }
        return null;
    }

    public void setClipPathCreator(ClipPathCreator clipPathCreator) {
        this.createClipPath = clipPathCreator;
    }

    @NonNull
    public Path createMask(int i, int i2) {
        return this.path;
    }

    @Nullable
    public Path getShadowConvexPath() {
        return this.path;
    }

    public void setupClipLayout(int i, int i2) {
        this.path.reset();
        Path a = getPath(i, i2);
        if (a != null) {
            this.path.set(a);
        }
    }
}
