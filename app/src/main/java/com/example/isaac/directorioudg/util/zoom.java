package com.example.isaac.directorioudg.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    String dirfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        ButterKnife.bind(this);
        setToolbar();
        imageLoader = new GlideImageLoader(this.getApplicationContext());
        Bundle bundle = this.getIntent().getExtras();
        dirfile = bundle.getString("urlfile");
        imageLoader.loadzoom(imagenExtendida, dirfile, true, false);
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

    /*@Nullable
    private String imageFileChache(ImageView imageview){
        //pasamos el ImageView al metodo imageFileCache para que se pueda compartir la imagen
        imageview.buildDrawingCache(true);
        Bitmap bitmap= imageview.getDrawingCache(true);
        File file;
        try {
            file = new File(imageview.getContext().getCacheDir(), bitmap + ".jpg");
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }*/

    /**private void shareImage(String urlfile) {
        String urlCache =imageFileChache(imageInvisible);
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(urlCache));
        intent.setType("image/jpg");
        startActivity(Intent.createChooser(intent, "Compartir"));
    }**/

    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbar);
    }

    private void deleteCache(String pArchivo) throws Exception {
        try {
            File fichero = new File(pArchivo);
            if (!fichero.delete()) {
                Log.v("cache", "Archivo no eliminado");
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            deleteCache(dirfile);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("cache", "Archivo no eliminado");
        }
    }
}
