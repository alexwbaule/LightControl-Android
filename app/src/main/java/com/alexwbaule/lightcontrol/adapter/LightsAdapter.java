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
import com.alexwbaule.lightcontrol.container.LightContainer;

/**
 * Created by alex on 12/12/15.
 */
public class LightsAdapter extends RecyclerView.Adapter<LightsAdapter.LightsViewHolder> {
    private final Context ctx;
    private ArrayList<LightContainer> lightContainers;

    public LightsAdapter(Context ctx, ArrayList<LightContainer> lightContainers) {
        this.ctx = ctx;
        this.lightContainers = lightContainers;
    }

    @Override
    public LightsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View retView = LayoutInflater.from(ctx).inflate(R.layout.eachlight, parent, false);
        LightsViewHolder holder = new LightsViewHolder(retView);
        return holder;
    }

    @Override
    public void onBindViewHolder(LightsAdapter.LightsViewHolder holder, int position) {
        LightContainer lightContainer = lightContainers.get(position);
        holder.devname.setText(lightContainer.getName());
        holder.devstate.setChecked(lightContainer.isState());
        holder.devstate.setText(lightContainer.getStatename());
    }
     public void reloadData(ArrayList<LightContainer> lc){
         lightContainers.clear();
         lightContainers = lc;
         notifyDataSetChanged();
     }

    @Override
    public int getItemCount() {
        return lightContainers.size();
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
