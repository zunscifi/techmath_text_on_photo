package com.techmath.textonphoto.dialog;

import android.app.Activity;
import android.app.Dialog;

import androidx.annotation.NonNull;

import com.techmath.textonphoto.R;

public class LoadingDialog extends Dialog {

    Activity ac;

    public LoadingDialog(@NonNull Activity activity) {
        super(activity);
        this.ac = activity;
        setContentView(R.layout.loading_dialog);
        addControls();
    }

    private void addControls() {
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }
}
