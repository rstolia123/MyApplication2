package com.example.raghvendrat.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

/*** Created by raghvendrat on 03-02-2016.*/

public class Channel_fragment extends Fragment implements UpdateableFragment{

    WifiManager mainwifi;
    private ColumnChartView chart;
    private ColumnChartData data;
    private boolean hasAxes = true;
    private boolean hasAxesNames = true;
    private boolean hasLabels = false;
    private boolean hasLabelForSelected = false;

    HashMap<String, Integer> map;
    public Channel_fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mainwifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        mainwifi.setWifiEnabled(true);
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_column_chart, container, false);

        chart = (ColumnChartView) rootView.findViewById(R.id.chart);
        chart.setOnValueTouchListener(new ValueTouchListener());
        createChart();
        return rootView;

    }

    public void createChart(){

        MainActivity obj=(MainActivity)getActivity();
        map = obj.getData();
        if(map.size()>0) {
            generateData();
            reset();
            toggleLabels();
        }
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

        if(id == R.id.action_refresh) {
            MainActivity obj=(MainActivity)getActivity();
            obj.startScan();
            //toggleLabels();
            return true;
        }
       if (id == R.id.action_toggle_labels) {
            //toggleLabels();
            return true;
        }
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

        int numSubcolumns = 3;                  // 3 subcolumns in each 12 of the columns.
        int numColumns = 12;

        List<Column> columns = new ArrayList<Column>();
        List<SubcolumnValue> values;
        int cntr=0;
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                values.add(new SubcolumnValue(valuelist.get(cntr++), getColors(j)));
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
        //chart.setValueSelectionEnabled(true);
    }
    private int getColors(int index){
        int COLOR_BLUE = Color.parseColor("#316ECC");
        int COLOR_VIOLET = Color.parseColor("#CC53AB");
        int COLOR_GREEN= Color.parseColor("#65B330");

        int value = 0;
        switch(index) {
            case 0: value= COLOR_BLUE;
                    break;
            case 1: value = COLOR_VIOLET;
                    break;
            case 2: value = COLOR_GREEN;
                    break;
        }
       return value;
    }
    private void generateData() {
        generateStackedData();
    }

    private void toggleLabels() {
        hasLabels = !hasLabels;
        if (hasLabels) {
            hasLabelForSelected = false;
            chart.setValueSelectionEnabled(hasLabelForSelected);
        }
        generateData();
    }

    @Override
    public void update(){
        createChart();
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
}
