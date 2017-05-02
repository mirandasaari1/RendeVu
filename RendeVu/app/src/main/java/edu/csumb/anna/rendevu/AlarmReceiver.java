package edu.csumb.anna.rendevu;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import edu.csumb.anna.rendevu.api.RendeVuAPI;

/**
 * Created by Sal on 5/2/2017.
 */

public class AlarmReceiver extends BroadcastReceiver{

    final String TAG = "AlarmReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "pinged");
        //call on notification
        RVNotifications notify = new RVNotifications();
        notify.sendNotification(context);
    }
}