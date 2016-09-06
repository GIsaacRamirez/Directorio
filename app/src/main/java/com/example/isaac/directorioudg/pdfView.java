package com.example.isaac.directorioudg;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.ScrollBar;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class pdfView extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL = 1;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbar);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_view);
        ButterKnife.bind(this);
        setToolbar();

        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=
        PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=
        PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_EXTERNAL);
            }
            return;
        }
        loadGaceta();
}

    private void loadGaceta() {
        String directory = "/sdcard/";

        File file = new File(directory, "sample.pdf");

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        ScrollBar scrollBar = (ScrollBar) findViewById(R.id.scrollBar);
        scrollBar.setHorizontal(true);
        pdfView.setScrollBar(scrollBar);


        pdfView.fromFile(file)
                .enableSwipe(true)
                .enableDoubletap(true)
                .swipeVertical(false)
                .defaultPage(1)
                .showPageWithAnimation(true)
                .showMinimap(true)
                .load();

        setTitle("Gaceta");
    }

    //Respuesta de los permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_EXTERNAL: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadGaceta();
                } else {
                    showSnackbar("Es necesario el permiso para mostrar la gaceta");
                }
            }
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
    private void showSnackbar(String msg) {
        Snackbar.make(getWindow().findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
    }
}
