package com.andvarp.domotica.util;

import android.content.Context;
import android.widget.Toast;

import com.andvarp.domotica.R;

/**
 * Created by andvarp on 11/20/14.
 */
public class Util {

    Context _context;

    public Util(Context context) {
        _context = context;
    }

    public void makeToast(String text,int duration){
        Toast.makeText(_context, text, duration).show();
    }

    public String urlBuilder(int request, Boolean state){
        String url = new String();
        Long tsLong = System.currentTimeMillis()/1000;
        String timeStamp = "/" + tsLong.toString();
        switch (request){
            case 0:
                url = getString(R.string.serverURL) + getString(R.string.serverConnect) + timeStamp;
                break;
            case 1:
                url = getString(R.string.serverURL) + getString(R.string.serverLight);
                break;
            case 2:
                url = getString(R.string.serverURL) + getString(R.string.serverMusic);
                break;
            case 3:
                url = getString(R.string.serverURL) + getString(R.string.serverSensor) + timeStamp;
                break;
            case 4:
                url = getString(R.string.serverURL) + getString(R.string.serverLight);
                if(state){
                    url += getString(R.string.serverON) + timeStamp;
                }else{
                    url += getString(R.string.serverOFF) + timeStamp;
                }
                break;
            case 5:
                url = getString(R.string.serverURL) + getString(R.string.serverMusic);
                if(state){
                    url += getString(R.string.serverON) + timeStamp;
                }else{
                    url += getString(R.string.serverOFF) + timeStamp;
                }
                break;
            default:
                url = getString(R.string.serverURL)+getString(R.string.serverConnect);
                break;
        }
        return url.toString();
    }

    private String getString(int id){
        return _context.getString(id);
    }
}
