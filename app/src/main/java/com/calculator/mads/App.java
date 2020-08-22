package com.calculator.mads;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class App extends Application {

    public static ArrayList<HashMap<String, String>> calculationsList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
