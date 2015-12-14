package com.alexwbaule.lightcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNsdHelper = new NsdHelper(LightControl.getInstance(), this);
        mNsdHelper.initializeNsd();
        lightContainers = new ArrayList<>();
        adapter = new LightsAdapter(LightControl.getInstance(), lightContainers);
        RecyclerView rw = (RecyclerView) findViewById(R.id.lights_list);
        rw.setLayoutManager(new LinearLayoutManager(this));
        rw.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNsdHelper.discoverServices();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNsdHelper.stopDiscovery();
        adapter.cleanData();
    }

    @Override
    public void onLoadNodesComplete(LightContainer lgts) {
        adapter.addData(lgts);
    }

    @Override
    public void onDiscoveryNode(DeviceAddr deviceAddr) {
        new FindNodes(this).execute(deviceAddr);
    }

    @Override
    public void onStopDiscovery() {
    }
}
