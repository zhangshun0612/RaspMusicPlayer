package com.example.zhangshun.raspmusicplayer.Console;

public class MusicCmdConsole extends CmdConsole {

    @Override
    public void parseCmdLine(String cmd) {
        String echoMsg = "invalid";
        sendEchoMessage(echoMsg);
    }
}
