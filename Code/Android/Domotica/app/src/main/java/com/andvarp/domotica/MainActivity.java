package com.andvarp.domotica;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.andvarp.domotica.util.State;
import com.andvarp.domotica.util.Util;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {

    private AQuery aq;
    private Util util;
    private State stateApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aq = new AQuery(this);
        util = new Util(this);
        stateApp = new State();

        aq.id(R.id.lightBtn).clicked(this, "toogleLight");
        aq.id(R.id.musicBtn).clicked(this, "toogleMusic");
        aq.id(R.id.sensorProBar);
        aq.id(R.id.loadingProBar);

        connect();

    }

    private void connect(){
        String url = util.urlBuilder(0,null);
        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {
//                    util.makeToast(json.toString(), Toast.LENGTH_LONG);
                    Boolean connectedStatus;
                    Boolean lightStatus;
                    Boolean musicStatus;
                    int sensorStatus;
                    try {
                        connectedStatus = json.getBoolean("connected");
                        lightStatus = json.getBoolean("light");
                        musicStatus = json.getBoolean("music");
                        sensorStatus = json.getInt("sensor");

                        stateApp = new State(connectedStatus,lightStatus,musicStatus,sensorStatus);

                        if (connectedStatus){
                            util.makeToast(getString(R.string.connected).toString(),Toast.LENGTH_SHORT);
                            aq.id(R.id.loadingProBar).getProgressBar().setVisibility(View.INVISIBLE);
                        }

                        if (lightStatus){
                            aq.id(R.id.lightBtn).image(getResources().getDrawable(R.drawable.light_on2));
                        }else{
                            aq.id(R.id.lightBtn).image(getResources().getDrawable(R.drawable.light_off2));
                        }

                        if (musicStatus){
                            aq.id(R.id.musicBtn).image(getResources().getDrawable(R.drawable.music_on));
                        }else{
                            aq.id(R.id.musicBtn).image(getResources().getDrawable(R.drawable.music_off));
                        }

                        aq.id(R.id.sensorProBar).getProgressBar().setProgress(sensorStatus);
                        getSensorState();

                    } catch (JSONException e) {
                        util.makeToast("1Error while connecting",Toast.LENGTH_SHORT);
                        e.printStackTrace();
                    }
                } else {
                    util.makeToast("2Error while connecting", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void getSensorState(){
        String url = util.urlBuilder(3,null);
        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {
                    Boolean musicStatus;
                    int sensorStatus;
                    try {

                        sensorStatus = json.getInt("sensor");
                        stateApp.setSensor(sensorStatus);


                        aq.id(R.id.sensorProBar).getProgressBar().setProgress(sensorStatus);
                        getSensorState();

                    } catch (JSONException e) {
                        util.makeToast("5Error while connecting",Toast.LENGTH_SHORT);
                        e.printStackTrace();
                    }
                } else {
                    util.makeToast("6Error while connecting", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void toogleLight(View button){
        String url = util.urlBuilder(4,stateApp.toogleLight());
        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {
                    // makeToast(json.toString(), Toast.LENGTH_LONG);
                    Boolean serverStatus = null;
                    try {
                        serverStatus = json.getBoolean("light");
                        //stateApp.setLight(serverStatus);
                        //util.makeToast(url+"   "+serverStatus.toString(),Toast.LENGTH_SHORT);
//                        if (serverStatus){
                        if (stateApp.getLight()){
                            aq.id(R.id.lightBtn).image(getResources().getDrawable(R.drawable.light_on2));
                        }else{
                            aq.id(R.id.lightBtn).image(getResources().getDrawable(R.drawable.light_off2));
                        }
                    } catch (JSONException e) {
                        util.makeToast("41Error while connecting",Toast.LENGTH_SHORT);
                        e.printStackTrace();
                    }
                } else {
                    util.makeToast("42Error while connecting", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void toogleMusic(View button){
        String url = util.urlBuilder(5,stateApp.toogleMusic());
        //util.makeToast(url,Toast.LENGTH_SHORT);
        aq.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json != null) {
                    // makeToast(json.toString(), Toast.LENGTH_LONG);
                    Boolean serverStatus = null;
                    try {
                        serverStatus = json.getBoolean("music");
                        //stateApp.setMusic(serverStatus);
//                        if (serverStatus){
                          if (stateApp.getMusic()){
                            aq.id(R.id.musicBtn).image(getResources().getDrawable(R.drawable.music_on));
                        }else{
                            aq.id(R.id.musicBtn).image(getResources().getDrawable(R.drawable.music_off));
                        }
                    } catch (JSONException e) {
                        util.makeToast("aError while connecting",Toast.LENGTH_SHORT);
                        e.printStackTrace();
                    }
                } else {
                    util.makeToast("wError while connecting", Toast.LENGTH_SHORT);
                }
            }
        });
    }

}


