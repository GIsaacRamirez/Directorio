package com.example.isaac.directorioudg.detalleprepa;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.isaac.directorioudg.MapActivity;
import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;
import com.example.isaac.directorioudg.listaprepas.PrepaListRepositoryImpl;
import com.example.isaac.directorioudg.util.Helper;
import com.example.isaac.directorioudg.util.zoom;
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

    Helper helper = new Helper(this);
    PrepaListRepositoryImpl repository;

    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbar);
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
        mapView.setDrawingCacheEnabled(true);
        mapView.getDrawingCache();
        mapView.getMapAsync(this);

        setDataInView();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePrepa();
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

        imageLoader.load(imageParalax, url, true,R.drawable.fotolugarvacio);


        imageLoader.load(imageDirector, prepa.getFotoDirectorURL(), false,R.drawable.fotonodisponible);


        Latitud = prepa.getLatitud();
        Longitud = prepa.getLongitud();

        lblDirector.setText(prepa.getDirector().trim());

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


    @OnClick({R.id.layoutweb, R.id.lblCorreoDirector, R.id.lblCorreoSecretario,R.id.image_paralax,R.id.btnVisualizar})
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

            case  R.id.image_paralax:
                zoomImage(imageParalax);
                break;
            case R.id.btnVisualizar:
                Intent intentmap = new Intent(getApplicationContext(), MapActivity.class);
                intentmap.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putBoolean("coordenadaVacia", false);
                bundle.putDouble("Latitud", prepa.getLatitud());
                bundle.putDouble("Longitud", prepa.getLongitud());
                bundle.putBoolean("isPrepa", true);
                bundle.putString("Name", "Preparatoria " + prepa.getPreparatoria());
                intentmap.putExtras(bundle);//ponerlos en el intent
                startActivity(intentmap);
                break;

        }
    }

    private void zoomImage(ImageView imageView){

        Intent intentzoom = new Intent(this, zoom.class);
        intentzoom.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putString("urlfile",prepa.getImagenURL());
        intentzoom.putExtras(bundle);//ponerlos en el intent
        startActivity(intentzoom);//iniciar la actividad
    }

    private void sendEmail(String emailTo) {
        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:"));
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
        try {
            startActivity(Intent.createChooser(email, "Seleccionar aplicación"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "No hay un cliente de correo instalado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sharePrepa() {
        String aux = "Preparatoria: " + prepa.getPreparatoria();
        aux+=" \n Imagen: "+prepa.getImagenURL()+"\n";
        aux +=" \nDir. " + prepa.getDireccion() + ", " + prepa.getMunicipio() + "Jalisco";
        aux += "\nCP:" + prepa.getCP();
        aux += "\n" + telefonosPrep.getText() + " \n" + prepa.getWEB();
        aux += "\nDirector: " + prepa.getDirector() + " email: " + prepa.getCorreoDirector();
        aux += "\nSecretario: " + prepa.getSecretario() + " email: " + prepa.getCorreoSecretario();
        //aux += "\nImagen: " +prepa.getImagenURL();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, aux);
        sendIntent.setType("text/plain");
        Intent i = Intent.createChooser(sendIntent, "Compartir");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // startActivityForResult(i,SEND_REQUEST);
        startActivityForResult(i, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_CANCELED) {
                // Operation failed or cancelled. Handle in your own way.
            }
        }
    }



}

