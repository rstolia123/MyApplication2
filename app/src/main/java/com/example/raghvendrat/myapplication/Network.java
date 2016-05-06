package com.example.raghvendrat.myapplication;

/*** Created by raghvendrat on 19-01-2016.*/
import android.content.Context;


public class Network {
     String SSID;
     int Rssi;
     int Icon_ID;
     String Channel;
     String Mac_add;


    /**************Creating Constructor**********/
    public Network(String SSID, int Rssi, int Icon_ID, String Channel, String Mac_add){
        super();
        this.SSID = SSID;
        this.Rssi = Rssi;
        this.Icon_ID = Icon_ID;
        this.Channel = Channel;
        this.Mac_add = Mac_add;
    }

    /******** Set methods*********/
    public void setSSID(String SSID){
        this.SSID = SSID;
    }
    public void setMac_add(String Mac_add){
       this.Mac_add = Mac_add;
    }
    public void setRssi(int Rssi){
        this.Rssi = Rssi;
    }
    public void setChannel(String Channel){this.Channel = Channel;}
    public void setIcon_ID(int Icon_ID){
        this.Icon_ID = Icon_ID;
    }


    /********Get Methods*******/

    public int getIcon_ID() {
        return Icon_ID;
    }
    public String getMac_add(Context context) {                                        // BSSID for mac address
       if (Mac_add == null) {
            Mac_add = "Device don't have mac address or wifi is disabled";
        }
        return this.Mac_add;
    }
    public String getChannel(Context context){
        return this.Channel;
    }
    public String getSSID(Context context){                                            // return SSID without broadcast receiver
        return this.SSID;
    }
    public int getRSSI(Context context) {// RSSI strength
        return this.Rssi;
    }
}