package com.example.andik1212;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.andik1212.fragments.FragmentDetail;

public class ActivityDetail extends SherlockFragmentActivity {
    public static final int OPT_BUTTON_LIKE = 0;
    public static final int OPT_BUTTON_FACEBOOOK = 1;
    public static final int OPT_BUTTON_TWEETTER = 2;
//    public static final int OPT_BUTTON_DISLIKE = 1;
    public Context cntx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Sherlock);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            handleIntentExtras(getIntent());
        }
    }



    private void handleIntentExtras(Intent intent) {
        String[] text = new String[2];
        if (intent.hasExtra(FragmentDetail.EXTRA_TEXT)) {
            text = intent.getStringArrayExtra(FragmentDetail.EXTRA_TEXT);
        }

        FragmentDetail fragmentDetail = FragmentDetail.newInstance(text);
        getSupportFragmentManager().beginTransaction().add(R.id.frag_container, fragmentDetail).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frag_container);
//        if (fragment instanceof Fragment2_2) {
//            ((Fragment2_2) fragment).updateDetailsFragment();
//        }

        super.onBackPressed();
    }
}