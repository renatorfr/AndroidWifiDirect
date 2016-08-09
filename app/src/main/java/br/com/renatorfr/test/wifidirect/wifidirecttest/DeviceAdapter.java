package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private List<WifiP2pDevice> deviceList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView deviceName;

        public ViewHolder(TextView textView) {
            super(textView);
            deviceName = textView;
        }
    }

    public DeviceAdapter(WifiP2pDeviceList deviceList) {
        this.deviceList = new ArrayList<>(deviceList.getDeviceList());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setPadding(5, 5, 5, 5);

        return new ViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.deviceName.setText(deviceList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }
}
