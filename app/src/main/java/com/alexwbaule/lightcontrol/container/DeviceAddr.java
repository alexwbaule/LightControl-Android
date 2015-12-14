package com.alexwbaule.lightcontrol.container;

/**
 * Created by alex on 12/12/15.
 */
public class DeviceAddr {
    String ipAddr;
    int port;

    public DeviceAddr(String ipAddr, int port) {
        this.ipAddr = ipAddr;
        this.port = port;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public int getPort() {
        return port;
    }
}
