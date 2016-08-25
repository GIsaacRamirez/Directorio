package com.example.isaac.directorioudg.lib;

import android.content.Context;
import android.widget.ImageView;


public interface ImageLoader {
    public void setLoaderContext(Context context);
    void load(ImageView imageView, String URL,Boolean cache);
    void load(ImageView imageView, int resource);
    void setOnFinishedImageLoadingListener(Object object);
}
