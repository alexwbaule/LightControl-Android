package com.alexwbaule.lightcontrol.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexwbaule.lightcontrol.LightControl;
import com.alexwbaule.lightcontrol.R;
import com.alexwbaule.lightcontrol.container.LightContainer;

/**
 * Created by alex on 15/12/15.
 */
public class ConfigDevice extends DialogFragment {
    private String name;
    private String addr;

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

        View myView = getActivity().getLayoutInflater().inflate(R.layout.config_wifi, null);


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
}
