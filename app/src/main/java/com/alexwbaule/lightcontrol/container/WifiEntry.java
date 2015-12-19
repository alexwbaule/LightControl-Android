package com.alexwbaule.lightcontrol.container;

/**
 * Created by alex on 17/12/15.
 */
public class WifiEntry {
    String ssid;
    String macaddr;
    int signal;

    public WifiEntry(String ssid, String macaddr, int signal) {
        this.ssid = ssid;
        this.macaddr = macaddr;
        this.signal = signal;
    }

    public String getSsid() {
        return ssid;
    }

    public String getMacaddr() {
        return macaddr;
    }

    public int getSignal() {
        return signal;
    }

}
