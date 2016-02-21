package com.tune.englishblog.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tune.englishblog.services.PostsSynchronizerService;

public class ConnectionChangeReceiver extends BroadcastReceiver {
    public ConnectionChangeReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PostsSynchronizerService.startActionSyncPosts(context);
    }
}
