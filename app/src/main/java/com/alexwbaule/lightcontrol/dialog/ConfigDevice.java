package com.alexwbaule.lightcontrol.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.alexwbaule.lightcontrol.LightControl;
import com.alexwbaule.lightcontrol.R;
import com.alexwbaule.lightcontrol.adapter.WifiListAdapter;
import com.alexwbaule.lightcontrol.callback.WifiLoadListener;
import com.alexwbaule.lightcontrol.container.WifiConf;
import com.alexwbaule.lightcontrol.container.WifiEntry;
import com.alexwbaule.lightcontrol.tasks.FindWifiList;
import com.alexwbaule.lightcontrol.tasks.SaveWifiConf;

import java.util.ArrayList;

/**
 * Created by alex on 15/12/15.
 */
public class ConfigDevice extends DialogFragment implements WifiLoadListener{
    private String name;
    private String addr;
    private WifiListAdapter adapter;
    private ProgressBar pgbar;
    private TextView ssid;
    private TextView passwd;
    private TextView devname;



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
        adapter = new WifiListAdapter(getActivity(),0, new ArrayList<WifiEntry>());

        View myView = getActivity().getLayoutInflater().inflate(R.layout.config_wifi, null);
        View myHeader = getActivity().getLayoutInflater().inflate(R.layout.config_wifi_title, null);


        TextView txt = (TextView) myHeader.findViewById(R.id.wifi_title);

        txt.setText("Configurações de Wifi (" + name + ")");

        Spinner rcview = (Spinner) myView.findViewById(R.id.wifi_list);
        rcview.setAdapter(adapter);

        ssid = (TextView) myView.findViewById(R.id.device_ssid);
        passwd = (TextView) myView.findViewById(R.id.device_passwd);
        devname = (TextView) myView.findViewById(R.id.device_name);



        rcview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    WifiEntry entry = (WifiEntry) parent.getSelectedItem();
                    if (position > 0)
                        ssid.setText(entry.getSsid());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pgbar = (ProgressBar) myView.findViewById(R.id.wifipbar);
        pgbar.setVisibility(View.VISIBLE);

        new FindWifiList(this).execute(addr);


        AlertDialog.Builder b = new AlertDialog.Builder(getActivity())
                .setCustomTitle(myHeader)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        this.doPositiveClick();
                        new SaveWifiConf().execute(new WifiConf(addr,devname.getText().toString(), ssid.getText().toString(), passwd.getText().toString()));
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
