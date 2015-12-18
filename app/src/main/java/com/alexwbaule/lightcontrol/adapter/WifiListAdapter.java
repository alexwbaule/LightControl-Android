package com.alexwbaule.lightcontrol.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexwbaule.lightcontrol.R;
import com.alexwbaule.lightcontrol.container.WifiEntry;

import java.util.ArrayList;

/**
 * Created by alex on 17/12/15.
 */
public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.WifiViewHolder> {
    private final Context ctx;
    private final FragmentManager fragmentManager;
    private ArrayList<WifiEntry> wifiEntries;

    public WifiListAdapter(FragmentManager fragmentManager, ArrayList<WifiEntry> wifiEntries, Context ctx) {
        this.fragmentManager = fragmentManager;
        this.wifiEntries = wifiEntries;
        this.ctx = ctx;
    }

    @Override
    public WifiListAdapter.WifiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View retView = LayoutInflater.from(ctx).inflate(R.layout.eachlight_v2, parent, false);
        WifiViewHolder holder = new WifiViewHolder(retView);
        return holder;
    }

    @Override
    public void onBindViewHolder(WifiListAdapter.WifiViewHolder holder, int position) {

    }

    public void replaceData(ArrayList<WifiEntry> wifiEnt){
        wifiEntries.clear();
        wifiEntries.addAll(wifiEnt);
        notifyDataSetChanged();
    }
    public void cleanData(){
        wifiEntries.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return wifiEntries.size();
    }


    public class WifiViewHolder extends RecyclerView.ViewHolder {

        public WifiViewHolder(View itemView) {
            super(itemView);
        }
    }
}