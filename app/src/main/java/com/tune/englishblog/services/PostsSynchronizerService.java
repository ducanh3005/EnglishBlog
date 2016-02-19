package com.tune.englishblog.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.couchbase.lite.Status;
import com.tune.englishblog.R;
import com.tune.englishblog.util.CBHelper;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import static com.tune.englishblog.util.CollectionUtils.map;
import static com.tune.englishblog.util.Pair.pair;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class PostsSynchronizerService extends IntentService {
    private static final String ACTION_SYNC_POSTS = "com.tune.englishblog.services.action.syncNewPosts";
    private static final String ACTION_BAZ = "com.tune.englishblog.services.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.tune.englishblog.services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.tune.englishblog.services.extra.PARAM2";

    private Context context;

    public PostsSynchronizerService(Context context){
        super(PostsSynchronizerService.class.getName());
        this.context = context;
    }

    /**
     * Starts this service to perform action sync Posts with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionSyncPosts(Context context) {
        Intent intent = new Intent(context, PostsSynchronizerService.class);
        intent.setAction(ACTION_SYNC_POSTS);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PostsSynchronizerService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public PostsSynchronizerService() {
        super("PostsSynchronizerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SYNC_POSTS.equals(action)) {
                showNotification(syncNewPosts());
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }
    }

    private void showNotification(Integer newCount) {
        if(newCount > 0){
            String text = getResources().getQuantityString(R.plurals.number_of_new_posts, newCount, newCount);

            Intent notificationIntent = new Intent(getApplicationContext(), this.getClass());
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder notifBuilder = new Notification.Builder(getApplicationContext())
                    .setContentIntent(contentIntent)
//                .setSmallIcon(R.drawable.ic_statusbar_rss)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setTicker(text)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(text)
                    .setLights(0xffffffff, 0, 0);

            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notifBuilder.getNotification());
        }
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    ////////////// HTML crawler /////////////////////
    private static final String TAG = PostsSynchronizerService.class.getSimpleName();
    private static final String POSTS_URL = "http://vnexpress.net/tin-tuc/giao-duc/hoc-tieng-anh/page/";
    public static final String POST_KEY_TITLE   = "title";
    public static final String POST_KEY_CONTENT = "content";
    private static final String CSS_TITLE_LINK = ".list_news .title_news a.txt_link";
    private static final String CSS_CONTENT_TITLE  = ".title_news h1";
    private static final String CSS_CONTENT_INTRO = ".short_intro";
    private static final String CSS_CONTENT_MAIN  = ".fck_detail";
    private static final String CSS_CONTENT_EXCLUDE  = ".box_quangcao";

    private Integer syncNewPosts(){
        int count = 0;
        int page = 36;
        try{
            while(true){
                Connection.Response response = Jsoup.connect(POSTS_URL + page + ".html").execute();
                if(response.statusCode() != Status.OK) return count; // Page not exist
                else page++;

                CBHelper cbHelper = new CBHelper(getApplicationContext());
                for(Element link : response.parse().body().select(CSS_TITLE_LINK)){
                    String url = link.attr("href");
                    String docID = StringUtils.substringBefore(StringUtils.substringAfterLast(url, "-"), ".html");

                    Document detailPage = Jsoup.connect(url).get();
                    Element elContent = detailPage.body().select(CSS_CONTENT_MAIN).first();
                    // remove the ads
                    elContent.getElementsByClass(CSS_CONTENT_EXCLUDE).remove();
                    String content = detailPage.body().select(CSS_CONTENT_INTRO).html() + elContent.html();
                    String title   = detailPage.body().select(CSS_CONTENT_TITLE).html();

                    Log.d(TAG, title);

                    if(cbHelper.getDatabaseInstance().getExistingDocument(docID) == null){
                        com.couchbase.lite.Document document = cbHelper.getDatabaseInstance().getDocument(docID);
                        document.putProperties(map(pair(POST_KEY_TITLE, (Object)title), pair(POST_KEY_CONTENT, (Object)content)));
                        count++;
                    }
                    else return count; // Meet the latest existing Post
                }
            }
        }
        catch(Exception e){
            Log.d(TAG, "Unexpected Exception: " + e.getMessage(), e);
            return count;
        }
    }
}
