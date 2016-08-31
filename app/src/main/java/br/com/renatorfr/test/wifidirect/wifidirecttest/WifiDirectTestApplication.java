package br.com.renatorfr.test.wifidirect.wifidirecttest;

import android.app.Application;

public class WifiDirectTestApplication extends Application {
    private WifiP2PHelper helper;

    public WifiP2PHelper getHelper() {
        return helper;
    }

    public void setHelper(WifiP2PHelper helper) {
        this.helper = helper;
    }
}
