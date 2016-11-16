package com.example.isaac.directorioudg.detallecentro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaac.directorioudg.MapActivity;
import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.detallecentro.ui.trabajador_centro;
import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;
import com.example.isaac.directorioudg.listcentros.CentroListRepositoryImpl;
import com.example.isaac.directorioudg.util.zoom;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.fab)
    FloatingActionButton fab;


    @Bind(R.id.cardCentro)
    CardView cardCentro;
    @Bind(R.id.cardMap)
    CardView cardMap;
    @Bind(R.id.nestedscroll)
    NestedScrollView nestedscroll;

    private GoogleMap mMap;

    private CameraUpdate mCamera;
    private Double Latitud, Longitud;
    String Estado = "Jalisco";

    Centro centro = new Centro();
    ImageLoader imageLoader;
    CentroListRepositoryImpl repository;
    trabajador_centro fragmentTrabajadorCentro = new trabajador_centro();


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
            imageLoader.load(imageParalax, R.drawable.fotolugarvacio,R.drawable.fotolugarvacio);
        } else {
            imageLoader.load(imageParalax, url, true,R.drawable.fotolugarvacio);
        }

        Latitud = centro.getLatitud();
        Longitud = centro.getLongitud();

        Bundle bundle = new Bundle();
        bundle.putInt("idcentro", centro.getIdCentro());
        fragmentTrabajadorCentro.setArguments(bundle);
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragmentTrabajadorCentro);
        fragmentTransaction.commit();
        nestedscroll.requestChildFocus(cardCentro, cardCentro);//Visualizar primero

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final LatLng latlongcentro = new LatLng(Latitud, Longitud);
        mMap.addMarker(new MarkerOptions()
                .position(latlongcentro)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_centros))
                .title(centro.getSigla()));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(guadalajara));
        mCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(Latitud, Longitud), 15);
        mMap.animateCamera(mCamera);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

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

    @OnClick({R.id.layoutweb, R.id.image_paralax,R.id.btnVisualizar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutweb:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(centro.getWeb()));
                startActivity(intent);
                break;

            case R.id.image_paralax:
                zoomImage(imageParalax);
                break;
            case R.id.btnVisualizar:
                Intent intentmap = new Intent(getApplicationContext(), MapActivity.class);
                intentmap.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putBoolean("coordenadaVacia", false);
                bundle.putDouble("Latitud", centro.getLatitud());
                bundle.putDouble("Longitud", centro.getLongitud());
                bundle.putBoolean("isPrepa", false);
                bundle.putString("Name", centro.getSigla());
                intentmap.putExtras(bundle);//ponerlos en el intent
                startActivity(intentmap);
                break;
        }
    }


    private void shareCentro() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String aux = "Centro: " + centro.getNombreCentro();
        aux += " \n Imagen: " + centro.getImagenURL() + "\n";
        aux += " \nDir. " + centro.getDireccion() + ", " + centro.getMunicipio() + "Jalisco";
        aux += "\nCP:" + centro.getCP();
        aux += "\n" + centro.getWeb();

        intent.putExtra(Intent.EXTRA_TEXT, aux);

        startActivity(Intent.createChooser(intent, "Compartir"));
    }


    private void zoomImage(ImageView imageView) {
        //pasamos el ImageView al metodo imageFileCache para que se pueda compartir la imagen

        Intent intentzoom = new Intent(this, zoom.class);
        intentzoom.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putString("urlfile", centro.getImagenURL());
        intentzoom.putExtras(bundle);//ponerlos en el intent
        startActivity(intentzoom);//iniciar la actividad
    }

}

