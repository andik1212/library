package com.example.andik1212;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.andik1212.fragments.FragmentList;
import com.example.andik1212.services.InternetCheckService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

public class StartActivity extends SherlockFragmentActivity {
    public static String INTERNET_STATUS = "com.example.andik1212.DOWN";
    private static StartActivity Instance;


    public static final int OPT_BUTTON_ALLLIKES = 0;


    /**
     * Called when the activity is first created.
     */
    FragmentList fragmentList = new FragmentList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Sherlock);
        setContentView(R.layout.main);
        checkInternet();
        startService(new Intent(this, InternetCheckService.class));
        Instance = this;
        if (savedInstanceState == null) {
            int commit = getSupportFragmentManager().beginTransaction().add(R.id.list_frag, fragmentList).commit();
        }
    }





    public void onDestroy(){
        stopService(new Intent(this, InternetCheckService.class));
//        db.close();
//        InternetCheckService.UPDATE = "com.example.andik1212.NULL";
        super.onDestroy();
    }



    private void checkInternet(){
        Context mContext = StartActivity.this.getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {
            INTERNET_STATUS = "com.example.andik1212.UP";
        }
        else {
            INTERNET_STATUS = "com.example.andik1212.DOWN";
        }
        sendBroadcast(new Intent(INTERNET_STATUS));

    }

    public static class BroadcastListenerUpdateAndCheck extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent){
            if (Instance == null || intent == null){
                return;
            }
            String action = intent.getAction();
            if (action.equals(InternetCheckService.UPDATE)){
                Instance.updateList();


//                Toast.makeText(context, "broadcast recived", Toast.LENGTH_SHORT).show();

            }

        }
    }
    private void updateList(){
        checkInternet();
        if (INTERNET_STATUS == "com.example.andik1212.UP"){
            fragmentList.loadData();
        }

    }
}
