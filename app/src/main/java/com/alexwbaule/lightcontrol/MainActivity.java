package com.alexwbaule.lightcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.alexwbaule.lightcontrol.adapter.LightsAdapter;
import com.alexwbaule.lightcontrol.callback.LoadNodesListener;
import com.alexwbaule.lightcontrol.container.DeviceAddr;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.dns_sd.NsdHelper;
import com.alexwbaule.lightcontrol.tasks.FindNodes;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadNodesListener {
    ArrayList<LightContainer> lightContainers;
    public static NsdHelper mNsdHelper;
    private LightsAdapter adapter;
    private ProgressBar pgbar;

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNsdHelper = new NsdHelper(LightControl.getInstance(), this);
        mNsdHelper.discoverServices();

        lightContainers = new ArrayList<>();
        adapter = new LightsAdapter(LightControl.getInstance(), lightContainers);
        RecyclerView rw = (RecyclerView) findViewById(R.id.lights_list);
        rw.setLayoutManager(new LinearLayoutManager(this));
        rw.setAdapter(adapter);

        pgbar = (ProgressBar) findViewById(R.id.progressBar);
        pgbar.setIndeterminate(true);
        pgbar.setContentDescription("Carrregando Devices");
        pgbar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        mNsdHelper.stopDiscovery();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onLoadNodesComplete(LightContainer lgts) {
        pgbar.setVisibility(View.INVISIBLE);
        adapter.addData(lgts);
    }

    @Override
    public void onDiscoveryNode(DeviceAddr deviceAddr) {
        Log.e(TAG, "Device Discovered: " + deviceAddr.getName() + " -> " + deviceAddr.getIpAddr());
        new FindNodes(this).execute(deviceAddr);
    }

    @Override
    public void onStopDiscovery() {
    }
}
