package com.example.andik1212;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.example.andik1212.database.DBHelperAdapter;
import com.example.andik1212.database.DataBaseHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: Andik
 * Date: 21.01.13
 * Time: 5:03
 * To change this template use File | Settings | File Templates.
 */
public class App extends Application {
    public void onCreate() {
        super.onCreate();
        DBHelperAdapter.SetHelper(getApplicationContext());
    }








    public void onTerminate(){

        super.onTerminate();
    }
}
