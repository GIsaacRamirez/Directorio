package com.example.isaac.directorioudg;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListRepository;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListRepositoryImpl;
import com.example.isaac.directorioudg.listcentros.CentroListRepository;
import com.example.isaac.directorioudg.listcentros.CentroListRepositoryImpl;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient apiClient;
    private Location lastKnowLocation;

    private boolean resolvingError=false;

    private static final int REQUEST_RESOLVE_ERROR=0;
    private static final int PERMISSIONS_REQUEST_LOCATION=1;

    @Bind(R.id.CmbToolbar)
    Spinner CmbToolbar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private GoogleMap mMap;
    private CameraUpdate mCamera;
    Double Latitud;
    Double Longitud;
    Boolean isPrepa;
    String title;
    String[] datos;
    Boolean isBundleEmpty;

    private void setToolbar() {
        // Añadir la Toolbar
        setSupportActionBar(toolbar);
    }

    private void setUpSpinner() {
        datos = new String[]{"Todo", "Metropolitanos", "Regionales", "Centros Universitarios", "CU Tematicos", "CU Regionales",
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

        setupGoogleAPIClient();

        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        setToolbar();
        setUpSpinner();
        Bundle bundle = getIntent().getExtras();
        isBundleEmpty = bundle.getBoolean("coordenadaVacia");
        if (!isBundleEmpty) {
            CmbToolbar.setVisibility(View.GONE);
            isPrepa = bundle.getBoolean("isPrepa");
            title = bundle.getString("Name");
            Latitud = bundle.getDouble("Latitud");
            Longitud = bundle.getDouble("Longitud");
        }

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

    public void loadMarkers(int filter) {
        final int foraneas = 7;
        final int metropolitanas = 11;
        int zoom = foraneas;

        PrepaListRepository prepaListRepository = new PrepaListRepositoryImpl();
        CentroListRepository centroListRepository = new CentroListRepositoryImpl(getApplicationContext());

        List<Prepa> prepaList = new ArrayList<>();
        List<Centro> centroList = new ArrayList<>();
        mMap.clear();

        switch (filter) {
            case 0:
                prepaList = prepaListRepository.getListPrepas(0);
                centroList = centroListRepository.getListCentro(0);
                zoom = foraneas;

                break;
            case 1:
                prepaList = prepaListRepository.getListPrepas(1);
                centroList = centroListRepository.getListCentro(1);
                zoom = metropolitanas;
                break;
            case 2:
                prepaList = prepaListRepository.getListPrepas(2);
                centroList = centroListRepository.getListCentro(2);
                zoom = foraneas;
                break;
            case 3:
                centroList = centroListRepository.getListCentro(0);
                zoom = foraneas;
                break;
            case 4:
                centroList = centroListRepository.getListCentro(1);
                zoom = metropolitanas;
                break;
            case 5:
                centroList = centroListRepository.getListCentro(2);
                zoom = foraneas;
                break;
            case 6:
                prepaList = prepaListRepository.getListPrepas(0);
                zoom = foraneas;
                break;
            case 7:
                prepaList = prepaListRepository.getListPrepas(1);
                zoom = metropolitanas;
                break;
            case 8:
                prepaList = prepaListRepository.getListPrepas(2);
                zoom = foraneas;
                break;
        }

        if (!prepaList.isEmpty()) {
            for (Prepa prepa : prepaList) {
                LatLng coordenadas = new LatLng(prepa.getLatitud(), prepa.getLongitud());
                mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .title("Prepa " + prepa.getPreparatoria()));
            }
        }

        if (!centroList.isEmpty()) {
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
    //https://maps.googleapis.com/maps/api/directions/json?origin=20.6538880,-103.3257880&destination=20.6798740,-103.3506070&key=AIzaSyDR_ZCIiXBAP00ff2m9-Nc9VQxvbR8OeWs

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (isBundleEmpty) {
            loadMarkers(0);
            mCamera = CameraUpdateFactory.newLatLngZoom(new LatLng(20.675356, -103.358919), 11);
        } else {
            LatLng coordenadas = new LatLng(Latitud, Longitud);

            if (isPrepa) {
                mMap.addMarker(new MarkerOptions()
                        .position(coordenadas)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                        .title(title));
                mCamera = CameraUpdateFactory.newLatLngZoom(coordenadas, 11);
            } else {
                mMap.addMarker(new MarkerOptions()
                        .position(coordenadas).
                                icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_centros))
                        .title(title));
                mCamera = CameraUpdateFactory.newLatLngZoom(coordenadas, 11);
            }
        }
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(guadalajara));
        mMap.animateCamera(mCamera);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSIONS_REQUEST_LOCATION);
            }
            return;
        }
            mMap.setMyLocationEnabled(true);



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
    }



    private void showSnackbar(String msg){
        Snackbar.make(getWindow().findViewById(android.R.id.content),msg, Snackbar.LENGTH_SHORT).show();
    }
    private void setupGoogleAPIClient() {
        if (apiClient == null) {
            apiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }
    //METODOS DE LA CONEXION y peticion de permisos de localizacion
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS_REQUEST_LOCATION:{
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mMap.setMyLocationEnabled(true);
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        apiClient.connect();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (resolvingError){
            return;
        }else if (connectionResult.hasResolution()){
            resolvingError=true;
            try {
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }else {
            resolvingError=true;
            GoogleApiAvailability.getInstance().getErrorDialog(this,connectionResult.getErrorCode(), REQUEST_RESOLVE_ERROR).show();
        }
    }

    //Ciclo de vida
    @Override
    protected void onStop() {
        apiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //Boton de regreso
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
