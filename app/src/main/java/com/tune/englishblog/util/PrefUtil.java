package com.tune.englishblog.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by TungNT on 19/02/2016.
 */
public class PrefUtil {

    public static Object get(Context context, String key, Object defValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if(defValue instanceof Boolean) return settings.getBoolean(key, (boolean) defValue);
        if(defValue instanceof Integer) return settings.getInt(key, (int) defValue);
        if(defValue instanceof Long) return settings.getLong(key, (long) defValue);
        if(defValue instanceof String) return settings.getString(key, (String) defValue);
        else return null;
    }

    public static void put(Context context, String key, Object defValue) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        if(defValue instanceof Boolean) editor.putBoolean(key, (boolean) defValue);
        if(defValue instanceof Integer) editor.putInt(key, (int) defValue);
        if(defValue instanceof Long) editor.putLong(key, (long) defValue);
        if(defValue instanceof String) editor.putString(key, (String) defValue);
        editor.apply();
    }

    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.remove(key);
        editor.apply();
    }
}
