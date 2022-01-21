package com.haemilsoft.ems.network;

import com.haemilsoft.ems.constant.CONFIG;
import com.haemilsoft.ems.network.asynctask.HttpAsyncTask;
import com.haemilsoft.ems.network.asynctask.HttpRequestListener;

import org.json.JSONObject;

public class HttpRequestManager {

    private static HttpRequestManager manager = null;
    private static String address = CONFIG.DEVELOP_MODE ? NetworkUtil.DEVELOP_DOMAIN : NetworkUtil.AUTH_DOMAIN;

    public static HttpRequestManager inst() {
        if (manager == null) {
            manager = new HttpRequestManager();
        }

        return manager;
    }

    public void login(String id, String password, HttpRequestListener httpRequestListener)
    {
        try {
            JSONObject header = new JSONObject();
            header.put("Content-Type", "application/json");

            JSONObject body = new JSONObject();
            body.put("id", id);
            body.put("password", password);

            HttpAsyncTask httpAsyncTask = new HttpAsyncTask("POST", header, body, httpRequestListener);
            httpAsyncTask.execute(address + "/api/login");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setPushToken(String com_cd, String phone_num,
                             String phone_device_id, String push_token,
                             HttpRequestListener httpRequestListener) {
        try {
            JSONObject header = new JSONObject();
            header.put("Content-Type", "application/json");

            JSONObject body = new JSONObject();
            body.put("com_cd", com_cd);
            body.put("phone_num", phone_num);
            body.put("phone_device_id", phone_device_id);
            body.put("push_token", push_token);

            HttpAsyncTask httpAsyncTask = new HttpAsyncTask("POST", header, body, httpRequestListener);
            httpAsyncTask.execute(address + "/api/push");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
