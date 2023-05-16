package com.techmath.textonphoto.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;

public class SettingDialog {
    public static void showSettingDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GO SETTINGS", (dialogInterface, i) -> {
            dialogInterface.cancel();
            SettingDialog.openSettings(activity);
        });
        builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel());
        builder.show();
    }

    public static void openSettings(Activity activity) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", activity.getPackageName(),  null));
        activity.startActivityForResult(intent, 101);
    }
}
