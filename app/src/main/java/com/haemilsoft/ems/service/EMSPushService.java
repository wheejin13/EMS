package com.haemilsoft.ems.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.haemilsoft.ems.utils.LOG;
import com.haemilsoft.ems.view.notification.EMSNotificationManager;

public class EMSPushService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        LOG.i("new token : " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage != null && remoteMessage.getData().size() > 0) {
            EMSNotificationManager.getInstance(getApplicationContext()).send(remoteMessage.getData());
        }
    }
}
