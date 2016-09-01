package com.example.isaac.directorioudg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListRepository;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListRepositoryImpl;
import com.example.isaac.directorioudg.listcentros.CentroListRepository;
import com.example.isaac.directorioudg.listcentros.CentroListRepositoryImpl;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Bind(R.id.CmbToolbar)
    Spinner CmbToolbar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private GoogleMap mMap;
    private CameraUpdate mCamera;

    String[] datos;

    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbar);
    }
    private void setUpSpinner(){
        datos= new String[]{"Todo","Metropolitanos","Regionales","Centros Universitarios","CU Tematicos", "CU Regionales",
        "Preparatorias (Pre)", "Pre Metropolitanas", "Pre Regionales"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                android.R.layout.simple_spinner_item,
                datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CmbToolbar.setAdapter(adapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        setToolbar();
        setUpSpinner();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        CmbToolbar.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        loadMarkers(position);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


    }

    public void loadMarkers(int filter){
        final int  foraneas=7;
        final int metropolitanas=11;
        int zoom=foraneas;

        PrepaListRepository prepaListRepository = new PrepaListRepositoryImpl(getApplicationContext());
        CentroListRepository centroListRepository = new CentroListRepositoryImpl(getApplicationContext());

        List<Prepa> prepaList = new ArrayList<>();
        List<Centro> centroList=new ArrayList<>();
        mMap.clear();

        switch (filter){
            case 0:
                prepaList=prepaListRepository.getListPrepas(0);
                centroList=centroListRepository.getListCentro(0);
                zoom=foraneas;

                break;
            case 1:
                prepaList=prepaListRepository.getListPrepas(1);
                centroList=centroListRepository.getListCentro(1);
                zoom=metropolitanas;
                break;
            case 2:
                prepaList= prepaListRepository.getListPrepas(2);
                centroList=centroListRepository.getListCentro(2);
                zoom=foraneas;
                break;
            case 3:
                centroList=centroListRepository.getListCentro(0);
                zoom=foraneas;
                break;
            case 4:
                centroList=centroListRepository.getListCentro(1);
                zoom=metropolitanas;
                break;
            case 5:
                centroList=centroListRepository.getListCentro(2);
                zoom=foraneas;
                break;
            case 6:
                prepaList=prepaListRepository.getListPrepas(0);
                zoom=foraneas;
                break;
            case 7:
                prepaList=prepaListRepository.getListPrepas(1);
                zoom=metropolitanas;
                break;
            case 8:
                prepaList=  prepaListRepository.getListPrepas(2);
                zoom=foraneas;
                break;
        }

        if(!prepaList.isEmpty()){
            for (Prepa prepa : prepaList) {
                LatLng coordenadas = new LatLng(prepa.getLatitud(), prepa.getLongitud());
                mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .title("Prepa "+prepa.getPreparatoria()));
            }
        }

        if(!centroList.isEmpty()) {
            for (Centro centro : centroList) {
                LatLng coordenadas = new LatLng(centro.getLatitud(), centro.getLongitud());
                mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_centros))
                        .title(centro.getSigla()));
            }
        }

        mCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(20.675356, -103.358919), zoom);
        mMap.animateCamera(mCamera);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        loadMarkers(0);
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(guadalajara));

        mCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(20.675356, -103.358919), 11);
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
}
