package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConnectedActivity extends AppCompatActivity implements CommandHandler {
    private Button btnUp;
    private Button btnDown;
    private Button btnLeft;
    private Button btnRight;
    private Button btnConnect;
    private TextView txtCommands;
    private TextView txtLog;
    private BroadcastReceiver receiver;
    private WifiP2PHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.btnUp = (Button) findViewById(R.id.btnUp);
        this.btnDown = (Button) findViewById(R.id.btnDown);
        this.btnLeft = (Button) findViewById(R.id.btnLeft);
        this.btnRight = (Button) findViewById(R.id.btnRight);
        this.btnConnect = (Button) findViewById(R.id.btnConnect);

        this.txtCommands = (TextView) findViewById(R.id.txtCommands);
        this.txtCommands.setMovementMethod(new ScrollingMovementMethod());

        this.txtLog = (TextView) findViewById(R.id.txtLog);
        this.txtLog.setMovementMethod(new ScrollingMovementMethod());

        setListeners();

        this.helper = new WifiP2PHelper(getApplicationContext());
//        this.receiver = new WifiDirectBroadcastReceiver(helper.getWifiP2pManager(), helper.getChannel(), this, helper);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, helper.getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void setListeners() {
        this.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.UP);
            }
        });

        this.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.DOWN);
            }
        });

        this.btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.LEFT);
            }
        });

        this.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommand(Commands.RIGHT);
            }
        });

        this.btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect();
            }
        });
    }

    private void connect() {
        helper.discoverPeers();
    }

    private void writeCommand(Commands command) {
        StringBuilder message = new StringBuilder(command.getCommand());
        message.append(System.getProperty("line.separator"));
        message.append(this.txtCommands.getText());
        this.txtCommands.setText(message);
    }

    private void startSocketServer() {
//        new SocketServerAsyncTask(getApplicationContext(), this).execute();
    }

    public void writeLog(LogCommands logCommand) {
        this.writeLog(logCommand, null);
    }

    public void writeLog(LogCommands logCommand, String arg) {
        StringBuilder message = new StringBuilder(logCommand.getCommand());
        if (arg != null) {
            message.append(arg);
        }
        message.append(System.getProperty("line.separator"));
        message.append(this.txtLog.getText());
        this.txtLog.setText(message);
    }

    @Override
    public void handle(Commands command) {
        writeCommand(command);
    }

    @Override
    public void log(LogCommands logCommand) {
        writeLog(logCommand);
    }

    @Override
    public void log(LogCommands logCommand, String arg) {
        writeLog(logCommand, arg);
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

enum LogCommands {
    WIFIP2P_ENABLED("Wifi P2P enabled \\o/"), WIFIP2P_DISABLED("Wifi P2P disabled :("),
    DISCOVER_PEERS_SUCCESS("Peers discover success"), DISCOVER_PEERS_FAILURE("Peers discover failure"),
    PEERS_FOUND("Peers found \\o/"), PEER("\tPeer: "), PEER_CONNECTED("Peer connected \\o/"),
    PEER_NOT_CONNECTED("Peer not connected :(");

    private String logCommand;

    LogCommands(String logCommand) {
        this.logCommand = logCommand;
    }

    public String getCommand() {
        return logCommand;
    }
}