package com.tune.englishblog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.couchbase.lite.Document;
import com.squareup.picasso.Picasso;
import com.tune.englishblog.services.PostsSynchronizerService;
import com.tune.englishblog.util.CBHelper;

import org.apache.commons.lang3.StringUtils;

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

    public void reloadAllPosts(Context context){
        this.clear();
        this.addAll(postList = new CBHelper(context).getAllPosts());
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        Document post = getItem(position);
        TextView tvTitle = (TextView) convertView.findViewById(android.R.id.text1);
        tvTitle.setText((String) post.getProperty(PostsSynchronizerService.POST_KEY_TITLE));
        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.icon);
        Picasso.with(getContext()).load(StringUtils.trimToNull((String) post.getProperty(PostsSynchronizerService.POST_KEY_ICON))).into(ivIcon);

        return convertView;
    }

}
