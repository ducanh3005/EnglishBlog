package com.tune.englishblog.services;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TungNT on 17/02/2016.
 */
public class PostsSynchronizer {
    private static final String TAG = "tune::PostsSynchronizer";
    private static final String POSTS_URL = "http://vnexpress.net/tin-tuc/giao-duc/hoc-tieng-anh";
    private static final String CSS_TITLE_LINK = ".list_news .title_news a.txt_link";

    public List<String> pullAllPosts(){
        try{
            List<String> lists = new ArrayList<>();

            Document doc = Jsoup.connect(POSTS_URL).get();
            for(Element link : doc.body().select(CSS_TITLE_LINK)){
                lists.add(link.attr("href"));
            }
            return lists;
        }
        catch(Exception e){
            Log.d(TAG, "Unexpected Exception: "+e.getMessage());
        }

        return null;
    }



    public static void main(String[] args){
        PostsSynchronizer synchronizer = new PostsSynchronizer();
        for(String title : synchronizer.pullAllPosts()){
            System.out.println(title);
        }
    }
}
