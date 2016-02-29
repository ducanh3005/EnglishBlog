package com.tune.englishblog;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.couchbase.lite.Document;
import com.squareup.picasso.Picasso;
import com.tune.englishblog.util.CBHelper;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by TungNT on 18/02/2016.
 */
public class PostListAdapter extends ArrayAdapter<Document> {

    private List<Document> postList;
    private int resource;
    private CBHelper cbHelper;
    private Integer activatePosition;

    public PostListAdapter(Context context, int resource) {
        super(context, resource);
        this.resource = resource;
        this.cbHelper = new CBHelper(context);
        this.addAll(postList = this.cbHelper.getAllPosts());
    }

    public void reloadAllPosts(Context context){
        this.clear();
        this.addAll(postList = this.cbHelper.getAllPosts());
        this.notifyDataSetChanged();
    }

    public void setActivatePosition(int activatePosition){
        this.activatePosition = activatePosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        Document post = getItem(position);
        TextView tvTitle = (TextView) convertView.findViewById(android.R.id.text1);
        tvTitle.setText((String) post.getProperty(CBHelper.POST_KEY_TITLE));
        tvTitle.setEnabled(!BooleanUtils.isTrue((Boolean) post.getProperties().get(CBHelper.POST_KEY_READ)));
        convertView.setBackgroundColor((activatePosition != null && position == activatePosition) ?
                getContext().getResources().getColor(R.color.color_primary): Color.TRANSPARENT);
        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.icon);
        Picasso.with(getContext()).load(StringUtils.trimToNull((String) post.getProperty(CBHelper.POST_KEY_ICON))).into(ivIcon);

        return convertView;
    }

}
