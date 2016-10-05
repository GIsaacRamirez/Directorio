package com.example.isaac.directorioudg.detallecentro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaac.directorioudg.MapActivity;
import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;
import com.example.isaac.directorioudg.listcentros.CentroListRepositoryImpl;
import com.example.isaac.directorioudg.util.Helper;
import com.example.isaac.directorioudg.util.zoom;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetalleCentroActivity extends AppCompatActivity implements OnMapReadyCallback {


    @Bind(R.id.image_paralax)
    ImageView imageParalax;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapser)
    CollapsingToolbarLayout collapser;
    @Bind(R.id.nombreCentro)
    TextView nombreCentro;
    @Bind(R.id.direccionCentro)
    TextView direccionCentro;
    @Bind(R.id.codigoPostalCentro)
    TextView codigoPostalCentro;
    @Bind(R.id.DirWeb)
    TextView DirWeb;

    //CardRector
    @Bind(R.id.imageRector)
    ImageView imageRector;
    @Bind(R.id.lblNombreRector)
    TextView lblNombreRector;
    @Bind(R.id.txtTelefonoRector)
    TextView txtTelefonoRector;
    @Bind(R.id.txtCorreoRector)
    TextView txtCorreoRector;

    //Card Secretario academico
    @Bind(R.id.imageSecacademico)
    ImageView imageSecacademico;
    @Bind(R.id.lblNombreSecAcademico)
    TextView lblNombreSecAcademico;
    @Bind(R.id.txtTelefonoSecAcademico)
    TextView txtTelefonoSecAcademico;
    @Bind(R.id.txtCorreoSecAcademico)
    TextView txtCorreoSecAcademico;

    //Card secretario administrativo
    @Bind(R.id.imageSecAdministrativo)
    ImageView imageSecAdministrativo;
    @Bind(R.id.lblNombreSecAdministrativo)
    TextView lblNombreSecAdministrativo;
    @Bind(R.id.txtTelefonoSecAdministrativo)
    TextView txtTelefonoSecAdministrativo;
    @Bind(R.id.txtCorreoSecAdministrativo)
    TextView txtCorreoSecAdministrativo;
    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    Helper helper = new Helper(this);
    private GoogleMap mMap;

    private CameraUpdate mCamera;
    private Double Latitud, Longitud;
    String Estado = "Jalisco";

    Centro centro = new Centro();
    ImageLoader imageLoader;
    CentroListRepositoryImpl repository;

    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbar);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_centro);
        ButterKnife.bind(this);
        setToolbar();// Añadir action bar
        repository = new CentroListRepositoryImpl();
        imageLoader = new GlideImageLoader(this.getApplicationContext());

        Bundle bundle = this.getIntent().getExtras();
        centro = bundle.getParcelable("centro");
        map.onCreate(null);
        map.setDrawingCacheEnabled(true);
        map.getDrawingCache();
        map.getMapAsync(this);

        setDataInView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareCentro();
            }
        });
    }

    private void setDataInView() {
        collapser.setTitle(centro.getSigla());

        //Card centro
        nombreCentro.setText(centro.getNombreCentro());
        String dir = "Direccion:" + centro.getDireccion();
        dir = dir.replaceAll("\n", "");
        String municipio = centro.getMunicipio();
        String direccion = dir + ", " + municipio + ", " + Estado;
        direccionCentro.setText(direccion);
        codigoPostalCentro.setText("Codigo Postal: " + centro.getCP());
        DirWeb.setText(centro.getWeb());
        String url = centro.getImagenURL().toString();
        if (url.equalsIgnoreCase("No Disponible")) {
            imageLoader.load(imageParalax,R.drawable.fotolugarvacio);
        }else {
            imageLoader.load(imageParalax, url, true);
        }

        Latitud = centro.getLatitud();
        Longitud = centro.getLongitud();

        //CardRector
        if (helper.isConect()) {
            imageLoader.load(imageRector, centro.getFotoRectorURL(), false);
            imageLoader.load(imageSecacademico, centro.getFotoSecAcademicoURL(), false);
            imageLoader.load(imageSecAdministrativo, centro.getFotoSecAdministrativoURL(), false);
        }else {
            imageLoader.load(imageRector, R.drawable.fotonodisponible);
            imageLoader.load(imageSecacademico, R.drawable.fotonodisponible);
            imageLoader.load(imageSecAdministrativo, R.drawable.fotonodisponible);
        }
        lblNombreRector.setText(centro.getRector());
        txtTelefonoRector.setText(centro.getTelefonoRector());
        txtCorreoRector.setText(centro.getCorreoRector());
        //Card secretario academico

        lblNombreSecAcademico.setText(centro.getSecretarioAcademico());
        txtTelefonoSecAcademico.setText(centro.getTelefonoSecAcademico());
        txtCorreoSecAcademico.setText(centro.getCorreoSecAcademico());
        //Card secretario administrativo

        lblNombreSecAdministrativo.setText(centro.getSecretarioAdministrativo());
        txtTelefonoSecAdministrativo.setText(centro.getTelefonoSecAdministrativo());
        txtCorreoSecAdministrativo.setText(centro.getCorreoSecAdministrativo());

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final String title = "Centro " + centro.getSigla();

        final LatLng centro = new LatLng(Latitud, Longitud);
        mMap.addMarker(new MarkerOptions()
                .position(centro)
                .title(title));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(guadalajara));
        mCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(Latitud, Longitud), 15);
        mMap.animateCamera(mCamera);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putBoolean("coordenadaVacia",false);
                bundle.putDouble("Latitud",centro.latitude);
                bundle.putDouble("Longitud",centro.longitude);
                bundle.putBoolean("isPrepa",false);
                bundle.putString("Name",title);
                intent.putExtras(bundle);//ponerlos en el intent
                startActivity(intent);

            }
        });
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

    @OnClick({R.id.layoutweb, R.id.txtCorreoRector, R.id.txtCorreoSecAcademico, R.id.txtCorreoSecAdministrativo, R.id.image_paralax})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutweb:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(centro.getWeb()));
                startActivity(intent);
                break;
            case R.id.txtCorreoRector:
                sendEmail(txtCorreoRector.getText().toString());
                break;
            case R.id.txtCorreoSecAcademico:
                sendEmail(txtCorreoSecAcademico.getText().toString());
                break;
            case R.id.txtCorreoSecAdministrativo:
                sendEmail(txtCorreoSecAdministrativo.getText().toString());
                break;
            case  R.id.image_paralax:
                zoomImage(imageParalax);
                break;
        }

    }


    private void sendEmail(String emailTo) {
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
        startActivity(Intent.createChooser(email, "Seleccionar aplicación"));
    }
    private void shareCentro() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String aux = "Centro: " + centro.getNombreCentro();
        aux+=" \nDir. " + centro.getDireccion() + ", " + centro.getMunicipio() + "Jalisco";
        aux += "\nCP:" + centro.getCP();
        aux += "\n" + centro.getWeb();
        aux += "\nRector: " + centro.getRector() +"\n Tel."+centro.getTelefonoRector() +"\n email: " + centro.getCorreoRector();
        aux += "\nSec. Academico: " + centro.getSecretarioAcademico() +"\n Tel."+centro.getTelefonoSecAcademico() +"\n email: " + centro.getCorreoSecAcademico();
        aux += "\nSec. Administrativo: " + centro.getSecretarioAdministrativo() +"\n Tel."+centro.getTelefonoSecAdministrativo() +"\n email: " + centro.getCorreoSecAdministrativo();

        intent.putExtra(Intent.EXTRA_TEXT, aux);

        startActivity(Intent.createChooser(intent, "Compartir"));
    }


    private void zoomImage(ImageView imageView){
        //pasamos el ImageView al metodo imageFileCache para que se pueda compartir la imagen
        String dirfile=imageFileChache(imageView);

        Intent intentzoom = new Intent(this, zoom.class);
        intentzoom.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putString("urlfile",dirfile);
        intentzoom.putExtras(bundle);//ponerlos en el intent
        startActivity(intentzoom);//iniciar la actividad
    }
    @Nullable
    private String imageFileChache(ImageView imageview){
        imageview.getAdjustViewBounds();
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
            showSnackbar("Ha ocurrido un error");
            return null;
        }
    }

    private void showSnackbar(String msg) {
        Snackbar.make(getWindow().findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
    }

}

