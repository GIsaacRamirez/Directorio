package com.example.isaac.directorioudg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class zoom extends AppCompatActivity {

    @Bind(R.id.imagen_extendida)
    ImageViewTouch imagenExtendida;

    @Bind(R.id.image_paralax)
    ImageView imageParalax;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        ButterKnife.bind(this);
        setToolbar();
        Bundle bundle = this.getIntent().getExtras();
        String url = bundle.getString("url");
        loadImage(url);
    }

    private void loadImage(String url) {
        if (url.equalsIgnoreCase("No Disponible")) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.fotolugarvacio)
                    .fitCenter()
                    .into(imagenExtendida);
            Glide.with(getApplicationContext())
                    .load(R.drawable.fotolugarvacio)
                    .fitCenter()
                    .into(imageParalax);
        } else {
            Glide.with(getApplicationContext())
                    .load(url.toString())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(imagenExtendida);
            Glide.with(getApplicationContext())
                    .load(url.toString())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .fitCenter()
                    .into(imageParalax);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zoom, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_compartir:
                shareImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareImage(){
        imageParalax.buildDrawingCache();
        Bitmap bitmap = imageParalax.getDrawingCache();
        /***** COMPARTIR IMAGEN *****/
        try {
            File file = new File(imageParalax.getContext().getCacheDir(), bitmap + ".jpg");
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/jpg");
            startActivity(Intent.createChooser(intent, "Compartir"));
        } catch (Exception e) {
            e.printStackTrace();
            showSnackbar("Error por favor intente de nuevo");
        }
    }
    private void showSnackbar(String msg) {
        Snackbar.make(getWindow().findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
    }
    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbar);
    }


}
