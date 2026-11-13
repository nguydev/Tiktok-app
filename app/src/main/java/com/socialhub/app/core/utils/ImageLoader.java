package com.socialhub.app.core.utils;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.socialhub.app.R;

/**
 * Centralized ImageLoader wrapping Glide for high performance caching and memory optimization.
 */
public class ImageLoader {

    public static void load(ImageView imageView, String url) {
        if (url == null || url.trim().isEmpty()) {
            imageView.setImageResource(R.drawable.ic_launcher_foreground);
            return;
        }

        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(imageView);
    }

    public static void loadAvatar(ImageView imageView, String url) {
        if (url == null || url.trim().isEmpty()) {
            imageView.setImageResource(R.drawable.ic_profile);
            return;
        }

        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(R.drawable.ic_profile)
                .error(R.drawable.ic_profile)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(imageView);
    }
}
