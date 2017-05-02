package edu.csumb.anna.rendevu;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Sal on 5/2/2017.
 */

public class RVNotifications {

    public static int NOTIFICATION_ID = 1;

    public RVNotifications(){}

    public void sendNotification(Context aContext){
        //Notifications with a broadcast receiver
        ///////////////////////////////////////
        //Create an Intent for the BroadcastReceiver
        Intent buttonIntentYes = new Intent(aContext, ButtonReceiver.class);
        buttonIntentYes.putExtra("notificationId",NOTIFICATION_ID);
        buttonIntentYes.putExtra("isOK", true);

        Intent buttonIntentNo = new Intent(aContext, ButtonReceiver.class);
        buttonIntentNo.putExtra("notificationId",NOTIFICATION_ID+1);
        buttonIntentNo.putExtra("isOK", false);

//Create the PendingIntent
        PendingIntent btPendingIntentYes = PendingIntent.getBroadcast(aContext, 0, buttonIntentYes,PendingIntent.FLAG_ONE_SHOT);
        PendingIntent btPendingIntentNo = PendingIntent.getBroadcast(aContext, 1, buttonIntentNo,PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(aContext)
                .setSmallIcon(R.drawable.ic_notification_rendevu)
                .setContentTitle("Is the date going ok?")
                .setContentText("Let us know!")
                .addAction(android.R.drawable.checkbox_on_background, "Yes", btPendingIntentYes)
                .addAction(android.R.drawable.ic_delete, "No", btPendingIntentNo)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX);

        NotificationManager notificationManager = (NotificationManager) aContext.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
