package com.alexwbaule.lightcontrol.container;


import com.alexwbaule.lightcontrol.LightControl;
import com.alexwbaule.lightcontrol.R;

/**
 * Created by alex on 12/12/15.
 */
public class LightContainer {
    String name;
    String adrress;
    String statename;
    boolean state;
    boolean config;

    public LightContainer(String name, String adrress, boolean state, boolean config) {
        this.name = name;
        this.adrress = adrress;
        this.state = state;
        this.config = config;
        statename = LightControl.getInstance().getString(R.string.stateoff);
        if(state){
            statename = LightControl.getInstance().getString(R.string.stateon);
        }
    }

    public boolean isConfig() {
        return config;
    }

    public String getStatename() {
        return statename;
    }

    public String getName() {
        return name;
    }

    public String getAdrress() {
        return adrress;
    }

    public boolean isState() {
        return state;
    }
}
