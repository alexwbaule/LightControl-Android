package com.alexwbaule.lightcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.alexwbaule.lightcontrol.adapter.LightsAdapter;
import com.alexwbaule.lightcontrol.callback.LoadNodesListener;
import com.alexwbaule.lightcontrol.container.LightContainer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadNodesListener {
    ArrayList<LightContainer> lightContainers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightContainers = new ArrayList<>();
        lightContainers.add(new LightContainer("Sala", "192.168.250.104", true));
        lightContainers.add(new LightContainer("Sala 01", "192.168.250.104", false));
        lightContainers.add(new LightContainer("Sala 02", "192.168.250.158", false));
        lightContainers.add(new LightContainer("Sala 03", "192.168.250.154", false));

        RecyclerView rw = (RecyclerView) findViewById(R.id.lights_list);
        rw.setLayoutManager(new LinearLayoutManager(this));
        rw.setAdapter(new LightsAdapter(LightControl.getInstance(), lightContainers));
    }

    @Override
    protected void onResume() {
        super.onResume();
        LightControl.mNsdHelper.discoverServices();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LightControl.mNsdHelper.stopDiscovery();
    }

    @Override
    public void onLoadNodesComplete(ArrayList<LightContainer> lgts) {

    }
}
