package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class ConnectedActivity extends AppCompatActivity implements CommandHandler {
    private TextView txtCommands;
    private TextView txtDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected);

        this.txtCommands = (TextView) findViewById(R.id.txtCommands);
        this.txtCommands.setMovementMethod(new ScrollingMovementMethod());

        this.txtDevice = (TextView) findViewById(R.id.txtDevice);
        Intent intent = getIntent();
        this.txtDevice.setText(intent.getStringExtra(MainActivity.EXTRA_MESSAGE));

        startSocketServer();
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
}