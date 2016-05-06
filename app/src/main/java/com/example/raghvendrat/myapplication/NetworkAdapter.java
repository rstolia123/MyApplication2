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

/*** Created by raghvendrat on 28-01-2016.*/
public class NetworkAdapter extends ArrayAdapter<Network> {
    Context context;
    int layoutResId;
    List<Network> myNets = new ArrayList<Network>();

    /*****  Create NetworkAdapter constructor **********/
    public NetworkAdapter(Context context, int layoutResId, List<Network> myNets){
        super( context , R.layout.item_view, myNets);
        this.context = context;
        this.layoutResId = layoutResId;
        this.myNets = myNets;
    }
    /*** Methods ******/
    @Override
    public int getCount(){
        return myNets.size();
    }
    @Override
    public Network getItem(int position ){                                       // Here network is the object
         return myNets.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }



    /************* View holder*********8***/
    public View getView(int position, View convertView, ViewGroup parent) {

      Networkholder holder = null;
      View vi = convertView;

      if (vi == null) {
          LayoutInflater inflater = ((Activity) context).getLayoutInflater();
          vi = inflater.inflate(R.layout.item_view, parent, false);

          holder = new Networkholder();
          holder.tvSSID = (TextView) vi.findViewById(R.id.tvSSID);
          holder.tvMac = (TextView) vi.findViewById(R.id.tvMac);
          holder.tvRSSI = (TextView) vi.findViewById(R.id.tvRSSI);
          holder.tvChannel = (TextView) vi.findViewById(R.id.tvChannel);
          holder.ivIcon = (ImageView) vi.findViewById(R.id.rssi_icon);

          vi.setTag(holder);
      }

      else {     /********* to view the screen when no NEW view***********/
                 holder = (Networkholder)vi.getTag();
      }
        /********** Setup tvRSSI,SSID,Mac,Icon ********************/

        holder.tvSSID.setText(myNets.get(position).getSSID(context));
        holder.tvRSSI.setText(Integer.toString((myNets.get(position).getRSSI(context))));
        holder.tvMac.setText(myNets.get(position).getMac_add(context));
        holder.tvChannel.setText(myNets.get(position).getChannel(context));
        holder.ivIcon.setImageResource(myNets.get(position).getIcon_ID());

      return vi;

  }

    public static class Networkholder{
        TextView tvSSID;
        TextView tvMac;
        TextView tvRSSI;
        TextView tvChannel;
        ImageView ivIcon;

    }
}
