package com.example.pcconfigproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String C_ID = "PC_config_notification_channel";
    private final int N_ID = 0;
    private Context mContext;
    private NotificationManager mNotificationManager;
    public NotificationHandler(Context context) {
        this.mContext = context;
        this.mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();
    }

    private void createChannel() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        NotificationChannel channel = new NotificationChannel(C_ID, "PC Config Notification", NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableVibration(true);
        channel.setDescription("Notifications from PC Config app.");
        this.mNotificationManager.createNotificationChannel(channel);
    }

    public void send(String message) {
        NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(mContext, C_ID)
                .setContentTitle("PC Config APP")
                .setContentText(message)
                .setSmallIcon(R.drawable.compare_configs);
        this.mNotificationManager.notify(N_ID, nbuilder.build());
    }
}
