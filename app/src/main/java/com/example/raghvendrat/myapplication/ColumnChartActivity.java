package com.example.raghvendrat.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class ColumnChartActivity extends ActionBarActivity {

    int one_55 = 0;     int one_65 = 0;     int one_80 = 0;
    int two_55 = 0;     int two_65 = 0;     int two_80 = 0;
    int three_55 = 0;   int three_65 = 0;   int three_80 = 0;
    int four_55 = 0;    int four_65 = 0;    int four_80 = 0;
    int five_55 = 0;    int five_65 = 0;    int five_80 = 0;
    int six_55 = 0;     int six_65 = 0;     int six_80 = 0;
    int seven_55 = 0;   int seven_65 = 0;   int seven_80 = 0;
    int eight_55 = 0;   int eight_65 = 0;   int eight_80 = 0;
    int nine_55 = 0;    int nine_65 = 0;    int nine_80 = 0;
    int ten_55 = 0;     int ten_65 = 0;     int ten_80 = 0;
    int eleven_55 = 0;  int eleven_65 = 0;  int eleven_80 = 0;
    int twelve_55 = 0;  int twelve_65 = 0;  int twelve_80 = 0;
    int thirteen_55=0;  int thirteen_65=0;  int thirteen_80=0;

    List<ScanResult> wifi_list;
    WifiManager mainWifi;
    WifiReceiver receiver;
    HashMap<String, Integer> map = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_column_chart);
        mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        receiver = new WifiReceiver();
        startScan();

        Log.d("Testing", "Checkpoint 1");
     }

    public void startScan(){
        mainWifi.startScan();
    }

    protected void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }
    protected void onResume() {
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }

    private class WifiReceiver extends BroadcastReceiver {
        public void onReceive(Context c, Intent i) {
            Log.d("TAG", "On recieve called");
            resetCounter();
            wifi_list = mainWifi.getScanResults();
            Find_no_AP();
            pop();
            refreshGraphFragment();
        }
    }

    public void resetCounter(){
        one_55 = 0;     one_65 = 0;      one_80 = 0;
        two_55 = 0;     two_65 = 0;      two_80 = 0;
        three_55 = 0;   three_65 = 0;    three_80 = 0;
        four_55 = 0;    four_65 = 0;     four_80 = 0;
        five_55 = 0;    five_65 = 0;     five_80 = 0;
        six_55 = 0;     six_65 = 0;      six_80 = 0;
        seven_55 = 0;   seven_65 = 0;    seven_80 = 0;
        eight_55 = 0;   eight_65 = 0;    eight_80 = 0;
        nine_55 = 0;    nine_65 = 0;     nine_80 = 0;
        ten_55 = 0;     ten_65 = 0;      ten_80 = 0;
        eleven_55 = 0;  eleven_65 = 0;   eleven_80 = 0;
        twelve_55 = 0;  twelve_65 = 0;   twelve_80 = 0;
        thirteen_55=0;  thirteen_65=0;   thirteen_80=0;
    }

    public void refreshGraphFragment(){
        Fragment currFrag = this.getSupportFragmentManager().findFragmentById(R.id.container);
        if (currFrag!=null) {
            getSupportFragmentManager().beginTransaction().detach(currFrag).attach(currFrag).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
        }
    }

    public void pop() {

        map.put("1_55", one_55);       map.put("1_65", one_65);       map.put("1_80", one_80);
        map.put("2_55", two_55);       map.put("2_65", two_65);       map.put("2_80", two_80);
        map.put("3_55", three_55);     map.put("3_65", three_65);     map.put("3_80", three_80);
        map.put("4_55", four_55);      map.put("4_65", four_65);      map.put("4_80", four_80);
        map.put("5_55", five_55);      map.put("5_65", five_65);      map.put("5_80", five_80);
        map.put("6_55", six_55);       map.put("6_65", six_65);       map.put("6_80", six_80);
        map.put("7_55", seven_55);     map.put("7_65", seven_65);     map.put("7_80", seven_80);
        map.put("8_55", eight_55);     map.put("8_65", eight_65);     map.put("8_80", eight_80);
        map.put("9_55", nine_55);      map.put("9_65", nine_65);      map.put("9_80", nine_80);
        map.put("10_55", ten_55);      map.put("10_65", ten_65);      map.put("10_80", ten_80);
        map.put("11_55", eleven_55);   map.put("11_65", eleven_65);   map.put("11_80", eleven_80);
        map.put("12_55", twelve_55);   map.put("12_65", twelve_65);   map.put("12_80", twelve_80);
        map.put("13_55", thirteen_55); map.put("13_65", thirteen_65); map.put("13_80", thirteen_80);

        for (String key : map.keySet())
            System.out.println(key + ":" + map.get(key));
    }

    public String getChannelfromfreq(int freq) {

        if (freq == 2412)
            return "CH: 1";
        else if (freq == 2417)
            return "CH: 2";
        else if (freq == 2422)
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
        else
            return "CH: 0";
    }

    public int getAPfromRssi(int level) {
        if (level >= -55)
            return -55;                    //a_1
        else if (level >= -65 && level < -55)
            return -65;                    //a_2
        else if (level >= -100 && level < -65)
            return -80;                    //a_3
        else
            return 0;                    //a_0
    }

    public void Find_no_AP() {
        String channelno;
        int dB;

        for (int i = 0; i < wifi_list.size(); i++) {

            int freq = wifi_list.get(i).frequency;
            int level = wifi_list.get(i).level;
            channelno = getChannelfromfreq(freq);
            dB = getAPfromRssi(level);

            switch (channelno) {

                case "CH: 1":switch (dB){ case -55: one_55++;
                                                  break;
                                          case -65: one_65++;
                                                  break;
                                          case -80: one_80++;
                                                  break;
                                        }
                                        break;

                case "CH: 2":switch (dB){ case -55: two_55++;
                                                   break;
                                          case -65: two_65++;
                                                   break;
                                          case -80: two_80++;
                                                   break;
                                        }
                                        break;

                case "CH: 3":switch (dB){ case -55: three_55++;
                                                  break;
                                          case -65: three_65++;
                                                   break;
                                          case -80:three_80++;
                                                   break;
                                        }
                                        break;

                case "CH: 4":switch (dB) {case -55:four_55++;
                                                   break;
                                         case -65:four_65++;
                                                    break;
                                        case -80:four_80++;
                                                    break;
                                         }
                                        break;


                case "CH: 5":switch (dB){ case -55:five_55++;
                                                    break;
                                         case -65:five_65++;
                                                    break;
                                         case -80:five_80++;
                                                    break;
                                        }
                                        break;

                case "CH: 6":switch (dB) {case -55:six_55++;
                                                    break;
                                          case -65: six_65++;
                                                    break;
                                          case -80: six_80++;
                                                     break;
                                         }
                                        break;

                case "CH: 7":switch (dB) { case -55:seven_55++;
                                                    break;
                                           case -65:seven_65++;
                                                    break;
                                           case -80:seven_80++;
                                                     break;
                                            }
                                            break;

                case "CH: 8":switch (dB) { case -55:eight_55++;
                                                    break;
                                           case -65:eight_65++;
                                                    break;
                                            case -80:eight_80++;
                                                    break;
                                            }
                                            break;

                case "CH: 9":switch (dB) { case -55: nine_55++;
                                                    break;
                                           case -65:nine_65++;
                                                     break;
                                           case -80: nine_80++;
                                                      break;
                                        }
                                        break;

                case "CH: 10":switch (dB) {case -55:ten_55++;
                                                    break;
                                           case -65:ten_65++;
                                                      break;
                                           case -80:ten_80++;
                                                     break;
                                        }
                                        break;

                case "CH: 11":switch (dB) {case -55:eleven_55++;
                                                break;
                                            case -65:eleven_65++;
                                                break;
                                            case -80:eleven_80++;
                                                break;
                                         }
                                         break;

                case "CH: 12":switch (dB) { case -55: twelve_55++;
                                                     break;
                                            case -65: twelve_65++;
                                                      break;
                                            case -80: twelve_80++;
                                                      break;
                                            }
                                            break;

                case "CH: 13":switch (dB) { case -55: thirteen_55++;
                                                      break;
                                            case -65: thirteen_65++;
                                                      break;
                                            case -80: thirteen_80++;
                                                      break;
                                        }
                    break;
                }
            }
    }

    public HashMap<String ,Integer> getData(){
        return map;
    }

    /***  fragment contain column chart.***/
    public static class PlaceholderFragment extends Fragment {

        private ColumnChartView chart;
        private ColumnChartData data;
        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasLabels = false;
        private boolean hasLabelForSelected = false;
        HashMap<String, Integer> map;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_column_chart, container, false);

            chart = (ColumnChartView) rootView.findViewById(R.id.chart);
            chart.setOnValueTouchListener(new ValueTouchListener());
            ColumnChartActivity obj=(ColumnChartActivity)getActivity();
            map = obj.getData();
            generateData();

            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.column_chart, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_reset) {
                reset();
                generateData();
                return true;
            }


           /* if (id == R.id.action_toggle_labels) {
                toggleLabels();
                return true;
            }

            if (id == R.id.action_toggle_selection_mode) {
                toggleLabelForSelected();

                Toast.makeText(getActivity(), "Selection mode set to " + chart.isValueSelectionEnabled() + " select any point.",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
          */
            return super.onOptionsItemSelected(item);

        }//  End of onOptionsItemSelected


        private void reset() {
            hasAxes = true;
            hasAxesNames = true;
            hasLabels = false;
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }


        /*** Generates columns with stacked subcolumns.*/
        private void generateStackedData() {

           List<Integer> valuelist = new ArrayList<Integer>(map.values());

            int numSubcolumns = 3;
            int numColumns = 12;

            // 3 subcolumns in each 12 of the columns.

            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            int cntr=0;
            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue(valuelist.get(cntr++), ChartUtils.pickColor()));
                }

                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            // Set stacked flag.
            data.setStacked(true);

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    axisX.setName("Channels");
                    axisY.setName("Number  of Channels");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

            chart.setColumnChartData(data);
        }


        private int getSign() {
            int[] sign = new int[]{-1, 1};
            return sign[Math.round((float) Math.random())];
        }

        private void generateData() {
            generateStackedData();
        }



        private class ValueTouchListener implements ColumnChartOnValueSelectListener {

            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                Toast.makeText(getActivity(), "Selected: " + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {
            }
        }// End of ValueTouchListener
    }//end of Placeholder Fragment
}