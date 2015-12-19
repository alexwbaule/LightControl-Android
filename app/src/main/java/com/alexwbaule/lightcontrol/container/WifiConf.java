package com.alexwbaule.lightcontrol.container;

/**
 * Created by alex on 19/12/15.
 */
public class WifiConf {
    String name;
    String ssid;
    String passwd;
    String addr;

    public WifiConf(String addr, String name, String ssid, String passwd) {
        this.addr = addr;
        this.name = name;
        this.ssid = ssid;
        this.passwd = passwd;
    }

    public String getAddr() {
        return addr;
    }

    public String getName() {
        return name;
    }

    public String getSsid() {
        return ssid;
    }

    public String getPasswd() {
        return passwd;
    }
}
