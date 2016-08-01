package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClient {
    Context context;
    String host;
    int port;
    int len;
    Socket socket = new Socket();
    byte buf[] = new byte[1024];

    public SocketClient(Context context, String host, int port, int len) {
        this.context = context;
        this.host = host;
        this.port = port;
        this.len = len;
    }

    public void send(Commands command) {
        try {
            /**
             * Create a client socket with the host,
             * port, and timeout information.
             */
            socket.bind(null);
            socket.connect((new InetSocketAddress(host, port)), 500);

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(command.getCode());
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(CommandHandler.TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(CommandHandler.TAG, e.getMessage());
        } finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        Log.e(CommandHandler.TAG, e.getMessage());
                    }
                }
            }
        }
    }
}
