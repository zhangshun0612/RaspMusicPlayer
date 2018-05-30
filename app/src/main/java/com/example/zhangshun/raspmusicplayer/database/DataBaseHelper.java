package com.example.zhangshun.raspmusicplayer.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "rasp_music.db";
    private static int DATABASE_VERSION = 1;

    public static String MUSIC_LIST_TABLE = "music_list_table";
    public static String MUSICID_COLUMN = "music_id";
    public static String TITLE_COLUMN = "title";
    public static String ALBUM_COLUMN = "album";
    public static String ARTIST_COLUMN = "artist";
    public static String URL_COLUMN = "url";
    public static String DURATION_COLUMN = "duration";
    public static String SIZE_COLUMN = "size";


    private static String CREATE_MUSIC_LIST_TABLE_SQL = "CREATE TABLE IF NOT exists " +
            MUSIC_LIST_TABLE  + "(ID " + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MUSICID_COLUMN  + " INTEGER,"
            + TITLE_COLUMN + " TEXT,"
            + ALBUM_COLUMN + " TEXT,"
            + ARTIST_COLUMN + " TEXT,"
            + URL_COLUMN + " TEXT,"
            + DURATION_COLUMN + " INTEGER,"
            + SIZE_COLUMN + " INTEGER"
            + ");";

    public DataBaseHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MUSIC_LIST_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
