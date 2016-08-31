package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.app.Application;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ConnectedActivity extends AppCompatActivity implements CommandHandler, WifiP2pManager.ActionListener {
    private WifiDirectTestApplication app;
    private TextView txtCommands;
    private TextView txtDevice;
    private FloatingActionButton btnDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected);

        app = ((WifiDirectTestApplication) getApplication());

        this.txtCommands = (TextView) findViewById(R.id.txtCommands);
        this.txtCommands.setMovementMethod(new ScrollingMovementMethod());

        this.txtDevice = (TextView) findViewById(R.id.txtDevice);
        Intent intent = getIntent();
        this.txtDevice.setText(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));

        startSocketServer();

        this.btnDisconnect = (FloatingActionButton) findViewById(R.id.btnDisconnect);
        this.btnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnect();
            }
        });
    }

    private void disconnect() {
        app.getHelper().disconnect(this);
    }

    private void writeCommand(String command) {
        StringBuilder message = new StringBuilder(command);
        message.append(System.getProperty("line.separator"));
        message.append(this.txtCommands.getText());
        this.txtCommands.setText(message);
    }

    private void startSocketServer() {
        new SocketServerAsyncTask(getApplicationContext(), this).execute();
    }

    @Override
    public void handle(Integer result) {
        if (result == 1) {
            writeCommand("Pong - REMOTE");
        }

        startSocketServer();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "Disconnect successful :)", Toast.LENGTH_LONG);
    }

    @Override
    public void onFailure(int i) {
        Toast.makeText(this, "Disconnect unsuccessful :(", Toast.LENGTH_LONG);
    }
}