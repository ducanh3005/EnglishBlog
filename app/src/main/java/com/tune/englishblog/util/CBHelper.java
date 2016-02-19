package com.tune.englishblog.util;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Manager;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by TungNT on 18/02/2016.
 */
public class CBHelper {
    private Context context;
    private Manager manager = null;
    private Database database = null;
    private static final String DB_NAME = "english-blog";

    public CBHelper(Context context){
        this.context = context;
    }

    public Database getDatabaseInstance() throws CouchbaseLiteException, IOException {
        if ((this.database == null)) {
            this.database = this.getManagerInstance().getDatabase(DB_NAME);
        }
        return database;
    }
    public Manager getManagerInstance() throws IOException {
        if (manager == null) {
            manager = new Manager(new AndroidContext(context), Manager.DEFAULT_OPTIONS);
        }
        return manager;
    }

    public List<Document> getAllPosts() {
        try{
            List<com.couchbase.lite.Document> allDocs = new ArrayList<>();
            Query query = getDatabaseInstance().createAllDocumentsQuery();
            query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);
            query.setLimit(15);
            QueryEnumerator result = query.run();
            for (Iterator<QueryRow> it = result; it.hasNext(); ) {
                allDocs.add(it.next().getDocument());
            }

            return allDocs;
        }
        catch(Exception e){
            Log.d(getClass().getName(), "Application Exception: " + e.getMessage(), e);
            return null;
        }
    }
}
