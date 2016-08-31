package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SendPingActivity extends AppCompatActivity implements WifiP2pManager.ActionListener {
    private WifiDirectTestApplication app;
    private Button btnPing;
    private TextView txtDevice;
    private String deviceAddress;
    private FloatingActionButton btnDisconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_ping);

        app = ((WifiDirectTestApplication) getApplication());

        Intent intent = getIntent();
        this.deviceAddress = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        this.txtDevice = (TextView) findViewById(R.id.txtDevice);
        this.txtDevice.setText(deviceAddress);

        this.btnPing = (Button) findViewById(R.id.btnPing);
        this.btnPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand();
            }
        });

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

    private void sendCommand() {
        new SocketClientAsyncTask(deviceAddress).execute(1);
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
