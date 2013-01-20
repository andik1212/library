package com.example.andik1212.services;

/**
 * Created with IntelliJ IDEA.
 * User: Vadim
 * Date: 26.11.12
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */

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


    Timer timer;
    public void onCreate() {
        timer = new Timer();
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
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

}