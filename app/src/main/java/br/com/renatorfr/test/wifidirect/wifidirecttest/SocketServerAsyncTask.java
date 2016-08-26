package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerAsyncTask extends AsyncTask<Void, Integer, Integer> {
    public static final Integer SOCKET_PORT = 8888;
    private static final String LOG_TAG = "WIFIP2P_SOCKET";
    private Context context;
    private CommandHandler commandHandler;

    public SocketServerAsyncTask(Context context, CommandHandler commandHandler) {
        this.context = context;
        this.commandHandler = commandHandler;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        try {

            /**
             * Create a server socket and wait for client connections. This
             * call blocks until a connection is accepted from a client
             */
            ServerSocket serverSocket = new ServerSocket(SOCKET_PORT);
            Log.i(LOG_TAG, "Socket server started");
            Socket client = serverSocket.accept();

            /**
             * If this code is reached, a client has connected and transferred data
             */
            DataInputStream inputstream = new DataInputStream(client.getInputStream());
            serverSocket.close();
            return inputstream.readInt();
        } catch (IOException e) {
            Log.e(commandHandler.TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result != null) {
            commandHandler.handle(result);
        }
    }
}