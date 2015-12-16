package com.alexwbaule.lightcontrol.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
            holder.devname.setText(lightContainer.getName());
            holder.devstate.setChecked(lightContainer.isState());
            if(lightContainer.isState()){
                holder.light.setImageResource(R.drawable.light_bulb_on);
            }else{
                holder.light.setImageResource(R.drawable.light_bulb_off);
            }
            holder.devstate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(lightContainer.isState()){
                        holder.light.setImageResource(R.drawable.light_bulb_turn_off);
                    }else{
                        holder.light.setImageResource(R.drawable.light_bulb_turn_on);
                    }
                    runExec(lightContainer);
                }
            });
        }else{
            holder.devname.setText(lightContainer.getName());
            holder.devstate.setBackgroundResource(R.drawable.toggleconfigbutton);
            holder.light.setImageResource(R.drawable.light_bulb_off);
            holder.devstate.setOnClickListener(new View.OnClickListener() {
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
        }
    }
    private void runExec(LightContainer lightContainer){
        new ControlLights(this).execute(lightContainer);
    }

    public void addData(LightContainer lightContainer){
        Logger.log("Adapter", "Adicionando: " + lightContainer.getName() + " -> " + lightContainer.getAdrress());
        if(!lightContainers.contains(lightContainer)) {
            Logger.log("Adapter", "Nao existe entrada, adicionando.");
            lightContainers.add(lightContainer);
            notifyDataSetChanged();
        }
    }
    public void cleanData(){
         lightContainers.clear();
         notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lightContainers.size();
    }

    @Override
    public void onLightsOk(LightContainer lct) {
        lightContainers.set(lightContainers.indexOf(lct), lct);
        Logger.log("Adapter", "Ok, atualizando item " + lct.toString());
        notifyDataSetChanged();
    }

    public class LightsViewHolder extends RecyclerView.ViewHolder{
        TextView devname;
        ToggleButton devstate;
        ImageView light;

        public LightsViewHolder(View itemView) {
            super(itemView);
            devname = (TextView) itemView.findViewById(R.id.devname);
            devstate = (ToggleButton) itemView.findViewById(R.id.devstate);
            light = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
