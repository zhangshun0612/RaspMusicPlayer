package com.example.zhangshun.raspmusicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MusicPlayerService extends Service {

    private MusicPlayerServiceBinder mBinder;

    public MusicPlayerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new MusicPlayerServiceBinder();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public class MusicPlayerServiceBinder extends Binder{
        public MusicPlayerService getService(){
            return MusicPlayerService.this;
        }
    }
}
