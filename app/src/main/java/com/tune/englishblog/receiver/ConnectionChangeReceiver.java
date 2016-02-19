package com.tune.englishblog.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tune.englishblog.services.PostsSynchronizerService;

public class ConnectionChangeReceiver extends BroadcastReceiver {
    public ConnectionChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(isNetworkAvailable(context)){
            PostsSynchronizerService.startActionSyncPosts(context);
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
