package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements WifiP2pManager.PeerListListener {
    private Button btnUp;
    private Button btnDown;
    private Button btnLeft;
    private Button btnRight;
    private Button btnRefresh;
    private TextView txtCommands;
    private TextView txtLog;
    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnUp = (Button) findViewById(R.id.btnUp);
        this.btnDown = (Button) findViewById(R.id.btnDown);
        this.btnLeft = (Button) findViewById(R.id.btnLeft);
        this.btnRight = (Button) findViewById(R.id.btnRight);
        this.btnRefresh = (Button) findViewById(R.id.btnRefresh);

        this.txtCommands = (TextView) findViewById(R.id.txtCommands);
        this.txtCommands.setMovementMethod(new ScrollingMovementMethod());

        this.txtLog = (TextView) findViewById(R.id.txtLog);
        this.txtLog.setMovementMethod(new ScrollingMovementMethod());

        setListeners();

        wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = wifiP2pManager.initialize(this, getMainLooper(), null);
        receiver = new WifiDirectBroadcastReceiver(wifiP2pManager, channel, this, this);

        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        discoverPeers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
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

        this.btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discoverPeers();
            }
        });
    }

    private void discoverPeers() {
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                writeLog(LogCommands.DISCOVER_PEERS_SUCCESS);
            }

            @Override
            public void onFailure(int reason) {
                writeLog(LogCommands.DISCOVER_PEERS_FAILURE);
            }
        });
    }

    private void writeCommand(Commands command) {
        StringBuilder message = new StringBuilder(command.getCommand());
        message.append(System.getProperty("line.separator"));
        message.append(this.txtCommands.getText());
        this.txtCommands.setText(message);
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
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        writeLog(LogCommands.PEERS_FOUND);
        for (WifiP2pDevice peer : peers.getDeviceList()) {
            writeLog(LogCommands.PEER, peer.toString());
        }
    }
}

enum Commands {
    UP("Up"), DOWN("Down"), LEFT("Left"), RIGHT("Right");

    private String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}

enum LogCommands {
    WIFIP2P_ENABLED("Wifi P2P enabled \\o/"), WIFIP2P_DISABLED("Wifi P2P disabled :("),
    DISCOVER_PEERS_SUCCESS("Peers discover success"), DISCOVER_PEERS_FAILURE("Peers discover failure"),
    PEERS_FOUND("Peers found \\o/"), PEER("\tPeer: ");

    private String logCommand;

    LogCommands(String logCommand) {
        this.logCommand = logCommand;
    }

    public String getCommand() {
        return logCommand;
    }
}