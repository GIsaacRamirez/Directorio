package com.example.isaac.directorioudg.util;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public class zoom extends AppCompatActivity {

    @Bind(R.id.imagen_extendida)
    ImageViewTouch imagenExtendida;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    ImageLoader imageLoader;
    String dirfile=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        ButterKnife.bind(this);
        setToolbar();
        imageLoader = new GlideImageLoader(this.getApplicationContext());
        Bundle bundle = this.getIntent().getExtras();
        dirfile= bundle.getString("urlfile");
        imageLoader.loadzoom(imagenExtendida,dirfile,false,false);

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
                shareImage(dirfile);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void shareImage(String urlfile){
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(urlfile));
        intent.setType("image/jpg");
        startActivity(Intent.createChooser(intent, "Compartir"));
    }
    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbar);
    }

    private void  deleteCache(String pArchivo) throws Exception {
        try {
            File fichero = new File(pArchivo);
            if (!fichero.delete()) {
                Log.v("cache","Archivo no eliminado");
            }
        } catch (Exception e) {}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            deleteCache(dirfile);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("cache","Archivo no eliminado");
        }
    }
}
