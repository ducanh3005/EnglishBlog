/**
 * An {@link android.app.IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */

package com.tune.englishblog.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class RefreshService extends Service {
    private AlarmManager mAlarmManager;
    private PendingIntent mTimerIntent;

    @Override
    public IBinder onBind(Intent intent) {
        onRebind(intent);
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true; // we want to use rebind
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        restartTimer(true);
    }

    public static void startAutoRefreshService(Context context) {
        context.startService(new Intent(context, RefreshService.class));
    }

    private void restartTimer(boolean created) {
        if (mTimerIntent == null) {
            mTimerIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, RefreshAlarmReceiver.class), 0);
        } else {
            mAlarmManager.cancel(mTimerIntent);
        }

        mAlarmManager.setExact(AlarmManager.ELAPSED_REALTIME, 1000*60*60*12L, mTimerIntent);
    }

    @Override
    public void onDestroy() {
        if (mTimerIntent != null) {
            mAlarmManager.cancel(mTimerIntent);
        }
        super.onDestroy();
    }

    public static class RefreshAlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            PostsSynchronizerService.startActionSyncPosts(context);
        }
    }
}
