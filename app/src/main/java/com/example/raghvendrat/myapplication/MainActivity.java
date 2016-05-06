package com.example.raghvendrat.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.util.Log;
import android.content.BroadcastReceiver;

import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MainActivity extends AppCompatActivity {

    int one = 0;    int two = 0;    int three = 0;
    int four = 0;   int five = 0;   int six = 0;
    int seven = 0;  int eight = 0;  int nine = 0;
    int ten = 0;    int eleven = 0; int twelve = 0;  int thirteen = 0;

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

    WifiManager mainWifi;
    WifiReceiver receiver;

    List<ScanResult> wifi_list;
    List<Network> myNets = new ArrayList<Network>();
    List<Network> myNets1 = new ArrayList<Network>();
    List<Reco> myReco = new ArrayList<Reco>();
    ArrayAdapter<Reco> recoAdapter;

    ArrayAdapter<Network> adapter;
    ViewPager vpager;

    HashMap<String, Integer> map = new HashMap<String, Integer>();
    private RadioGroup radioGroup;
    private Menu mymenu;

    int currTabIndex = 0;
    boolean bCurrWifiState=false;

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mainWifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        receiver = new WifiReceiver();
        startScan();

        radioGroup = (RadioGroup) findViewById(R.id.Radio_group);
        radioGroup.check(R.id.BandtwoGhz);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.BandtwoGhz) {

                    Toast.makeText(getApplicationContext(), "choice: 2.4GHz", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "choice: 5GHz", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(savedInstanceState == null) {
            //getSupportFragmentManager().beginTransaction().add(R.id.container, new Channel_fragment()).commit();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("AP View"));                    // Access Point view
        tabLayout.addTab(tabLayout.newTab().setText("CH View"));                    // Channel fragment view
        tabLayout.addTab(tabLayout.newTab().setText("RECO View"));                  // Recommendation View
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initialization
        vpager = (ViewPager)findViewById(R.id.view_pager);
        final MyPagerAdapter adapter=  new MyPagerAdapter(getSupportFragmentManager());
        vpager.setAdapter(adapter);

        //On swiping the viewpager change listener helps to get tab selected
        vpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpager.setCurrentItem(tab.getPosition());
                currTabIndex = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}

        });
    }

    public void startScan(){mainWifi.startScan();}

    public static class MyPagerAdapter extends FragmentStatePagerAdapter{

        int num = 3;
        public MyPagerAdapter(FragmentManager fm)
        {super(fm);}

        public int getCount(){
            return num;
        }

        // Returns fragments to display for the page.
        public Fragment getItem(int position){
            switch(position){
                case 0: AP_fragment tab1 = new AP_fragment();
                        return tab1;
                case 1: Channel_fragment tab2 = new Channel_fragment();
                        return tab2;
                case 2: Reco_fragment tab3 = new Reco_fragment();
                        return tab3;
                default: return null;
            }
        }

        public int getItemPosition(Object object){

            if (object instanceof UpdateableFragment) {
                ((UpdateableFragment) object).update();
            }
            return super.getItemPosition(object);
        }
    }

    public List checkList(){
        int select = radioGroup.getCheckedRadioButtonId();
        if (select == R.id.BandtwoGhz) {return myNets;}
        else {return myNets1;}
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.BandtwoGhz:
                if (checked) {
                    populateListView();                           // return myNets
                }
                break;
            case R.id.BandfiveGhz:
                if (checked) {
                    populateListView();                          // return myNets1
                }
                break;
        }
    }

    protected void onPause(){                                           //onPause  broadcast method
        unregisterReceiver(receiver);
        super.onPause();
    }

    protected void onResume(){                                          //onResume broadcst method
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
     }

    public void ap_frag() {

        if (wifi_list != null && myNets != null) {

            for (int i = 0; i < wifi_list.size(); i++) {
                if (checkChannel(wifi_list.get(i).frequency) == true) {
                    myNets.add(new Network(wifi_list.get(i).SSID, wifi_list.get(i).level, getIconfromRssi(wifi_list.get(i).level),
                            getChannelfromfreq(wifi_list.get(i).frequency), wifi_list.get(i).BSSID));   // { SSID ,RSSI,Icon_ID, Mac_add }
                } else {
                    myNets1.add(new Network(wifi_list.get(i).SSID, wifi_list.get(i).level, getIconfromRssi(wifi_list.get(i).level),
                            getfiveChannelfromfreq(wifi_list.get(i).frequency), wifi_list.get(i).BSSID));
                }
            }
        }
    }

    public boolean checkChannel(int frequency){
        if(frequency >= 2412  && frequency <= 2472)
            return true;
        else
            return false;
    }

    public void channel_frag(){
         Find_no_AP();
         pop();
         refreshGraphFragment();
     }


    private class WifiReceiver extends BroadcastReceiver{                                      // Broadcast Receiver used to start scanning
        @Override
        public void onReceive(Context c, Intent intent) {
            Log.d("TAG", "On recieve called");
            resetCounter();
            resetlistCounter();
            wifi_list = mainWifi.getScanResults();

            ap_frag();                // AP_frag
            channel_frag() ;          // Channel_frag
            reco_frag();
            populateListView();

            Log.d("TAG","Wifi details called");
        }
    }

    public void reco_frag(){
        No_of_networks();
        display();
     }

    private void No_of_networks() {
        String channelno;
        for (int i = 0; i < wifi_list.size(); i++) {

            int freq = wifi_list.get(i).frequency;
            channelno = getChannelfromfreq(freq);
            switch (channelno) {
                case "CH: 1":   one++;
                    break;
                case "CH: 2":   two++;
                    break;
                case "CH: 3":   three++;
                    break;
                case "CH: 4":   four++;
                    break;
                case "CH: 5":   five++;
                    break;
                case "CH: 6":   six++;
                    break;
                case "CH: 7":   seven++;
                    break;
                case "CH: 8":   eight++;
                    break;
                case "CH: 9":   nine++;
                    break;
                case "CH: 10": ten++;
                    break;
                case "CH: 11": eleven++;
                    break;
                case "CH: 12": twelve++;
                    break;
                case "CH: 13": thirteen++;
                    break;
            }
        }
    }

    public void display(){
        if(wifi_list  != null && myReco != null )
        {
            myReco.clear();
            myReco.add(new Reco(1 , getpbar_image(one)     ,one      ,channel_clarity(one)));
            myReco.add(new Reco(2 , getpbar_image(two)     ,two      ,channel_clarity(two)));
            myReco.add(new Reco(3 , getpbar_image(three)   ,three    ,channel_clarity(three)));
            myReco.add(new Reco(4 , getpbar_image(four)    ,four     ,channel_clarity(four)));
            myReco.add(new Reco(5 , getpbar_image(five)    ,five     ,channel_clarity(five)));
            myReco.add(new Reco(6 , getpbar_image(six)     ,six      ,channel_clarity(six)));
            myReco.add(new Reco(7 , getpbar_image(seven)   ,seven    ,channel_clarity(seven)));
            myReco.add(new Reco(8 , getpbar_image(eight)   ,eight    ,channel_clarity(eight)));
            myReco.add(new Reco(9 , getpbar_image(nine)    ,nine     ,channel_clarity(nine)));
            myReco.add(new Reco(10 ,getpbar_image(ten)     ,ten      ,channel_clarity(ten)));
            myReco.add(new Reco(11 ,getpbar_image(eleven)  ,eleven   ,channel_clarity(eleven)));
            myReco.add(new Reco(12 ,getpbar_image(twelve)  ,twelve   ,channel_clarity(twelve)));
            myReco.add(new Reco(13 ,getpbar_image(thirteen),thirteen ,channel_clarity(thirteen)));
        }
    }

    public int getpbar_image (int counter){
        if(counter == 0 )
            return R.drawable.pbar0;
        else if(counter == 1)
            return R.drawable.pbar_1;      //GREAT
        else if(counter > 1 && counter <=4)
            return R.drawable.pbar2;      // GOOD
        else if(counter > 4 && counter <= 7)
            return R.drawable.pbar7;     // AVERAGE
        else
            return R.drawable.pbarfull;     // POOR
    }

    public String channel_clarity(int channelNo) {

        if (channelNo == 0) return "  BEST   ";                                    //BEST  -  0
        else if (channelNo == 1) return "  GREAT  ";                              //GREAT -  1
        else if (channelNo > 1 && channelNo <= 4) return "AVERAGE";              //GOOD  -  2,4
        else if (channelNo >= 5) return "  POOR   ";                            //POOR -   >5
        else return "NONE";
    }

    public void resetRecoCounter(){
        one = 0;    two = 0;     three = 0;
        four = 0;   five = 0;    six = 0;
        seven = 0;  eight = 0;   nine = 0;
        ten = 0;    eleven = 0;  twelve = 0;   thirteen =0;
    }

    public void refreshGraphFragment(){
        vpager.getAdapter().notifyDataSetChanged();
    }

    public void pop() {
    if (map!=null)
      map.clear();

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

    public void Find_no_AP() {
        resetlistCounter();
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

                case "CH: 6":switch (dB) {case -55: six_55++;
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

    public HashMap<String ,Integer> getData(){return map;}


    /****   Wifi Switch on - off ( onCreate Options Method ) ***/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            MenuItem menuItem = menu.findItem(R.id.action_enable);
            View view = MenuItemCompat.getActionView(menuItem);
            mymenu = menu;

            //Wifi Switch
            Switch switcha = (Switch) view.findViewById(R.id.switchForActionBar);
            switcha.setChecked(bCurrWifiState);
            switcha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        bCurrWifiState=true;
                        toggleWifi(true);
                        Toast.makeText(getApplicationContext(), "Wifi is Enabled", Toast.LENGTH_SHORT).show();
                    } else {
                        bCurrWifiState=false;
                        toggleWifi(false);
                        Toast.makeText(getApplicationContext(), "Wifi is Disabled", Toast.LENGTH_SHORT).show();

                        adapter.clear();
                        adapter.notifyDataSetChanged();

                    }
                }
            });

            return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
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

                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ImageView iv = (ImageView)inflater.inflate(R.layout.iv_refresh, null);
                Animation rotation = AnimationUtils.loadAnimation(this, R.anim.animation);
                rotation.setRepeatCount(Animation.INFINITE);
                iv.startAnimation(rotation);
                item.setActionView(iv);

                new UpdateTask(this).execute();
                adapter.clear();
                startScan();
                populateListView();


                Log.d("TAG", "Refresh called");
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    /*** Update task for refresh_action method ***/
    public class UpdateTask extends AsyncTask<Void ,Void,Void > {

        private Context mcon;
        public UpdateTask(Context con){
            mcon = con;
        }

        protected Void doInBackground(Void... params) {
            try{
                Thread.sleep(3000);
                return null;
            }
            catch (Exception e) {
                return null ;
            }
        }
        protected void onPostExecute(Void nope){

            Toast.makeText(mcon, "Finish Updating Process ",Toast.LENGTH_LONG).show();
            ((MainActivity) mcon).resetUpdating();
        }
    }

    /** Reset Update for menu option **/
    public void resetUpdating(){

        MenuItem m = mymenu.findItem(R.id.action_refresh);
        if(m.getActionView()!=null)
        { // Remove the animation.
            m.getActionView().clearAnimation();
            m.setActionView(null);
        }
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

    public void resetlistCounter(){
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

    private void populateListView() {

        switch (currTabIndex){
            case 0:    adapter = new NetworkAdapter(this, R.layout.item_view, checkList());
                       ListView list = (ListView) findViewById(R.id.netListView);
                       if(list != null) {
                           list.setAdapter(adapter);
                           adapter.notifyDataSetChanged();
                           Log.d("TAG", "ListView details called");
                       }
                       break;

            case 2:   recoAdapter = new RecoAdapter(this, R.layout.reco_item_view, myReco);
                      list = (ListView) findViewById(R.id.recoListView);
                      if (list != null) {
                          list.setAdapter(recoAdapter);
                          recoAdapter.notifyDataSetChanged();
                          Log.d("TAG", "RecoListView details called");
                      }
                      break;
        }
    }

    private void resetCounter() {
        switch (currTabIndex) {
            case 0 : resetlistCounter();
                     break;
            case 2:  resetRecoCounter();
                    break;

        }
    }

    public String getfiveChannelfromfreq(int freq_five){
        if(freq_five == 5180)
            return "CH: 36";
        else if(freq_five == 5200)
            return "CH: 40";
        else if(freq_five == 5220)
            return "CH: 44";
        else if(freq_five == 5240)
            return "CH: 48";
        else if(freq_five == 5260)
            return "CH: 52";
        else if(freq_five == 5280)
            return "CH: 56";
        else if(freq_five == 5300)
            return "CH: 60";
        else if(freq_five == 5320)
            return "CH: 64";
        else if(freq_five == 5500)
            return "CH: 100";
        else if(freq_five == 5520)
            return "CH: 104";
        else if(freq_five == 5540)
            return "CH: 108";
        else if(freq_five == 5560)
            return "CH: 112";
        else if(freq_five == 5580)
            return "CH: 116";
        else if(freq_five == 5590)
            return "CH: 118";
        else if(freq_five == 5600)
            return "CH: 120";
        else if(freq_five == 5610)
            return "CH: 122";
        else if(freq_five == 5620)
            return "CH: 124";
        else if(freq_five == 5630)
            return "CH: 126";
        else if(freq_five == 5640)
            return "CH: 128";
        else if(freq_five == 5660)
            return "CH: 132";
        else if(freq_five == 5670)
            return "CH: 134";
        else if(freq_five == 5680)
            return "CH: 136";
        else if(freq_five == 5690)
            return "CH: 138";
        else if(freq_five == 5700)
            return "CH: 140";
        else if(freq_five == 5710)
            return "CH: 142";
        else if(freq_five == 5720)
            return "CH: 144";
        else if(freq_five == 5745)
            return "CH: 149";
        else if(freq_five == 5755)
            return "CH: 151";
        else if(freq_five == 5765)
            return "CH: 153";
        else if(freq_five == 5775)
            return "CH: 155";
        else if(freq_five == 5785)
            return "CH: 157";
        else if(freq_five == 5795)
            return "CH: 159";
        else if(freq_five == 5805)
            return "CH: 161";
        else if(freq_five == 5825)
            return "CH: 165";

        else
            return "CH :0";
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

}
