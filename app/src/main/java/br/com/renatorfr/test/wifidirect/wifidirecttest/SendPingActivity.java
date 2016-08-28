package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SendPingActivity extends AppCompatActivity {
    private Button btnPing;
    private TextView txtDevice;
    private String deviceAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_ping);

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
    }

    private void sendCommand() {
        new SocketClientAsyncTask(deviceAddress).execute(1);
    }
}
