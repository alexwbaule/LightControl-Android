package com.alexwbaule.lightcontrol.callback;

import com.alexwbaule.lightcontrol.container.LightContainer;

import java.util.ArrayList;

/**
 * Created by alex on 13/12/15.
 */
public interface LoadNodesListener {
    void onLoadNodesComplete(ArrayList<LightContainer> lgts);
}
