package com.example.rayleigh.monthyreport_05_2015;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {
    public static int NOTIFICATION_ID = 1;
    public NotificationCompat.Builder builder;
    public NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startLoveActivity(View view){
        Intent intent = new Intent(this, LoveActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder = new NotificationCompat.Builder(this);

//        builder.setSmallIcon(R.drawable.ic_stat_notification);
        builder.setSmallIcon(R.drawable.small_love);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.large_love));
        builder.setContentTitle("Base Notification");
        builder.setContentText("Base Notification");
        builder.setSubText("Go to love-activity");

        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        incrementsetNotificationId();
    }

    public void customNotification(View view){
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.ic_stat_notification);
        builder.setAutoCancel(true);
        Notification notification = builder.build();

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
        final String time = DateFormat.getTimeInstance().format(new Date()).toString();
        contentView.setTextViewText(R.id.textView, "Now: " + time);
        notification.contentView = contentView;
        notificationManager.notify(NOTIFICATION_ID, notification);
        incrementsetNotificationId();
    }

    public void processBarNotification(View view){
        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.ic_stat_notification);
        new Thread(
            new Runnable() {
                @Override
                public void run() {
                    int incr, notic_id = NOTIFICATION_ID;
                    incrementsetNotificationId();
                    for (incr = 0; incr <= 100; incr+=5) {
                        builder.setProgress(100, incr, false);
                        notificationManager.notify(notic_id, builder.build());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Log.d("ProcessBar", "sleep failure");
                        }
                    }
                    builder.setContentText("Download complete")
                            .setProgress(0, 0, false);
                    notificationManager.notify(notic_id, builder.build());
                    incrementsetNotificationId();
                }
            }
        ).start();
    }

    public void processBarNotification2(View view){
        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.drawable.ic_stat_notification);
        builder.setProgress(0, 0, true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        incrementsetNotificationId();
    }

    public void incrementsetNotificationId() {
        NOTIFICATION_ID++;
    }
}
