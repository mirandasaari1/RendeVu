package edu.csumb.anna.rendevu;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Sal on 4/24/2017.
 */

public class ButtonReceiver extends BroadcastReceiver {
    final String TAG = "ButtonReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle extras = intent.getExtras();
        boolean isOK = extras.getBoolean("isOK");
        int notificationId = extras.getInt("notificationId");

        Log.d(TAG, "broadcast receiver hit");
        Log.d(TAG, "isOK: "+isOK);
        // Do what you want were.

        // if you want cancel notification

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(isOK)
            manager.cancel(notificationId);
        else
            manager.cancel(notificationId-1);
    }
}