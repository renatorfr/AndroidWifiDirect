package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements WifiP2pManager.PeerListListener {
    private static final String LOG_TAG = "WIFIP2P_MAIN_ACTIVITY";
    private RecyclerView lstDevice;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private WifiP2PHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.lstDevice = (RecyclerView) findViewById(R.id.lstDevice);
        this.lstDevice.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        lstDevice.setLayoutManager(layoutManager);

        this.helper = new WifiP2PHelper(getApplicationContext(), this);
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
        lstDevice.setAdapter(new DeviceAdapter(peers));
    }
}