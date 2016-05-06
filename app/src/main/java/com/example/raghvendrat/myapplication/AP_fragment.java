package com.example.raghvendrat.myapplication;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by raghvendrat on 03-02-2016.
 */
public class AP_fragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        WifiManager mainWifi;
        mainWifi = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
        mainWifi.startScan();
        View view = inflater.inflate(R.layout.ap_frag, container, false);
        return view;
    }

}
