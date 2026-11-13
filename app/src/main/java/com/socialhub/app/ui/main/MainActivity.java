package com.socialhub.app.ui.main;

import android.content.Intent;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import com.socialhub.app.R;
import com.socialhub.app.core.base.BaseActivity;
import com.socialhub.app.databinding.ActivityMainBinding;
import com.socialhub.app.ui.create.CreatePostActivity;
import com.socialhub.app.ui.explore.ExploreFragment;
import com.socialhub.app.ui.feed.FeedFragment;
import com.socialhub.app.ui.notifications.NotificationsFragment;
import com.socialhub.app.ui.profile.ProfileFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private final Fragment feedFragment = new FeedFragment();
    private final Fragment exploreFragment = new ExploreFragment();
    private final Fragment notificationsFragment = new NotificationsFragment();
    private final Fragment profileFragment = new ProfileFragment();

    @Override
    protected ActivityMainBinding inflateBinding(LayoutInflater inflater) {
        return ActivityMainBinding.inflate(inflater);
    }

    @Override
    protected void initViews() {
        // Load FeedFragment as initial screen
        loadFragment(feedFragment, "Feed");

        getBinding().bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_feed) {
                loadFragment(feedFragment, getString(R.string.nav_feed));
                return true;
            } else if (itemId == R.id.navigation_explore) {
                loadFragment(exploreFragment, getString(R.string.nav_explore));
                return true;
            } else if (itemId == R.id.navigation_create) {
                startActivity(new Intent(MainActivity.this, CreatePostActivity.class));
                return false; // Don't select create tab since it's an action
            } else if (itemId == R.id.navigation_notifications) {
                loadFragment(notificationsFragment, getString(R.string.nav_notifications));
                return true;
            } else if (itemId == R.id.navigation_profile) {
                loadFragment(profileFragment, getString(R.string.nav_profile));
                return true;
            }
            return false;
        });
    }

    private void loadFragment(Fragment fragment, String title) {
        getBinding().toolbar.setTitle(title);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }
}
