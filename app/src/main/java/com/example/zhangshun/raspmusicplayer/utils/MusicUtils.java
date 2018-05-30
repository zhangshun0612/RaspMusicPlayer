package com.example.zhangshun.raspmusicplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.zhangshun.raspmusicplayer.bean.Music;
import com.example.zhangshun.raspmusicplayer.database.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MusicUtils {

    private static MusicUtils instance = null;
    private Context mContext;

    private MusicUtils(Context context)
    {
        mContext = context;
    }

    public static synchronized MusicUtils getInstance(Context context) {
        if(instance == null){
            instance = new MusicUtils(context);
        }

        return instance;
    }

    public List<Music> getMusicList(){
        return  DBManager.getInstance(mContext).getMusicList();
    }

    public List<Music> getMusicListFromStorage(){

        List<Music> musicList = new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

        while(cursor.moveToNext()){
            int id= cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            int size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

            //滤除30s 以下的银屏
            if(duration < 30) {
                continue;
            }

            Music music = new Music(id, title, album, artist, url, duration, size);
            musicList.add(music);
        }

        return musicList;
    }
}
