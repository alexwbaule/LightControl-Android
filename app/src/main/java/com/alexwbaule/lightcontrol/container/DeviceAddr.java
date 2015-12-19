package com.alexwbaule.lightcontrol.container;

/**
 * Created by alex on 12/12/15.
 */
public class DeviceAddr {
    String name;
    String ipAddr;
    int port;

    public DeviceAddr(String name, String ipAddr, int port) {
        this.name = name;
        this.ipAddr = ipAddr;
        this.port = port;
    }

    public String getName(){
        return name;
    }

    public String getIpAddr() {
        return ipAddr;
    }
}
