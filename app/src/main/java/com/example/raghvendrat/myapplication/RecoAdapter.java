package com.example.raghvendrat.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*** Created by raghvendrat on 04-03-2016.*/

public class RecoAdapter extends ArrayAdapter<Reco> {
    Context context;
    int layoutResId;
    List<Reco> myReco = new ArrayList<Reco>();

    public RecoAdapter(Context context,int layoutResId, List<Reco> myReco){
        super( context , R.layout.reco_item_view , myReco);
        this.context = context;
        this.layoutResId = layoutResId;
        this.myReco = myReco;
    }

    public int getCount(){
        return myReco.size();
    }

    public Reco getItem(int position ){                                       // Here network is the object
        return myReco.get(position);
    }

    public long getItemId(int position){
        return position;
    }

    /************* View holder*********8***/
    public View getView(int position, View convertView, ViewGroup parent) {

        Recoholder holder = null;
        View vi = convertView;

        if (vi == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            vi = inflater.inflate(R.layout.reco_item_view, parent, false);

            holder = new Recoholder();
            holder.tvserialno = (TextView) vi.findViewById(R.id.tvserialno);
            holder.tvsizechannel = (TextView) vi.findViewById(R.id.tvsizechannel);
            holder.tvClarity = (TextView) vi.findViewById(R.id.tvClarity);
            holder.ivprogressbar = (ImageView) vi.findViewById(R.id.ivprogressbar);

            vi.setTag(holder);
        }

        else {     /********* to view the screen when no NEW view***********/
            holder = (Recoholder) vi.getTag();
        }
        /********** Setup tvRSSI,SSID,Mac,Icon ********************/

        holder.tvserialno.setText(Integer.toString(myReco.get(position).getChannelno(context)));
        holder.tvsizechannel.setText(Integer.toString((myReco.get(position).getNum(context))));
        holder.tvClarity.setText(myReco.get(position).getClarity(context));
        holder.ivprogressbar.setImageResource(myReco.get(position).getPbar_image());

        return vi;

    }

     public static class Recoholder{
         TextView tvserialno;
         TextView tvsizechannel;
         TextView tvClarity;
         ImageView ivprogressbar;
     }
}
