package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

            /**
             * Create a byte stream from a JPEG file and pipe it to the output stream
             * of the socket. This data will be retrieved by the server device.
             */
            OutputStream outputStream = socket.getOutputStream();
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            inputStream = cr.openInputStream(Uri.parse("path/to/picture.jpg"));
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
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
