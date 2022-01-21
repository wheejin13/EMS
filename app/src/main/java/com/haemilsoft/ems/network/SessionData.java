package com.haemilsoft.ems.network;

import com.haemilsoft.ems.utils.LOG;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionData {

    private String idx;
    private String auth_code;
    private String usr_type_cd;
    private String id;
    private String password;
    private String name;
    private String phone_num;
    private String com_cd;
    private String site_id;

    private static SessionData INSTANCE;

    public static SessionData getInstance() {
        if (INSTANCE == null) {
            synchronized (SessionData.class) {
                if (INSTANCE == null)
                    INSTANCE = new SessionData();
            }
        }
        return INSTANCE;
    }

    private SessionData() {

    }

    public void set(Object obj) {
        if (obj instanceof String) {

            if (obj != null) {
                try {
                    JSONObject jsonObject = new JSONObject(obj.toString());

                    if (jsonObject.has("idx"))
                        this.idx = jsonObject.getString("idx");
                    if (jsonObject.has("id"))
                        this.idx = jsonObject.getString("id");
                    if (jsonObject.has("password"))
                        this.password = jsonObject.getString("password");
                    if (jsonObject.has("name"))
                        this.name = jsonObject.getString("name");
                    if (jsonObject.has("phone_num"))
                        this.phone_num = jsonObject.getString("phone_num");
                    if (jsonObject.has("com_cd"))
                        this.phone_num = jsonObject.getString("com_cd");
                    if (jsonObject.has("site_id"))
                        this.phone_num = jsonObject.getString("site_id");

                } catch (JSONException ex) {
                    LOG.e(ex.toString());
                }
            }
            else {
                LOG.e("session data is null");
            }
        }
        else {
            LOG.e("session data type is not string");
        }
    }
}
