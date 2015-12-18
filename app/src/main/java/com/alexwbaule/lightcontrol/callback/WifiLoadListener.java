package com.alexwbaule.lightcontrol.callback;

import com.alexwbaule.lightcontrol.container.WifiEntry;

import java.util.ArrayList;

/**
 * Created by alex on 17/12/15.
 */
public interface WifiLoadListener {
    void onLoadComplete(ArrayList<WifiEntry> wifiEntries);
}
