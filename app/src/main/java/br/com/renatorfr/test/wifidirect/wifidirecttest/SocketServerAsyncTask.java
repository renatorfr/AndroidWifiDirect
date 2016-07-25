package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerAsyncTask extends AsyncTask<Void, Integer, Integer> {

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
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket client = serverSocket.accept();

            /**
             * If this code is reached, a client has connected and transferred data
             */
            InputStream inputstream = client.getInputStream();
            serverSocket.close();
            return inputstream.read();
        } catch (IOException e) {
            Log.e(commandHandler.TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result != null) {
            commandHandler.handle(Commands.getByCode(result));
        }
    }
}