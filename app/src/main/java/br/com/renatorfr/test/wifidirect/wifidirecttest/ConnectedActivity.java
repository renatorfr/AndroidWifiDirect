package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConnectedActivity extends AppCompatActivity implements CommandHandler {
    private static final String LOG_TAG = "WIFIP2P_CONNECTED_ACT";

    private Button btnUp;
    private Button btnDown;
    private Button btnLeft;
    private Button btnRight;
    private TextView txtCommands;
    private TextView txtDevice;
    private WifiP2pDevice device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connected);

        this.btnUp = (Button) findViewById(R.id.btnUp);
        this.btnDown = (Button) findViewById(R.id.btnDown);
        this.btnLeft = (Button) findViewById(R.id.btnLeft);
        this.btnRight = (Button) findViewById(R.id.btnRight);

        this.txtCommands = (TextView) findViewById(R.id.txtCommands);
        this.txtCommands.setMovementMethod(new ScrollingMovementMethod());

        this.txtDevice = (TextView) findViewById(R.id.txtDevice);
        Intent intent = getIntent();
        this.device = intent.getParcelableExtra(MainActivity.EXTRA_MESSAGE);
        this.txtDevice.setText(this.device.deviceName);

        setListeners();

        startSocketServer();
    }

    private void setListeners() {
        this.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.UP);
                sendCommand(Commands.UP);
            }
        });

        this.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.DOWN);
                sendCommand(Commands.DOWN);
            }
        });

        this.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.LEFT);
                sendCommand(Commands.LEFT);
            }
        });

        this.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.RIGHT);
                sendCommand(Commands.RIGHT);
            }
        });
    }

    private void writeCommand(Commands command) {
        writeCommand(command, null);
    }

    private void writeCommand(Commands command, String suffix) {
        StringBuilder message = new StringBuilder(command.getCommand());
        message.append(suffix != null ? suffix : "");
        message.append(System.getProperty("line.separator"));
        message.append(this.txtCommands.getText());
        this.txtCommands.setText(message);
    }

    private void sendCommand(Commands command){
        new SocketClientAsyncTask(device.deviceAddress).execute(command.getCode());
    }

    private void startSocketServer() {
        new SocketServerAsyncTask(getApplicationContext(), this).execute();
    }

    @Override
    public void handle(Commands command) {
        writeCommand(command, " - REMOTE");
    }
}

enum Commands {
    UP("Up", 1), DOWN("Down", 2), LEFT("Left", 3), RIGHT("Right", 4);

    private String command;
    private Integer code;

    Commands(String command, Integer code) {
        this.command = command;
        this.code = code;
    }

    public String getCommand() {
        return command;
    }

    public Integer getCode() {
        return code;
    }

    public static Commands getByCode(Integer code) {
        if (code == null) {
            return null;
        }

        for (Commands command : Commands.values()) {
            if (command.getCode() == code) {
                return command;
            }
        }

        return null;
    }
}