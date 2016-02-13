package com.alexwbaule.lightcontrol;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alexwbaule.lightcontrol.adapter.LightsAdapter;
import com.alexwbaule.lightcontrol.callback.LoadNodesListener;
import com.alexwbaule.lightcontrol.container.DeviceAddr;
import com.alexwbaule.lightcontrol.container.LightContainer;
import com.alexwbaule.lightcontrol.tasks.FindNodes;
import com.alexwbaule.lightcontrol.tasks.LoadNodes;
import com.alexwbaule.lightcontrol.tasks.ScanForNodes;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoadNodesListener, SwipeRefreshLayout.OnRefreshListener {
    ArrayList<LightContainer> lightContainers;
    private LightsAdapter adapter;
    private Snackbar snackbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private LightControl app;
    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        app = LightControl.getInstance();

        lightContainers = new ArrayList<>();
        lightContainers.addAll(app.getCacheDiscovery());
        adapter = new LightsAdapter(app, lightContainers, getFragmentManager());
        recyclerView = (RecyclerView) findViewById(R.id.lights_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        refreshLayout.setOnRefreshListener(this);

        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        new LoadNodes(this).execute(lightContainers);

        snackbar = Snackbar.make(recyclerView, "Atualizando lista de devices", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public void onRefresh() {
        new LoadNodes(this).execute(lightContainers);
    }


    public void updateSnackBar(final int txt){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), getResources().getQuantityString(R.plurals.found_device,txt,txt), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            snackbar = Snackbar.make(recyclerView, "Procurando por novos devices", Snackbar.LENGTH_INDEFINITE);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

            snackbar.show();
            Handler alex = new Handler();
            alex.postDelayed(new ScanForNodes(this), 15000);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadNodesComplete(ArrayList<LightContainer> lgts) {
        app.SaveCacheDiscovery(lgts);
        adapter.addData(lgts);
        snackbar.dismiss();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onFindNodesComplete(ArrayList<LightContainer> lgts) {
        app.SaveCacheDiscovery(lgts);
        adapter.addData(lgts);
        snackbar.dismiss();
    }

    @Override
    public void onStopDiscovery(ArrayList<DeviceAddr> deviceAddr) {
        new FindNodes(this).execute(deviceAddr);
    }

    @Override
    public void onResolveService(int id) {
        updateSnackBar(id);
    }


}
