package com.alexwbaule.lightcontrol.callback;

import com.alexwbaule.lightcontrol.container.DeviceAddr;
import com.alexwbaule.lightcontrol.container.LightContainer;

import java.util.ArrayList;

/**
 * Created by alex on 13/12/15.
 */
public interface LoadNodesListener {
    void onFindNodesComplete(ArrayList<LightContainer> lgts);
    void onLoadNodesComplete(ArrayList<LightContainer> lgts);
    void onStopDiscovery(ArrayList<DeviceAddr> deviceAddr);
    void onResolveService(int id);
}