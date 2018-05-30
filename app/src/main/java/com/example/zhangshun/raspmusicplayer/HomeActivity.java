package com.example.zhangshun.raspmusicplayer;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.zhangshun.raspmusicplayer.Console.MusicCmdConsole;
import com.example.zhangshun.raspmusicplayer.service.MusicPlayerService;
import com.example.zhangshun.raspmusicplayer.service.UIService;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class HomeActivity extends Activity {

    private static String TAG = HomeActivity.class.getName();

    private MusicPlayerService.MusicPlayerServiceBinder mMusicServiceBinder;
    private MusicPlayerService mMusicService;

    private UIService.UIServiceBinder mUIServiceBinder;
    private UIService mUIService;

    private MusicCmdConsole musicCmdConsole = new MusicCmdConsole();

    private ServiceConnection mMusicServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "Service Connected");
            mMusicServiceBinder = (MusicPlayerService.MusicPlayerServiceBinder) service;
            mMusicService = mMusicServiceBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private ServiceConnection mUIServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mUIServiceBinder = (UIService.UIServiceBinder) service;
            mUIService = mUIServiceBinder.getService();

            mUIService.setCmdConsole(musicCmdConsole);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent musicServiceIntent = new Intent(HomeActivity.this, MusicPlayerService.class);
        bindService(musicServiceIntent, mMusicServiceConnection, Service.BIND_AUTO_CREATE);

        Intent uiServiceIntent = new Intent(HomeActivity.this, UIService.class);
        bindService(uiServiceIntent, mUIServiceConnection, Service.BIND_AUTO_CREATE);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(mMusicServiceConnection);
        unbindService(mUIServiceConnection);
    }
}
