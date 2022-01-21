package com.haemilsoft.ems.view.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;

import com.haemilsoft.ems.R;

/**
 * Created by ssw on 2016-11-14.
 */

public class BasicAlertDialog {

    public static void showBasicDialog(final Activity activity, final String msg) {
        if (activity.isFinishing())	return;

        AlertDialog.Builder abAlert;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            abAlert = new AlertDialog.Builder(activity);
        } else {
            abAlert = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_LIGHT);
        }

        abAlert.setMessage(msg)
                .setCancelable(false)
                .setNegativeButton(activity.getString(R.string.common_str_confirm), new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

        abAlert.create().show();
    }

    public static void showBasicDialogWithFinish(final Activity activity, final String msg) {
        if (activity.isFinishing())	return;

        AlertDialog.Builder abAlert;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            abAlert = new AlertDialog.Builder(activity);
        } else {
            abAlert = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_LIGHT);
        }

        abAlert.setMessage(msg)
                .setCancelable(false)
                .setNegativeButton(activity.getString(R.string.common_str_confirm), new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        activity.finish();
                        activity.overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_right_out_activity);
                    }
                });

        abAlert.create().show();
    }
}
