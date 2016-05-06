package com.example.raghvendrat.myapplication;

import android.content.Context;

/*** Created by raghvendrat on 29-02-2016.*/
public class Reco {
    int channelno;
    int num;
    int pbar_image;
    String Clarity;

    /**************Creating Constructor**********/
    public Reco(int channelno,  int pbar_image,int num, String Clarity){
        super();
        this.channelno = channelno;
        this.pbar_image = pbar_image;
        this.num = num;
        this.Clarity= Clarity;
    }


    /******** Set methods*********/
    public void setChannelno(int channelno){this.channelno = channelno;}
    public void setnum(int num){this.num = num;}
    public void setPbar_image(int pbar_image){
        this.pbar_image = pbar_image;
    }
    public void setClarity(String Clarity ){
        this.Clarity = Clarity;
    }

    /********Get Methods*******/
    public int getPbar_image() {return this.pbar_image;}
    public int getChannelno(Context context) {return this.channelno;}
    public int getNum(Context context) {return this.num;}
    public String getClarity(Context context){
        return this.Clarity;
    }

}
