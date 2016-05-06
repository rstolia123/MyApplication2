package com.example.raghvendrat.myapplication;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/** Created by raghvendrat on 08-03-2016.*/

public class Reco_fragment extends Fragment{

    public Reco_fragment(){};

    @Override
    public View onCreateView(LayoutInflater inflater ,ViewGroup container , Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        WifiManager mainWifi;
        mainWifi = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
        mainWifi.startScan();

        View view = inflater.inflate(R.layout.reco_frag , container , false);
        return view;
    }
}
