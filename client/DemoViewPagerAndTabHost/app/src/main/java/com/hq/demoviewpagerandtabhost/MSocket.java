package com.hq.demoviewpagerandtabhost;

import android.app.Application;

import io.socket.client.IO;
import io.socket.client.Socket;
import java.net.URISyntaxException;
/**
 * Created by My-PC on 3/31/2017.
 */

public class MSocket extends Application {
    public MSocket(){

    }
    public Socket MSocket;
    {
        try {
            MSocket = IO.socket("http://192.168.56.1:3000");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return MSocket;
    }
}
