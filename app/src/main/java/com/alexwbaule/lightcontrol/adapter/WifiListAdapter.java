package com.alexwbaule.lightcontrol.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexwbaule.lightcontrol.LightControl;
import com.alexwbaule.lightcontrol.R;
import com.alexwbaule.lightcontrol.container.WifiEntry;

import java.util.ArrayList;

/**
 * Created by alex on 17/12/15.
 */
public class WifiListAdapter extends ArrayAdapter<WifiEntry> {
    private final Context ctx;
    private LayoutInflater inflater;
    private ArrayList<WifiEntry> wifiEntries;
    private ImageView signal;
    private TextView name;
    private TextView mac;
    private View itemView;
    private boolean reload = false;

    public WifiListAdapter(Context context, int resource, ArrayList<WifiEntry> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.wifiEntries = objects;
        wifiEntries.add(new WifiEntry(null,null,0));
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View retView, ViewGroup parent) {
        WifiEntry wifiEntry = wifiEntries.get(position);
        if(wifiEntry.getSsid() == null){
            itemView = inflater.inflate(R.layout.loading, parent, false);
            if(reload) {
                name = (TextView) itemView.findViewById(R.id.msg_loading);
                name.setText("Escolha o wifi");
            }
        }else {
            itemView = inflater.inflate(R.layout.eachwifi, parent, false);

            name = (TextView) itemView.findViewById(R.id.ssid_wifi);
            mac = (TextView) itemView.findViewById(R.id.mac_wifi);
            signal = (ImageView) itemView.findViewById(R.id.signal_wifi);

            name.setText(wifiEntry.getSsid());
            mac.setText(wifiEntry.getMacaddr());

            signal.setImageResource(LightControl.getInstance().getWifiSignalImage(wifiEntry.getSignal()));
        }
        return itemView;
    }

    public void replaceData(ArrayList<WifiEntry> wifiEnt){
        //wifiEntries.clear();
        wifiEntries.addAll(wifiEnt);
        reload = true;
        notifyDataSetChanged();
    }
}