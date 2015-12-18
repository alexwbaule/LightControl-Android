package com.alexwbaule.lightcontrol.container;

/**
 * Created by alex on 17/12/15.
 */
public class WifiEntry {
    String ssid;
    String macaddr;
    String signal;

    public WifiEntry(String ssid, String macaddr, String signal) {
        this.ssid = ssid;
        this.macaddr = macaddr;
        this.signal = signal;
    }

    public WifiEntry() {
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getMacaddr() {
        return macaddr;
    }

    public void setMacaddr(String macaddr) {
        this.macaddr = macaddr;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }
}
