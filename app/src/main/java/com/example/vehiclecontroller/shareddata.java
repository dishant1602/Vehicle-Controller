package com.example.vehiclecontroller;

import android.content.Context;
import android.content.SharedPreferences;

public class shareddata {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public shareddata(Context _context){
        context = _context;
        sharedPreferences = _context.getSharedPreferences("dishant",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void inputdata(String s, String p){
        editor.putString(s,p);
        editor.commit();
    }

    public void cleardata(String i){
        editor.remove(i);
        editor.commit();
    }

    public String outputdata(String l){
        String x = sharedPreferences.getString(l,"y");
        return x;
    }
}
