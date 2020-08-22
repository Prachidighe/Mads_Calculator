package com.calculator.mads.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsUtil {

    private static String PREFERENCES = "Mads";

    public static void setStringPreferences(Context context, String key,
                                            String value) {
        SharedPreferences setting = context
                .getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getStringPreferences(Context context, String key) {
        SharedPreferences setting = context.getSharedPreferences(PREFERENCES, 0);
        return setting.getString(key, "");

    }
}
