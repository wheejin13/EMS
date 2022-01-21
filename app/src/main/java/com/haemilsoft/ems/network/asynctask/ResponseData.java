package com.haemilsoft.ems.network.asynctask;

import com.haemilsoft.ems.utils.LOG;

import org.json.JSONException;
import org.json.JSONObject;

public class ResponseData {
    private int code;
    private String message = "";
    private String data = "";

    public ResponseData(ResultCode code) {
        setCode(code);
    }

    public ResponseData(String data) {
        try {
            if (data != null) {
                LOG.printJsonMsg(data);

                JSONObject obj = new JSONObject(data);

                if (obj.has("code"))
                    setCode(obj.getInt("code"));
                if (obj.has("message"))
                    setMessage(obj.getString("message"));
                if (obj.has("data"))
                    setData(obj.getString("data"));
            }
        } catch (JSONException ex) {
            LOG.e(ex.toString());
        }
    }

    public void setCode(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getData() {
        return this.data;
    }
}
