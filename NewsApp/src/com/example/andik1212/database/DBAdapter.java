package com.example.andik1212.database;

/**
 * Created with IntelliJ IDEA.
 * User: Andik
 * Date: 15.01.13
 * Time: 13:08
 * To change this template use File | Settings | File Templates.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DBAdapter {
    int id = 0;
    public static final String KEY_ROWID = "_id";
    public static final String KEY_JID = "jid";
    public static final String KEY_VALUE = "value";
    public static final String KEY_DATE = "date";
    public static final String KEY_CONTENT = "content";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "LikedArticles";
    private static final String DATABASE_TABLE = "Articles";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table tblRandomQuotes ("
                    + "jid text primary key not null, value text not null, date text not null, content text not null );";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);
        }
    }

    //---открывает базу данных---
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---закрывает базу данных---
    public void close() {
        DBHelper.close();
    }

    //---вставляем заголовок в базу данных---
    public long putDB(String[] text) {
        Cursor cursor = db.rawQuery(
                "SELECT COUNT("+KEY_JID +") FROM "+DATABASE_TABLE+" WHERE "+KEY_JID+" = "+text[0], null);
        if (cursor.getInt(0) == 0){
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_JID, text[0]);
        initialValues.put(KEY_VALUE, text[1]);
        initialValues.put(KEY_DATE, text[2]);
        initialValues.put(KEY_CONTENT, text[3]);
        return db.insert(DATABASE_TABLE, null, initialValues);
        } else {
            return 0;
        }
    }

    public int getCountEntries() {
        Cursor cursor = db.rawQuery(
                "SELECT COUNT("+KEY_JID +") FROM "+DATABASE_TABLE, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return cursor.getInt(0);

    }

//    public String getAllEntry() {
//
//        id = getCountEntries();
//        Random random = new Random();
//        int rand = random.nextInt(getAllEntries());
//        if (rand == 0)
//            ++rand;
//        Cursor cursor = db.rawQuery(
//                "SELECT Quote FROM tblRandomQuotes WHERE _id = " + rand, null);
//        if (cursor.moveToFirst()) {
//            return cursor.getString(0);
//        }
//        return cursor.getString(0);
//
//    }

}

