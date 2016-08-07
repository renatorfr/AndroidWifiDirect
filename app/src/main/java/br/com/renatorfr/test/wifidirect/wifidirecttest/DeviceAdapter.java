package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.net.wifi.p2p.WifiP2pDeviceList;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class DeviceAdapter extends RecyclerView.Adapter {
    private WifiP2pDeviceList deviceList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public DeviceAdapter(WifiP2pDeviceList deviceList) {
        this.deviceList = deviceList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
