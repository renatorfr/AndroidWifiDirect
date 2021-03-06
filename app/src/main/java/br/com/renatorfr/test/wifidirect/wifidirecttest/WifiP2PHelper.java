package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.util.Collection;
import java.util.List;

public class WifiP2PHelper {
    private final String LOG_TAG = "WIFIP2P_HELPER";

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private IntentFilter intentFilter;
    private BroadcastReceiver receiver;
    private WifiP2pManager.PeerListListener peerListListener;
    private WifiP2pManager.ConnectionInfoListener connectionInfoListener;

    public WifiP2PHelper(Context context, WifiP2pManager.PeerListListener peerListListener, WifiP2pManager.ConnectionInfoListener connectionInfoListener) {
        this.peerListListener = peerListListener;
        this.connectionInfoListener = connectionInfoListener;

        wifiP2pManager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        channel = wifiP2pManager.initialize(context, context.getMainLooper(), null);

        initIntents();
        initBroadcastReceiver(context);
    }

    private void initBroadcastReceiver(Context context) {
        this.receiver = new WifiDirectBroadcastReceiver(this);
        context.registerReceiver(receiver, intentFilter);
    }

    private void initIntents() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    public void discoverPeers() {
        wifiP2pManager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(LOG_TAG, LogCommands.DISCOVER_PEERS_SUCCESS.getCommand());
            }

            @Override
            public void onFailure(int reason) {
                Log.i(LOG_TAG, LogCommands.DISCOVER_PEERS_FAILURE.getCommand());
            }
        });
    }

    public void requestPeers() {
        wifiP2pManager.requestPeers(channel, peerListListener);
    }

    public void connectTo(final WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        wifiP2pManager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i(LOG_TAG, LogCommands.PEER_CONNECTED.getCommand());
            }

            @Override
            public void onFailure(int reason) {
                Log.i(LOG_TAG, LogCommands.PEER_NOT_CONNECTED.getCommand());
            }
        });
    }

    public void receivedConnection() {
        wifiP2pManager.requestConnectionInfo(channel, connectionInfoListener);
    }

    public void disconnect(WifiP2pManager.ActionListener listener) {
        wifiP2pManager.removeGroup(channel, listener);
    }
}
