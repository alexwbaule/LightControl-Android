package com.alexwbaule.lightcontrol.container;

import com.alexwbaule.lightcontrol.LightControl;
import com.alexwbaule.lightcontrol.R;

/**
 * Created by alex on 12/12/15.
 */
public class LightContainer {
    String unique_name;
    String name;
    String adrress;
    String statename;
    int signal;
    boolean state;
    boolean config;

    public LightContainer(String unique_name, String name, String adrress, int sg) {
        this.unique_name = unique_name;
        this.name = name;
        this.adrress = adrress;
        this.signal = sg;
        this.statename = LightControl.getInstance().getString(R.string.stateoff);
        this.state = false;
        this.config = true;
    }

    public LightContainer(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LightContainer that = (LightContainer) o;

        if (unique_name.equals(that.unique_name)) return true;
        return false;
    }

    public boolean isConfig() {
        return config;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdrress(String adrress) {
        this.adrress = adrress;
    }

    public void setState(boolean state) {
        this.state = state;
        statename = LightControl.getInstance().getString(R.string.stateoff);
        if(state){
            statename = LightControl.getInstance().getString(R.string.stateon);
        }
    }

    public String getUnique_name() {
        return unique_name;
    }

    public void setUnique_name(String unique_name) {
        this.unique_name = unique_name;
    }

    public void setConfig(boolean config) {
        this.config = config;
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

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }
}
