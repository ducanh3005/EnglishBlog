package com.tune.englishblog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.melnykov.fab.FloatingActionButton;
import com.tune.englishblog.services.PostsSynchronizerService;
import com.tune.englishblog.util.PrefUtil;


/**
 * An activity representing a list of Posts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PostDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link PostListFragment} and the item details
 * (if present) is a {@link PostDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link PostListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class PostListActivity extends FragmentActivity
        implements PostListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private FloatingActionButton drawerHideReadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        PostsSynchronizerService.startActionSyncPosts(this);

        // Load AdMob
        AdView mAdView = (AdView) findViewById(R.id.adTopView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("D8466940C49144F6336111363C348DCB").build();
        mAdView.loadAd(adRequest);

        // Show/Hide read posts
        drawerHideReadButton = (FloatingActionButton) findViewById(R.id.hide_read_button);

        if (findViewById(R.id.post_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((PostListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.post_list))
                    .setActivateOnItemClick(true);
        }
    }

    /**
     * Callback method from {@link PostListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(PostDetailFragment.ARG_ITEM_ID, id);
            PostDetailFragment fragment = new PostDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.post_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, PostDetailActivity.class);
            detailIntent.putExtra(PostDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    public void onClickShowHideRead(View view) {
        if (!PrefUtil.get(view.getContext(), PrefUtil.SHOW_READ, true)) {
            PrefUtil.put(view.getContext(), PrefUtil.SHOW_READ, true);
            drawerHideReadButton.setColorNormalResId(R.color.color_primary);
            Toast.makeText(this, R.string.context_menu_show_read, Toast.LENGTH_SHORT).show();
        } else {
            PrefUtil.put(view.getContext(), PrefUtil.SHOW_READ, false);
            drawerHideReadButton.setColorNormalResId(R.color.floating_action_button_disabled);
            Toast.makeText(this, R.string.context_menu_hide_read, Toast.LENGTH_SHORT).show();
        }
    }
}
