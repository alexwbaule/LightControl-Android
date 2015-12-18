package com.alexwbaule.lightcontrol.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.alexwbaule.lightcontrol.LightControl;
import com.alexwbaule.lightcontrol.R;
import com.alexwbaule.lightcontrol.adapter.WifiListAdapter;
import com.alexwbaule.lightcontrol.callback.WifiLoadListener;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.container.WifiEntry;
import com.alexwbaule.lightcontrol.tasks.FindWifiList;

import java.util.ArrayList;

/**
 * Created by alex on 15/12/15.
 */
public class ConfigDevice extends DialogFragment implements WifiLoadListener{
    private String name;
    private String addr;
    private WifiListAdapter adapter;
    private ProgressBar pgbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(LightControl.NAME);
            addr = getArguments().getString(LightControl.ADDR);
        }
    }

    public ConfigDevice() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        adapter = new WifiListAdapter(new ArrayList<WifiEntry>(), getActivity());

        View myView = getActivity().getLayoutInflater().inflate(R.layout.config_wifi, null);

        RecyclerView rcview = (RecyclerView) myView.findViewById(R.id.wifi_list);
        rcview.setLayoutManager(new LinearLayoutManager(getActivity()));
        rcview.setAdapter(adapter);

        pgbar = (ProgressBar) myView.findViewById(R.id.wifipbar);
        pgbar.setVisibility(View.VISIBLE);

        new FindWifiList(this).execute(addr);

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setTitle("Configurações de Wifi ("+ name +")")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        this.doPositiveClick();
                    }

                    private void doPositiveClick() {

                    }
                });
        b.setView(myView);
        return b.create();
    }

    @Override
    public void onLoadComplete(ArrayList<WifiEntry> wifiEntries) {
        pgbar.setVisibility(View.INVISIBLE);
        adapter.replaceData(wifiEntries);
    }
}
