package com.example.isaac.directorioudg.lib;

import android.content.Context;
import android.icu.text.DateFormat;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.example.isaac.directorioudg.R;


public class GlideImageLoader implements ImageLoader {
    Context context;
    private RequestManager glideRequestManager;
    private RequestListener onFinishedImageLoadingListener;

    public GlideImageLoader() {
    }
    public GlideImageLoader(Context context) {
        this.context=context;
        this.glideRequestManager = Glide.with(context);
    }

    public void setLoaderContext(Context context) {
        this.glideRequestManager = Glide.with(context);
    }

    @Override
    public void loadzoom(ImageView imageView, String URL, Boolean cache, Boolean center) {
        if(cache){
            if(center){
                glideRequestManager
                        .load(URL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .error(R.drawable.fotolugarvacio)
                        .into(imageView);
            }else {
                glideRequestManager
                        .load(URL)
                        .error(R.drawable.fotolugarvacio)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            }
        }else {
            if(center){
                glideRequestManager
                        .load(URL)
                        .error(R.drawable.fotolugarvacio)
                        .centerCrop()
                        .into(imageView);
            }else {
                glideRequestManager
                        .load(URL)
                        .error(R.drawable.fotolugarvacio)
                        .into(imageView);
            }
        }

    }

    @Override
    public void load(ImageView imageView, String URL, Boolean cache) {

        //Con dropbox se sustituye www por dl
        //String URL= "https://dl.drop)box.com/sh/ismxakaopf6wma0/AABAAgcJdHcNYurN6MdBCgPNa/Voca-1.jpg";
        //Desde el hosting
        //String URL="http://appdirectorioudg.com/DirectorioUDG/FotosPrepas/guzman/prepa-guzman-2.jpg";

        //String URL="https://drive.google.com/uc?export=view&id=1myesQuyUdNizxecxY52qhwhYp4WGVgHyYQ";
        //String URL="https://drive.google.com/open?id=1Nl1-lJyN7TwB8mTlzPxTiZr58hM0Ub6mnw";
        //URL=URL.replace("open?id" ,"uc?export=view&id");
        if(cache){
            glideRequestManager
                    .load(URL)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageView);
        }else {
            glideRequestManager
                    .load(URL)
                    .centerCrop()
                    .into(imageView);
        }
    }

    @Override
    public void load(ImageView imageView, int resource) {

        if (onFinishedImageLoadingListener != null) {
            glideRequestManager
                    .load(resource)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .listener(onFinishedImageLoadingListener)
                    .into(imageView);
        } else {
            glideRequestManager
                    .load(resource)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageView);
        }
    }

    @Override
    public void setOnFinishedImageLoadingListener(Object listener) {
        try {
            this.onFinishedImageLoadingListener = (RequestListener) listener;
        } catch (ClassCastException e) {
            Log.e(this.getClass().getName(),e.getMessage());
        }
    }
}