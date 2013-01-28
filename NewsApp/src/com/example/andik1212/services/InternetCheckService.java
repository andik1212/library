package com.example.andik1212.services;

/**
 * Created with IntelliJ IDEA.
 * User: Vadim
 * Date: 26.11.12
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import com.example.andik1212.StartActivity;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class InternetCheckService extends Service {
    public static String UPDATE = "com.example.andik1212.UPDATE";
//    private MainActivity activity;
    NotificationManager mNotificationManager;
    int icon = R.drawable.ic_dialog_alert; // Иконка для уведомления
    CharSequence tickerText = "New Notification";
    long when = System.currentTimeMillis(); // Выясним системное время

    Timer timer;
    Timer timerNotification;
    public void onCreate() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Создаем экземпляр менеджера уведомлений

        timer = new Timer();
        if (timerNotification != null) timerNotification.cancel();
        timerNotification = new Timer();
        super.onCreate();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //TODO update data
                sendBroadcast(new Intent(UPDATE));
            }
        }, 60000, 60000);

        timerNotification.schedule(new TimerTask() {
            @Override
            public void run() {
                //To change body of implemented methods use File | Settings | File Templates.
                sendNotificaton();
            }
        },1440*60000,1440*60000);
        return super.onStartCommand(intent, flags, startId);
    }
    void sendNotificaton(){
        Notification notification = new Notification(icon, tickerText, when); // Создаем экземпляр уведомления, и передаем ему наши параметры
        Context context = getApplicationContext();
        CharSequence contentTitle = "NewsApp by Andik";
        // Текст заголовка уведомления при развернутой строке статуса
        CharSequence contentText = "You are not visiting NewsApp for a long time";
        //Текст под заголовком уведомления при развернутой строке статуса
        Intent notificationIntent = new Intent(this, StartActivity.class);
        // Создаем экземпляр Intent
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        // Подробное описание в UPD к статье
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        // Передаем в наше уведомление параметры вида при развернутой строке состояния
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        mNotificationManager.notify(1, notification);
        // И наконец показываем наше уведомление через менеджер передав его ID


    }

    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

}