package com.haemilsoft.ems.utils;

import android.app.Activity;
import android.widget.Toast;

public class IToast {


    public static void show(Activity act, String msg) {
        Toast.makeText(act, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Activity act, String msg) {
        Toast.makeText(act, msg, Toast.LENGTH_LONG).show();
    }
}
