package com.alexwbaule.lightcontrol.callback;

import com.alexwbaule.lightcontrol.container.LightContainer;

/**
 * Created by alex on 15/12/15.
 */
public interface LoadLightResult {
    void onLightsOk(LightContainer lightContainers);
}
