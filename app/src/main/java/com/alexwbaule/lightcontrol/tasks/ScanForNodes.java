package com.alexwbaule.lightcontrol.tasks;

import com.alexwbaule.lightcontrol.LightControl;
import com.alexwbaule.lightcontrol.Logger;
import com.alexwbaule.lightcontrol.callback.LoadNodesListener;
import com.alexwbaule.lightcontrol.dns_sd.NsdHelper;


/**
 * Created by alex on 19/12/15.
 */
public class ScanForNodes implements Runnable {
    private static NsdHelper mNsdHelper;
    private LoadNodesListener loadNodesListener;

    public ScanForNodes(LoadNodesListener listener) {
        loadNodesListener = listener;
        mNsdHelper = new NsdHelper(LightControl.getInstance(), loadNodesListener);
        mNsdHelper.discoverServices();
    }

    @Override
    public void run() {
        if (loadNodesListener != null) {
            Logger.log("ScanForNodes", "Scan Finished");
            loadNodesListener.onStopDiscovery(mNsdHelper.stopDiscovery());
        }
    }
}
