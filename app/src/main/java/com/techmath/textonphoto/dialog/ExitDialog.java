package com.techmath.textonphoto.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.techmath.textonphoto.R;

public class ExitDialog extends Dialog {
    TextView exit;
    TextView stay;
    Activity context;

    public ExitDialog(@NonNull Activity activity) {
        super(activity);
        this.context = activity;
        setContentView(R.layout.exit_dialog);
        addControls();
        addEvents();
    }

    private void addEvents() {
        this.exit.setOnClickListener(view -> ExitDialog.this.context.finish());
        this.stay.setOnClickListener(view -> ExitDialog.this.dismiss());
    }

    private void addControls() {
        this.exit = findViewById(R.id.tv_discard);
        this.stay = findViewById(R.id.tv_keep);
        setCanceledOnTouchOutside(true);
    }
}
