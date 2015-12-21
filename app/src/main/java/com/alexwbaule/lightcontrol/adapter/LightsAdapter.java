package com.alexwbaule.lightcontrol.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;

import com.alexwbaule.lightcontrol.LightControl;
import com.alexwbaule.lightcontrol.Logger;
import com.alexwbaule.lightcontrol.R;
import com.alexwbaule.lightcontrol.callback.LoadLightResult;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.dialog.ConfigDevice;
import com.alexwbaule.lightcontrol.tasks.ControlLights;

/**
 * Created by alex on 12/12/15.
 */
public class LightsAdapter extends RecyclerView.Adapter<LightsAdapter.LightsViewHolder> implements LoadLightResult {
    private final Context ctx;
    private final FragmentManager fragmentManager;
    private ArrayList<LightContainer> lightContainers;
    private AnimationDrawable wifiAnime;

    public LightsAdapter(Context ctx, ArrayList<LightContainer> lightContainers, FragmentManager frmt) {
        this.ctx = ctx;
        this.fragmentManager= frmt;
        this.lightContainers = lightContainers;
    }

    @Override
    public LightsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View retView = LayoutInflater.from(ctx).inflate(R.layout.eachlight, parent, false);
        LightsViewHolder holder = new LightsViewHolder(retView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final LightsAdapter.LightsViewHolder holder, int position) {
        final LightContainer lightContainer = lightContainers.get(position);
        if (lightContainer.isConfig()) {
            holder.devstate.setChecked(lightContainer.isState());
            if(lightContainer.isState()){
                holder.lightstate.setImageResource(R.drawable.light_bulb_on);
            }else{
                holder.lightstate.setImageResource(R.drawable.light_bulb_off);
            }
            holder.light.setImageResource(LightControl.getInstance().getWifiSignalImage(lightContainer.getSignal()));

            holder.devstate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.lightstate.setBackgroundResource(R.drawable.animation_light);
                    wifiAnime = (AnimationDrawable) holder.lightstate.getBackground();
                    wifiAnime.start();
                    runExec(lightContainer);
                }
            });
        }else{
            holder.devstate.setBackgroundResource(R.drawable.togglebuttonoff);
            holder.light.setImageResource(R.drawable.mini_light_bulb_off);
        }
        holder.devname.setText(lightContainer.getName());
        holder.devconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(LightControl.NAME, lightContainer.getUnique_name());
                bundle.putString(LightControl.ADDR, lightContainer.getAdrress());
                ConfigDevice exportFragment = new ConfigDevice();
                exportFragment.setArguments(bundle);
                exportFragment.show(fragmentManager, "export");
            }
        });
        holder.lightstate.setBackgroundResource(0);
    }
    private void runExec(LightContainer lightContainer){
        new ControlLights(this).execute(lightContainer);
    }

    public void addData(ArrayList<LightContainer> lightContainer){
        Logger.log("Adapter", "Nao existe entrada, adicionando.");
        cleanData();
        lightContainers.addAll(lightContainer);
        notifyDataSetChanged();
    }
    public void cleanData(){
         lightContainers.clear();
    }

    @Override
    public int getItemCount() {
        return lightContainers.size();
    }

    @Override
    public void onLightsOk(LightContainer lct) {
        if (wifiAnime != null)
            wifiAnime.stop();
        lightContainers.set(lightContainers.indexOf(lct), lct);
        Logger.log("Adapter", "Ok, atualizando item " + lct.toString());
        notifyDataSetChanged();
    }

    public class LightsViewHolder extends RecyclerView.ViewHolder{
        TextView devname;
        ToggleButton devstate;
        ImageButton devconfig;
        ImageView lightstate;
        ImageView light;

        public LightsViewHolder(View itemView) {
            super(itemView);
            devname = (TextView) itemView.findViewById(R.id.devname);
            devconfig = (ImageButton) itemView.findViewById(R.id.config);
            devstate = (ToggleButton) itemView.findViewById(R.id.devstate);
            lightstate = (ImageView) itemView.findViewById(R.id.lightstate);
            light = (ImageView) itemView.findViewById(R.id.devsignal);
        }
    }
}
