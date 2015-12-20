package com.alexwbaule.lightcontrol.dns_sd;

import android.content.Context;
import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager;
import android.util.Log;
import com.alexwbaule.lightcontrol.Logger;
import com.alexwbaule.lightcontrol.callback.LoadNodesListener;
import com.alexwbaule.lightcontrol.container.DeviceAddr;

import java.util.ArrayList;

public class NsdHelper {
    private LoadNodesListener loadNodesListener;
    private NsdManager mNsdManager;
    private NsdManager.DiscoveryListener discoveryListener;
    private ArrayList<DeviceAddr> services;

    public static final String SERVICE_TYPE = "_light._tcp.";
    public static final String TAG = "NsdHelper";

    public NsdHelper(Context context, LoadNodesListener loadNodesListener) {
        this.loadNodesListener = loadNodesListener;
        mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        discoveryListener = new MyDiscoveryListener();
        services = new ArrayList<>();
    }

    private class MyDiscoveryListener implements NsdManager.DiscoveryListener{
        @Override
        public void onDiscoveryStarted(String regType) {
            Logger.log(TAG, "Service discovery started");
        }

        @Override
        public void onServiceFound(NsdServiceInfo service) {
            Logger.log(TAG, "Service discovery success all: " + service);
            mNsdManager.resolveService(service, new MyResolveListener());
        }

        @Override
        public void onServiceLost(NsdServiceInfo service) {
            Logger.log(TAG, "service lost" + service);
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            Log.i(TAG, "Discovery stopped: " + serviceType);
        }

        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            Logger.log(TAG, "Discovery failed: Error code:" + errorCode);
            mNsdManager.stopServiceDiscovery(this);
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            Logger.log(TAG, "Discovery failed: Error code:" + errorCode);
            mNsdManager.stopServiceDiscovery(this);
        }
    }

    private class MyResolveListener implements NsdManager.ResolveListener {
        @Override
        public void onResolveFailed(NsdServiceInfo serviceInfo, int errorCode) {
            Logger.log(TAG, "Service FAIL TO Discovered: " + serviceInfo.getServiceName() + " -> " + serviceInfo.getHost() + " ERROR IS " + errorCode);
            mNsdManager.resolveService(serviceInfo, new MyResolveListener());
        }

        @Override
        public void onServiceResolved(NsdServiceInfo serviceInfo) {
            DeviceAddr deviceAddr = new DeviceAddr(serviceInfo.getServiceName(), serviceInfo.getHost().getHostAddress(), serviceInfo.getPort());
            Logger.log(TAG, "Service Discovered: " + serviceInfo.getServiceName() + " -> " + serviceInfo.getHost());
            if(!services.contains(deviceAddr)) {
                services.add(deviceAddr);
            }
            if(loadNodesListener != null){
                loadNodesListener.onResolveService(services.size());
            }
        }
    }

    public void discoverServices() {
        mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public ArrayList<DeviceAddr> stopDiscovery() {
        mNsdManager.stopServiceDiscovery(discoveryListener);
        Logger.log(TAG, "Stop Service DIscovery Called services discovery count = " + services.size());
        return services;
    }
}