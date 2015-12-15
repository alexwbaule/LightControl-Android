package com.alexwbaule.lightcontrol.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import com.alexwbaule.lightcontrol.R;
import com.alexwbaule.lightcontrol.callback.GetFromVolley;
import com.alexwbaule.lightcontrol.callback.LoadLightResult;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.network.VolleySingleton;
import com.alexwbaule.lightcontrol.tasks.ControlLights;
import com.android.volley.RequestQueue;

/**
 * Created by alex on 12/12/15.
 */
public class LightsAdapter extends RecyclerView.Adapter<LightsAdapter.LightsViewHolder> implements LoadLightResult{
    private final Context ctx;
    private ArrayList<LightContainer> lightContainers;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public LightsAdapter(Context ctx, ArrayList<LightContainer> lightContainers) {
        this.ctx = ctx;
        this.lightContainers = lightContainers;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
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
        holder.devname.setText(lightContainer.getName() + " - " + lightContainer.getAdrress());
        holder.devstate.setChecked(lightContainer.isState());
        holder.devstate.setText(lightContainer.getStatename());
        holder.devstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ControlLights().execute(lightContainer);
            }
        });
    }

    public void addData(LightContainer lightContainer){
        Log.d("Adapter","Adicionando: " + lightContainer.getName() + " -> " + lightContainer.getAdrress());
        if(!lightContainers.contains(lightContainer)) {
            Log.d("Adapter","Nao existe entrada, adicionando.");
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
    public void onLightsOk(LightContainer lightContainers) {

    }

    public class LightsViewHolder extends RecyclerView.ViewHolder{
        TextView devname;
        Switch devstate;

        public LightsViewHolder(View itemView) {
            super(itemView);
            devname = (TextView) itemView.findViewById(R.id.devname);
            devstate = (Switch) itemView.findViewById(R.id.devstate);
        }
    }
}
