package com.example.raghvendrat.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;

import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ListView;
import java.util.List;

import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.content.BroadcastReceiver;

import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import lecho.lib.hellocharts.view.AbstractChartView;
import lecho.lib.hellocharts.view.ColumnChartView;



public class MainActivityNew extends AppCompatActivity {

    public String ssid;
    public String mac;
    WifiManager mainWifi;
    List<ScanResult> wifi_list;
    WifiReceiver receiver;
    String wifis[];
    List<Network> myNets = new ArrayList<Network>();

    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_new);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }


        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new PagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        mainWifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        receiver = new WifiReceiver();
        mainWifi.startScan();

    }

    protected void onPause(){                                           //onPause  broadcast method
        unregisterReceiver(receiver);
        super.onPause();
    }

    protected void onResume(){                                          //onResume broadcst method
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    private class WifiReceiver extends BroadcastReceiver{                                      // Broadcast Receiver used to start scanning
        @Override
        public void onReceive(Context c, Intent intent) {
            Log.d("TAG","On recieve called");
            wifi_list = mainWifi.getScanResults();
            wifis = new String[wifi_list.size()];

            for(int i = 0; i < wifi_list.size(); i++){
                myNets.add(new Network(wifi_list.get(i).SSID, wifi_list.get(i).level, getIconfromRssi(wifi_list.get(i).level), getChannelfromfreq(wifi_list.get(i).frequency ), wifi_list.get(i).BSSID));   // { SSID ,RSSI,Icon_ID, Mac_add }
            }

            populateListView();
            Log.d("TAG","Wifi details called");
        }
    }

    private void populateListView() {
        ArrayAdapter<Network> adapter = new NetworkAdapter(this, R.layout.item_view, myNets );
        ListView list = (ListView) findViewById(R.id.netListView);
        list.setAdapter(adapter);
        Log.d("TAG", "ListView details called");
    }

    public String getChannelfromfreq(int freq){

        if (freq == 2412)
            return "CH: 1";
        else if( freq == 2417)
            return "CH: 2";
        else if(freq == 2422)
            return "CH: 3";
        else if (freq == 2427)
            return "CH: 4";
        else if (freq == 2432)
            return "CH: 5";
        else if (freq == 2437)
            return "CH: 6";
        else if (freq == 2442)
            return "CH: 7";
        else if (freq == 2447)
            return "CH: 8";
        else if (freq == 2452)
            return "CH: 9";
        else if (freq == 2457)
            return "CH: 10";
        else if (freq == 2462)
            return "CH: 11";
        else if (freq == 2467)
            return "CH: 12";
        else if (freq == 2472)
            return "CH: 13";
        else if (freq == 2484)
            return "CH: 14";
        else
            return "CH: 0";
    }

    public int getIconfromRssi(int level) {

        if (level >= -55)
            return R.drawable.a_3;                   //a_3
        else if( level >= -70 && level <-55)
            return R.drawable.a_2;                  //a_2
        else if( level >= -100 && level < -70 )
            return R.drawable.a_1;                 //a_1
        else
            return R.drawable.a_0;                 //a_0
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            //noinspection SimplifiableIfStatement
            case R.id.action_settings: {

                Toast.makeText(this, "Action Settings selected", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.action_about: {
                Intent intent = new Intent(this , AboutActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.action_enable: {
                Switch switchForActionBar = (Switch)findViewById(R.id.switchForActionBar);
                switchForActionBar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {

                            toggleWifi(true);
                            Toast.makeText(getApplicationContext(), "Wifi is Enabled", Toast.LENGTH_SHORT).show();
                        } else {
                            toggleWifi(false);
                            Toast.makeText(getApplicationContext(), "Wifi is Disabled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;
            }

            case R.id.action_refresh: {
                Toast.makeText(this, "Action refresh selected", Toast.LENGTH_SHORT).show();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }
    public enum ChartType{
        COLUMN_CHART;
    }
    public void toggleWifi(boolean status){
        WifiManager mainWifi = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        if(status == true && !mainWifi.isWifiEnabled()) {
            mainWifi.setWifiEnabled(true);
        }
        else if(status == false && mainWifi.isWifiEnabled() ){
            mainWifi.setWifiEnabled(false);
        }
    }


    /*** A placeholder fragment containing a simple view.*/

    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemClickListener {

        private ListView listView;
        private ChartSamplesAdapter adapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            listView = (ListView) rootView.findViewById(android.R.id.list);
            adapter = new ChartSamplesAdapter(getActivity(), 0, generateSamplesDescriptions());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            return rootView;
        }

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            Intent intent;
            intent = new Intent(getActivity(), ColumnChartActivity.class);
            startActivity(intent);
        }

        private List<ChartSampleDescription> generateSamplesDescriptions() {
            List<ChartSampleDescription> list = new ArrayList<ChartSampleDescription>();

            list.add(new ChartSampleDescription("Column Chart", "", ChartType.COLUMN_CHART));

            return list;
        }
    }

    public static class ChartSamplesAdapter extends ArrayAdapter<ChartSampleDescription> {

        public ChartSamplesAdapter(Context context, int resource, List<ChartSampleDescription> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.list_item_sample, null);

                holder = new ViewHolder();
                holder.text1 = (TextView) convertView.findViewById(R.id.text1);
                holder.text2 = (TextView) convertView.findViewById(R.id.text2);
                holder.chartLayout = (FrameLayout) convertView.findViewById(R.id.chart_layout);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            ChartSampleDescription item = getItem(position);

            holder.chartLayout.setVisibility(View.VISIBLE);
            holder.chartLayout.removeAllViews();

            AbstractChartView chart;

            switch (item.chartType) {
                case COLUMN_CHART:
                    chart = new ColumnChartView(getContext());
                    holder.chartLayout.addView(chart);
                    break;
                default:
                    chart = null;
                    holder.chartLayout.setVisibility(View.GONE);
                    break;
            }

            if (null != chart) {
                chart.setInteractive(false);// Disable touch handling for chart on the ListView.
            }
            holder.text1.setText(item.text1);
            holder.text2.setText(item.text2);

            return convertView;
        }

        private class ViewHolder {

            TextView text1;
            TextView text2;
            FrameLayout chartLayout;
        }

    }

    public static class ChartSampleDescription {
        String text1;
        String text2;
        ChartType chartType;

        public ChartSampleDescription(String text1, String text2, ChartType chartType) {
            this.text1 = text1;
            this.text2 = text2;
            this.chartType = chartType;
        }
    }

}
