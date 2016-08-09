package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

public class WifiDirectBroadcastReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "WIFIP2P_BROADCAST";

    private WifiP2PHelper wifiP2PHelper;

    public WifiDirectBroadcastReceiver(WifiP2PHelper wifiP2PHelper) {
        super();

        this.wifiP2PHelper = wifiP2PHelper;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
                Log.i(LOG_TAG, LogCommands.WIFIP2P_ENABLED.getCommand());
            } else {
                // Wifi P2P is not enabled
                Log.i(LOG_TAG, LogCommands.WIFIP2P_DISABLED.getCommand());
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            if (wifiP2PHelper != null) {
                wifiP2PHelper.requestPeers();
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
    }
}
