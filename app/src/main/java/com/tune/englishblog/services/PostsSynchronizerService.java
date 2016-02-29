package com.tune.englishblog.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.couchbase.lite.Status;
import com.tune.englishblog.PostListActivity;
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
                if(isNetworkAvailable(getApplicationContext())){
                    showNotification(syncNewPosts());
                }
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
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

    private void showNotification(Integer newCount) {
        if(newCount > 0){
            String text = getResources().getString(R.string.number_of_new_posts, newCount);

            Intent notificationIntent = new Intent(this, PostListActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder notifyBuilder = new Notification.Builder(getApplicationContext())
                    .setContentIntent(contentIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker(text)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(text)
                    .setLights(0xffffffff, 0, 0);

            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, notifyBuilder.build());
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
    private static final String CSS_WRAPPER = ".list_news .block_image_news";
    private static final String CSS_TITLE_LINK = ".title_news a.txt_link";
    private static final String CSS_ICON_IMG = ".thumb img";
    private static final String CSS_TITLE_LINK_P2 = "a[href=\"%s\"]";
    private static final String CSS_CONTENT_TITLE  = ".title_news h1";
    private static final String CSS_CONTENT_INTRO = ".short_intro";
    private static final String CSS_CONTENT_MAIN  = ".fck_detail,#article_content";
    private static final String CSS_CONTENT_EXCLUDE  = ".box_quangcao";

    private Integer syncNewPosts(){
        int count = 0;
        int page = 1;
        int timeout = 10000;
        while(true){
            Log.d(TAG, "Tune Page: "+page);
            try{
                Connection.Response response = Jsoup.connect(POSTS_URL + page + ".html").timeout(timeout).execute();
                if(response.statusCode() != Status.OK) return count; // Page not exist
                else page++;
                CBHelper cbHelper = new CBHelper(getApplicationContext());
                for(Element wrapper : response.parse().body().select(CSS_WRAPPER)) {
                    try {
                        String iconUrl = wrapper.select(CSS_ICON_IMG).attr("src");
                        String url = wrapper.select(CSS_TITLE_LINK).attr("href");
                        String docID = StringUtils.substringBefore(StringUtils.substringAfterLast(url, "-"), ".html");

                        if(cbHelper.getDatabaseInstance().getExistingDocument(docID) != null) return count; // Meet the latest existing Post

                        Document detailPage = Jsoup.connect(url).timeout(timeout).get();
                        Log.d(TAG, detailPage.body().select(CSS_CONTENT_TITLE).html());
                        Element elContent = detailPage.body().select(CSS_CONTENT_MAIN).first();
                        // remove the ads
                        elContent.select(CSS_CONTENT_EXCLUDE).remove();
                        // load second page
                        String secondPageContent = "";
                        Element secondPageLink = elContent.select(String.format(CSS_TITLE_LINK_P2, StringUtils.substringBeforeLast(url,".html")+"-p2.html")).first();
                        if(secondPageLink != null) {
                            Log.d(TAG, String.format(CSS_TITLE_LINK_P2, StringUtils.substringBeforeLast(url,".html")+"-p2.html"));
                            Document docSecondPage = Jsoup.connect(secondPageLink.attr("href")).timeout(timeout).get();
                            Element elContentSecond = docSecondPage.body().select(CSS_CONTENT_MAIN).first();
                            if(elContentSecond != null){
                                secondPageContent = elContentSecond.html();
                                secondPageLink.remove();
                            }
                        }

                        String content = detailPage.body().select(CSS_CONTENT_INTRO).html() + elContent.html() + secondPageContent;
                        String title   = detailPage.body().select(CSS_CONTENT_TITLE).html();

                        if(cbHelper.getDatabaseInstance().getExistingDocument(docID) == null){
                            com.couchbase.lite.Document document = cbHelper.getDatabaseInstance().getDocument(docID);
                            document.putProperties(map(
                                    pair(CBHelper.POST_KEY_TITLE, (Object)title),
                                    pair(CBHelper.POST_KEY_CONTENT, (Object)content),
                                    pair(CBHelper.POST_KEY_ICON, (Object)iconUrl)
                            ));
                            count++;
                        }
                    }
                    catch(Exception e){
                        Log.d(TAG, "Unexpected Exception: " + e.getMessage(), e);
                    }
                }
            }
            catch(Exception e){
                Log.d(TAG, "Unexpected Exception: " + e.getMessage(), e);
            }
        }
//        Log.d(TAG, "New posts: "+count);
//        return count;
    }
}
