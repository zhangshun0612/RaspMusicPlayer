package com.example.zhangshun.raspmusicplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.zhangshun.raspmusicplayer.Console.CmdConsole;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class UIService extends Service {

    private static String TAG = Service.class.getName();

    private UIServiceBinder mBinder;
    private ServerSocket mServerSocket = null;
    private CmdConsole mConsole = null;

    public UIService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "UI Service onCrete");

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    mServerSocket = new ServerSocket(1234);
                    while (true){
                        Socket socket = mServerSocket.accept();
                        Log.d(TAG, "Service Connected");
                        SocketServerThread thread = new SocketServerThread(socket);
                        thread.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mServerSocket != null){
            try {
                mServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new UIServiceBinder();
        return mBinder;
    }

    public void setCmdConsole(CmdConsole console){
        mConsole = console;
    }

    public class UIServiceBinder extends Binder {

        public UIService getService(){
            return UIService.this;
        }
    }


    private class SocketServerThread extends Thread implements CmdConsole.CmdConsoleEventListener {

        private Socket mSocket;
        InputStream is = null;
        BufferedReader br = null;

        OutputStream os = null;
        BufferedWriter bw = null;

        public SocketServerThread(Socket socket){
            mSocket = socket;
        }

        @Override
        public void run() {
            try {
                is = mSocket.getInputStream();
                br = new BufferedReader(new InputStreamReader(is));

                os = mSocket.getOutputStream();
                bw = new BufferedWriter(new OutputStreamWriter(os));

                mConsole.setCmdConsoleEventListener(this);
                String receiveInfo;
                while((receiveInfo = br.readLine()) != null){
                    Log.d(TAG, "Received: " + receiveInfo);
                    if(mConsole != null){
                        mConsole.getInputCmdLine(receiveInfo);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if(br != null)
                        br.close();

                    if(is != null)
                        is.close();

                    if(bw != null)
                        bw.close();

                    if(os != null)
                        os.close();

                    if(mSocket != null)
                        mSocket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public void sendEchoMessage(String msg) {
            if(bw != null){
                try {
                    bw.write(msg + "\n");
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
