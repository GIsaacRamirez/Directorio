package com.example.isaac.directorioudg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bumptech.glide.Glide;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class zoom extends AppCompatActivity {

    @Bind(R.id.imagen_extendida)
    ImageViewTouch imagenExtendida;

    ImageLoader imageLoader;
    @Bind(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        ButterKnife.bind(this);
        setToolbar();
        imageLoader = new GlideImageLoader(this.getApplicationContext());
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
        } else {
            Glide.with(getApplicationContext())
                    .load(url.toString())
                    .fitCenter()
                    .into(imagenExtendida);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbar);
    }


}
