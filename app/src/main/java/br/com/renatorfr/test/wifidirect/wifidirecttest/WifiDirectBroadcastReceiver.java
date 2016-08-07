package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager wifiP2pManager;
    private WifiP2pManager.Channel channel;
    private ConnectedActivity activity;
    private WifiP2pManager.PeerListListener peerListListener;

    public WifiDirectBroadcastReceiver(WifiP2pManager wifiP2pManager,
                                       WifiP2pManager.Channel channel,
                                       ConnectedActivity activity,
                                       WifiP2pManager.PeerListListener peerListListener) {
        super();
        this.wifiP2pManager = wifiP2pManager;
        this.channel = channel;
        this.activity = activity;
        this.peerListListener = peerListListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
                activity.writeLog(LogCommands.WIFIP2P_ENABLED);
            } else {
                // Wifi P2P is not enabled
                activity.writeLog(LogCommands.WIFIP2P_DISABLED);
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            if (wifiP2pManager != null) {
                wifiP2pManager.requestPeers(channel, peerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
    }
}
