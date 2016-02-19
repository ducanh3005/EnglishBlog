package com.tune.englishblog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.couchbase.lite.Document;
import com.tune.englishblog.util.CBHelper;

import java.util.List;

/**
 * Created by TungNT on 18/02/2016.
 */
public class PostListAdapter extends ArrayAdapter<Document> {

    private List<Document> postList;
    private int resource;

    public PostListAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
        this.addAll(postList = new CBHelper(context).getAllPosts());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        Document post = getItem(position);
        TextView tvTitle = (TextView) convertView.findViewById(android.R.id.text1);
//        tvTitle.setText((String) post.getProperty(PostsSynchronizerService.POST_KEY_TITLE));
        tvTitle.setText((String) post.getId());
        return convertView;
    }

}
