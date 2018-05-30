package com.example.zhangshun.raspmusicplayer.Console;

public abstract class CmdConsole {
    CmdConsoleEventListener mListener;

    public void getInputCmdLine(String cmd){
        parseCmdLine(cmd);
    }

    public void sendEchoMessage(String msg){
        if(mListener != null){
            mListener.sendEchoMessage(msg);
        }
    }

    public void setCmdConsoleEventListener(CmdConsoleEventListener listener){
        mListener = listener;
    }

    public abstract void parseCmdLine(String cmd);

    public interface CmdConsoleEventListener{
        void sendEchoMessage(String msg);
    }
}
