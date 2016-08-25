package com.example.isaac.directorioudg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Spinner;

import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListRepository;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListRepositoryImpl;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Bind(R.id.CmbToolbar)
    Spinner CmbToolbar;
    @Bind(R.id.toolbarMap)
    Toolbar toolbarMap;
    private GoogleMap mMap;
    private CameraUpdate mCamera;

    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbarMap);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);// Habilitar up button
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Map");
        setToolbar();

        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        PrepaListRepository repository = new PrepaListRepositoryImpl(getApplicationContext());
        List<Prepa> prepaList = repository.getListPrepas(0);

        if (!prepaList.isEmpty()) {
            for (Prepa prepa : prepaList) {
                LatLng coordenadas = new LatLng(prepa.getLatitud(), prepa.getLongitud());
                mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .title(prepa.getPreparatoria()));
            }
        }

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(guadalajara));

        mCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(20.675356, -103.358919), 11);
        mMap.animateCamera(mCamera);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
    }

}
