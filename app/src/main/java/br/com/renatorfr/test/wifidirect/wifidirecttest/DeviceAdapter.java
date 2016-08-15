package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private static final String LOG_TAG = "WIFIP2P_DEVICE_ADAP";
    private List<WifiP2pDevice> deviceList;
    private RecyclerView recyclerView;
    private WifiP2PHelper helper;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView deviceName;

        public ViewHolder(TextView textView) {
            super(textView);
            deviceName = textView;
        }
    }

    public DeviceAdapter(WifiP2pDeviceList deviceList, RecyclerView recyclerView, WifiP2PHelper helper) {
        this.deviceList = new ArrayList<>(deviceList.getDeviceList());
        this.recyclerView = recyclerView;
        this.helper = helper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setPadding(5, 5, 5, 5);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = recyclerView.getChildAdapterPosition(view);
                if (pos >= 0 && pos < getItemCount()) {
                    Log.i(LOG_TAG, deviceList.get(pos).toString());
                    helper.connectTo(deviceList.get(pos));
                }
            }
        });

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
