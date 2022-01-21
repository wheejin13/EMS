package com.haemilsoft.ems.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.haemilsoft.ems.R;
import com.haemilsoft.ems.utils.SharedPref;
import com.haemilsoft.ems.view.dialog.LoadingProgressDialog;

public abstract class BaseActivity extends AppCompatActivity {

    protected LoadingProgressDialog mProgressDialog;
    protected SharedPref mPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mProgressDialog = new LoadingProgressDialog(this);
        mPref = SharedPref.getInstance(BaseActivity.this);
    }

    /*
     * view, data init.
     */
    protected abstract void onInit();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mProgressDialog.dismiss();
        mProgressDialog = null;
    }

    protected void startActivity(Context context, Class<?> clz)
    {
        startActivity(new Intent(context, clz));
        overridePendingTransition(R.anim.move_down_in_activity, R.anim.move_top_out_activity);
    }
}
