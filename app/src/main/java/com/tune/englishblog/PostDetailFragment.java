package com.tune.englishblog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.couchbase.lite.Document;
import com.tune.englishblog.services.PostsSynchronizerService;
import com.tune.englishblog.util.CBHelper;
import com.tune.englishblog.util.Constants;

/**
 * A fragment representing a single Post detail screen.
 * This fragment is either contained in a {@link PostListActivity}
 * in two-pane mode (on tablets) or a {@link PostDetailActivity}
 * on handsets.
 */
public class PostDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Document mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            CBHelper cbHelper = new CBHelper(getActivity().getApplicationContext());

            if (getArguments().containsKey(ARG_ITEM_ID)) {
                // Load the dummy content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                mItem = cbHelper.getDatabaseInstance().getDocument((getArguments().getString(ARG_ITEM_ID)));
            }
        }
        catch(Exception e){
            Log.d(getClass().getName(), e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            String postContent = Constants.HTML_HEAD +
                                Constants.TITLE_START +(String) mItem.getProperty(PostsSynchronizerService.POST_KEY_TITLE) + Constants.TITLE_END +
                                (String) mItem.getProperty(PostsSynchronizerService.POST_KEY_CONTENT) +
                                Constants.HTML_BODY_END;
            WebView webView = ((WebView) rootView.findViewById(R.id.post_detail));
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadDataWithBaseURL("", postContent, Constants.MIME_TYPE, Constants.ENCODING, null);
        }

        return rootView;
    }
}
