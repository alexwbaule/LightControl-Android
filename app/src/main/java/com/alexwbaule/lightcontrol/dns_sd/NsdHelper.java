package com.alexwbaule.lightcontrol.dns_sd;

import android.content.Context;
import android.net.nsd.NsdServiceInfo;
import android.net.nsd.NsdManager;
import android.util.Log;

import com.alexwbaule.lightcontrol.Logger;
import com.alexwbaule.lightcontrol.callback.LoadNodesListener;
import com.alexwbaule.lightcontrol.container.DeviceAddr;

public class NsdHelper {
    Context mContext;
    NsdManager mNsdManager;
    private NsdManager.DiscoveryListener discoveryListener;
    private LoadNodesListener loadNodesListener;

    public static final String SERVICE_TYPE = "_light._tcp.";
    public static final String TAG = "NsdHelper";

    public NsdHelper(Context context, LoadNodesListener loadNodesListener) {
        mContext = context;
        this.loadNodesListener = loadNodesListener;
        mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        discoveryListener = new MyDiscoveryListener();

    }

    private class MyDiscoveryListener implements NsdManager.DiscoveryListener{
        @Override
        public void onDiscoveryStarted(String regType) {
            Logger.log(TAG, "Service discovery started");
        }

        @Override
        public void onServiceFound(NsdServiceInfo service) {
            Logger.log(TAG, "Service discovery success all: " + service);
            if (service.getServiceType().equals(SERVICE_TYPE)) {
                mNsdManager.resolveService(service, new MyResolveListener());
            }
        }

        @Override
        public void onServiceLost(NsdServiceInfo service) {
            Logger.log(TAG, "service lost" + service);
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            Log.i(TAG, "Discovery stopped: " + serviceType);
            loadNodesListener.onStopDiscovery();
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
            //your code
        }

        @Override
        public void onServiceResolved(NsdServiceInfo serviceInfo) {
            DeviceAddr deviceAddr = new DeviceAddr(serviceInfo.getServiceName(), serviceInfo.getHost().getHostAddress(), serviceInfo.getPort());
            Logger.log(TAG, "Service Discovered: " + serviceInfo.getServiceName() + " -> " + serviceInfo.getHost());
            loadNodesListener.onDiscoveryNode(deviceAddr);
        }
    }

    public void discoverServices() {
        mNsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void stopDiscovery() {
        mNsdManager.stopServiceDiscovery(discoveryListener);
    }
}