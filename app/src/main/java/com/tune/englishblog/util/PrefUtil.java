package com.tune.englishblog.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by TungNT on 19/02/2016.
 */
public class PrefUtil {

    public static final String SHOW_READ = "show_read";

    public static <T> T get(Context context, String key, T defValue) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if(defValue instanceof Boolean) return (T)(Boolean) settings.getBoolean(key, (Boolean) defValue);
        if(defValue instanceof Integer) return (T)(Integer) settings.getInt(key, (Integer) defValue);
        if(defValue instanceof Long) return (T)(Long) settings.getLong(key, (Long) defValue);
        if(defValue instanceof String) return (T)(String) settings.getString(key, (String) defValue);
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

    public static void registerOnPrefChangeListener(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        try {
            PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(listener);
        } catch (Exception ignored) { // Seems to be possible to have a NPE here... Why??
        }
    }

    public static void unregisterOnPrefChangeListener(Context context, SharedPreferences.OnSharedPreferenceChangeListener listener) {
        try {
            PreferenceManager.getDefaultSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(listener);
        } catch (Exception ignored) { // Seems to be possible to have a NPE here... Why??
        }
    }
}
