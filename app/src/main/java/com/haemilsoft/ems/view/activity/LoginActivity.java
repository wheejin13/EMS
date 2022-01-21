package com.haemilsoft.ems.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.haemilsoft.ems.R;
import com.haemilsoft.ems.R.string;
import com.haemilsoft.ems.network.HttpRequestManager;
import com.haemilsoft.ems.network.SessionData;
import com.haemilsoft.ems.network.asynctask.HttpRequestListener;
import com.haemilsoft.ems.network.asynctask.ResponseData;
import com.haemilsoft.ems.network.asynctask.ResultCode;
import com.haemilsoft.ems.utils.IToast;
import com.haemilsoft.ems.utils.PermissionCheckHelper;
import com.haemilsoft.ems.utils.SharedPref;
import com.haemilsoft.ems.utils.StringUtils;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginActivity extends BaseActivity  {

    private EditText mEtId, mEtPwd;
    private CheckBox mCbSaveId;
    private Button mBtnLogin;

    private boolean b_CHECKED_SAVE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        onInit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAppPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // Permission 설정 요청 후 그 결과값에 대한 listener.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 모든 권한에 대해 승인하였을 경우.
        if (requestCode == PermissionCheckHelper.PERMISSION_REQUEST_CODE && PermissionCheckHelper.hasAllPermissionsGranted(grantResults)) {
            registerPushToken();
        }
        // 권한이 하나라도 거부당했을 경우.
        else {
            AtomicBoolean checkIfNeededToShowRequestPermissionRationale = new AtomicBoolean(false);

            for (String permission : permissions) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(permission)) {
                        checkIfNeededToShowRequestPermissionRationale.set(true);
                        break;
                    }
                }
            }

            if (!checkIfNeededToShowRequestPermissionRationale.get()) {
                IToast.show(LoginActivity.this, "GO TO SETTING AND CHECK PERMISSION TO USE THIS APPLICATION.");
            }
        }
    }

    private void checkAppPermission() {
        if (PermissionCheckHelper.lackOfBasicPermissions(getApplicationContext())) {
            PermissionCheckHelper.requestPermissions(this);
        } else {
            registerPushToken();
        }
    }

    @Override
    protected void onInit() {
        mEtId = findViewById(R.id.et_id);
        mEtPwd = findViewById(R.id.et_pwd);
        mCbSaveId = findViewById(R.id.cb_save_id);
        mBtnLogin = findViewById(R.id.btn_login);

        b_CHECKED_SAVE_ID = SharedPref.getInstance(LoginActivity.this).getBoolean(SharedPref.PREF_CHECK_SAVE_ID, false);
        if (b_CHECKED_SAVE_ID) {
            mCbSaveId.setChecked(true);
            mEtId.setText(mPref.getString(SharedPref.PREF_USER_ID, ""));
            mEtPwd.setText(mPref.getString(SharedPref.PREF_USER_PWD, ""));
        }

        mCbSaveId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                b_CHECKED_SAVE_ID = isChecked;
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorize();
            }
        });
    }

    private void authorize() {
        if (isValid()){
            mProgressDialog.show();
            HttpRequestManager.inst().login(getId(), StringUtils.getBase64encode(getPwd()), new HttpRequestListener() {
                @Override
                public void httpResponse(ResponseData responseData) {
                    mProgressDialog.dismiss();
                    if (responseData.getCode() == ResultCode.SUCCESS.code()) {
                        SessionData.getInstance().set(responseData.getData());
                        if (b_CHECKED_SAVE_ID)  mPref.saveLoginInfo(getId(), getPwd());
                        startActivity(LoginActivity.this, MainActivity.class);
                    } else
                        IToast.show(LoginActivity.this, responseData.getMessage());
                }
            });
        }
    }

    // TODO : PUSH 키 등록 순서를 정해야 함.
    private void registerPushToken() {
        mProgressDialog.show();
        HttpRequestManager.inst().setPushToken("", "", "", "", new HttpRequestListener() {
            @Override
            public void httpResponse(ResponseData responseData) {
                mProgressDialog.dismiss();
                if (responseData.getCode() == ResultCode.SUCCESS.code()) {
                    // TODO something.
                } else
                    IToast.show(LoginActivity.this, responseData.getMessage());
            }
        });
    }

    private String getId() {
        return mEtId.getText().toString();
    }

    private String getPwd() {
        return mEtPwd.getText().toString();
    }

    // empty check
    private boolean isValid() {
        if (getId().isEmpty()) {
            IToast.show(LoginActivity.this, getString(string.login_id_hint));
            return false;
        } else if (getPwd().isEmpty()) {
            IToast.show(LoginActivity.this, getString(string.login_pw_hint));
            return false;
        } else {
            return true;
        }
    }
}
