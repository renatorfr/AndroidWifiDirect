package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements WifiP2pManager.PeerListListener, WifiP2pManager.ConnectionInfoListener {
    private static final String LOG_TAG = "WIFIP2P_MAIN_ACTIVITY";
    public static final String EXTRA_MESSAGE = "br.com.renatorfr.test.wifidirect.MESSAGE";
    private RecyclerView lstDevice;
    private RecyclerView.LayoutManager layoutManager;
    private DeviceAdapter adapter;
    private WifiP2PHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lstDevice = (RecyclerView) findViewById(R.id.lstDevice);
        this.lstDevice.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        lstDevice.setLayoutManager(layoutManager);

        this.helper = new WifiP2PHelper(getApplicationContext(), this, this);
        this.helper.discoverPeers();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        Log.i(LOG_TAG, LogCommands.PEERS_FOUND.getCommand());
        adapter = new DeviceAdapter(peers, lstDevice, helper);
        lstDevice.setAdapter(adapter);
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        if (info.groupOwnerAddress == null) {
            return;
        }
        if (info.isGroupOwner) {
            changeActivity(ConnectedActivity.class, info.groupOwnerAddress.getHostAddress());
        } else {
            changeActivity(SendPingActivity.class, info.groupOwnerAddress.getHostAddress());
        }
    }

    private void changeActivity(Class clazz, String address) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra(EXTRA_MESSAGE, address);
        startActivity(intent);
    }
}