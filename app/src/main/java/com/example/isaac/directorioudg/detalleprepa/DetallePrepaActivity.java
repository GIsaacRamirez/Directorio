package com.example.isaac.directorioudg.detalleprepa;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListRepositoryImpl;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetallePrepaActivity extends AppCompatActivity implements OnMapReadyCallback {


    @Bind(R.id.imageDirector)
    ImageView imageDirector;
    private GoogleMap mMap;
    private CameraUpdate mCamera;
    private Double Latitud, Longitud;
    String Estado = "Jalisco";

    Prepa prepa = new Prepa();

    ImageLoader imageLoader;
    @Bind(R.id.collapser)
    CollapsingToolbarLayout collapser;
    @Bind(R.id.web)
    TextView txtweb;
    @Bind(R.id.image_paralax)
    ImageView imageParalax;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nombrePrep)
    TextView nombrePrep;
    @Bind(R.id.direccionPrep)
    TextView direccionPrep;
    @Bind(R.id.codigoPostalPrep)
    TextView codigoPostalPrep;
    @Bind(R.id.telefonosPrep)
    TextView telefonosPrep;
    @Bind(R.id.lblDirector)
    TextView lblDirector;
    @Bind(R.id.lblCorreoDirector)
    TextView lblCorreoDirector;
    @Bind(R.id.lblSecretario)
    TextView lblSecretario;
    @Bind(R.id.lblCorreoSecretario)
    TextView lblCorreoSecretario;
    @Bind(R.id.map)
    MapView mapView;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    PrepaListRepositoryImpl repository;

    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);// Habilitar up button
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_prepa);
        ButterKnife.bind(this);
        setToolbar();// Añadir action bar
        repository = new PrepaListRepositoryImpl();
        imageLoader = new GlideImageLoader(this.getApplicationContext());

        Bundle bundle = this.getIntent().getExtras();
        prepa = bundle.getParcelable("prepa");
        mapView.onCreate(null);
        mapView.getMapAsync(this);

        setDataInView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    private void setDataInView() {
        nombrePrep.setText("Preparatoria: " + prepa.getPreparatoria());
        String dir = "Direccion:" + prepa.getDireccion();
        dir = dir.replaceAll("\n", "");
        String municipio = prepa.getMunicipio();
        String direccion = dir + ", " + municipio + ", " + Estado;
        direccionPrep.setText(direccion);
        codigoPostalPrep.setText("Codigo Postal: " + prepa.getCP());
        String telefono1 = prepa.getTelefono1();
        String telefono2 = prepa.getTelefono2();
        if (telefono2.equalsIgnoreCase("No disponible")) {
            telefonosPrep.setText("Telefonos: " + telefono1);
        } else {
            telefonosPrep.setText("Telefonos: " + telefono1 + ", " + telefono2);
        }


        txtweb.setText(prepa.getWEB());
        String url = prepa.getImagenURL().toString();
        if (url.equalsIgnoreCase("No Disponible")) {
            url = "http://appdirectorioudg.com/photo.jpg";
        }
        imageLoader.load(imageParalax, url, true);

        Latitud = prepa.getLatitud();
        Longitud = prepa.getLongitud();

        lblDirector.setText(prepa.getDirector().trim());
        imageLoader.load(imageDirector, prepa.getFotoDirectorURL(), false);
        lblCorreoDirector.setText(prepa.getCorreoDirector().trim());
        lblSecretario.setText(prepa.getSecretario().trim());
        lblCorreoSecretario.setText(prepa.getCorreoSecretario().trim());
        collapser.setTitle(prepa.getPreparatoria());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String title = "Preparatoria " + prepa.getPreparatoria();

        final LatLng preparatoria = new LatLng(Latitud, Longitud);
        mMap.addMarker(new MarkerOptions()
                .position(preparatoria)
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
                bundle.putDouble("Latitud",prepa.getLatitud());
                bundle.putDouble("Longitud",prepa.getLongitud());
                bundle.putBoolean("isPrepa",true);
                bundle.putString("Name","Preparatoria " + prepa.getPreparatoria());
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


    @OnClick({R.id.layoutweb, R.id.lblCorreoDirector, R.id.lblCorreoSecretario})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutweb:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(prepa.getWEB()));
                startActivity(intent);
                break;
            case R.id.lblCorreoDirector:
                sendEmail(lblCorreoDirector.getText().toString());
                break;
            case R.id.lblCorreoSecretario:
                sendEmail(lblCorreoSecretario.getText().toString());
                break;
        }
    }

    public void sendEmail(String emailTo){
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
        startActivity(Intent.createChooser(email, "Seleccionar aplicación"));
    }
}

