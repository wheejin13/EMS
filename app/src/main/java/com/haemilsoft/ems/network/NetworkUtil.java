package com.haemilsoft.ems.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    public static final int HTTP_CONNECTION_TIMEOUT = 3000;
    public static final int HTTP_READ_TIMEOUT = 5000; // 5초

    public static final String DEVELOP_DOMAIN = "http://192.168.0.111:8080";
    public static final String AUTH_DOMAIN = "http://192.168.0.111:8080";

    public static final boolean NETWORK_CONNECTED = true;
    public static final boolean NETWORK_DISCONNECTED = false;

    public static boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            if(ConnectivityManager.TYPE_WIFI == activeNetwork.getType() ||
                    ConnectivityManager.TYPE_MOBILE == activeNetwork.getType())
                return NETWORK_CONNECTED;
            else
                return NETWORK_DISCONNECTED;
        } else {
            return NETWORK_DISCONNECTED;
        }
    }
}
