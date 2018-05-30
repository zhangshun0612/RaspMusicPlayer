package com.example.zhangshun.raspmusicplayer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.zhangshun.raspmusicplayer.bean.Music;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private static String TAG = DBManager.class.getName();

    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    private static DBManager instance = null;

    private DBManager(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
        db = dataBaseHelper.getWritableDatabase();
    }

    public static synchronized DBManager getInstance(Context context){
        if(instance == null){
            instance = new DBManager(context);
        }

        return instance;
    }

    public boolean isMusicExists(int musicId){
        boolean ret = false;
        Cursor cursor = db.query(DataBaseHelper.MUSIC_LIST_TABLE, null, DataBaseHelper.MUSICID_COLUMN + " = ?",
                new String[]{String.valueOf(musicId)}, null, null, null);

        if(cursor == null){
            return ret;
        }

        if(cursor.moveToFirst()){
            ret = true;
        }

        cursor.close();
        return ret;

    }

    public List<Music> getMusicList(){
        List<Music> musicList = new ArrayList<>();

        Cursor cursor = db.query(DataBaseHelper.MUSIC_LIST_TABLE, null, null, null, null, null, null);
        if(cursor != null){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.MUSICID_COLUMN));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.TITLE_COLUMN));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.ALBUM_COLUMN));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.ARTIST_COLUMN));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.URL_COLUMN));
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.DURATION_COLUMN));
            int size = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.SIZE_COLUMN));

            Music music = new Music(id, title, album, artist, url, duration, size);

            musicList.add(music);
        }

        return musicList;
    }

    public int updateMusicList(List<Music> musicList){
        int addInCount = 0;
        db.beginTransaction();
        try{
            for(Music music : musicList){

                if(isMusicExists(music.getId())){
                    continue;
                }

                ContentValues values = new ContentValues();

                values.put(DataBaseHelper.MUSICID_COLUMN, music.getId());
                values.put(DataBaseHelper.TITLE_COLUMN, music.getTitle());
                values.put(DataBaseHelper.ALBUM_COLUMN, music.getAlbum());
                values.put(DataBaseHelper.ARTIST_COLUMN, music.getArtist());
                values.put(DataBaseHelper.URL_COLUMN, music.getUrl());
                values.put(DataBaseHelper.DURATION_COLUMN, music.getDuration());
                values.put(DataBaseHelper.SIZE_COLUMN, music.getSize());

                long cnt = db.insert(DataBaseHelper.MUSIC_LIST_TABLE, null, values);
                addInCount += cnt;
                if(cnt < 0){
                    Log.d(TAG , "Insert Failed");
                }
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
            addInCount = 0;
        }finally {
            db.endTransaction();
        }

        return addInCount;
    }
}
