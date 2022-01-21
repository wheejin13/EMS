package com.haemilsoft.ems.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import com.haemilsoft.ems.R;

public class LoadingProgressDialog extends Dialog {

    public LoadingProgressDialog(Context context) {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_loading_progress);

        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.setCancelable(false);

        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.dimAmount = .3f;
        this.getWindow().setAttributes(layoutParams);
    }
}
