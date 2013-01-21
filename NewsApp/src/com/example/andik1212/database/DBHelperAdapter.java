package com.example.andik1212.database;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Created with IntelliJ IDEA.
 * User: Andik
 * Date: 21.01.13
 * Time: 5:01
 * To change this template use File | Settings | File Templates.
 */
public class DBHelperAdapter {

    private static DataBaseHelper dataBaseHelper;

    public static DataBaseHelper GetHelper(){
        return dataBaseHelper;
    }
    public static void SetHelper(Context context){
        dataBaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
    }
    public static void ReleaseHelper(){
        OpenHelperManager.releaseHelper();
        dataBaseHelper = null;
    }

}
