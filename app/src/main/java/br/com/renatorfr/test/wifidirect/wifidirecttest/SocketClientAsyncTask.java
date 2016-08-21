package br.com.renatorfr.test.wifidirect.wifidirecttest;


import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketClientAsyncTask extends AsyncTask<Integer, Void, Void> {
    private static final String LOG_TAG = "WIFIP2P_SOCKET_CLI";
    private String host;
    private Socket socket;

    public SocketClientAsyncTask(String host) {
        this.host = host;
        this.socket = new Socket();
    }

    @Override
    protected Void doInBackground(Integer... params) {
        Log.e(LOG_TAG, "Send command called");
        try {
            /**
             * Create a client socket with the host,
             * port, and timeout information.
             */
            socket.bind(null);
            socket.connect((new InetSocketAddress(host, SocketServerAsyncTask.SOCKET_PORT)), 500);

            /**
             * Create a byte stream from a command and pipe it to the output stream
             * of the socket. This data will be retrieved by the server device.
             */
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            outputStream.writeInt(params[0]);
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        /**
         * Clean up any open sockets when done
         * transferring or if an exception occurred.
         */ finally {
            if (socket != null) {
                if (socket.isConnected()) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }
                }
            }
        }

        return null;
    }
}