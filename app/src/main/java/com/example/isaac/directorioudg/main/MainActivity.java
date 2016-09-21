package com.example.isaac.directorioudg.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.isaac.directorioudg.MapActivity;
import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.listaprepasrecycler.ui.PrepaList;
import com.example.isaac.directorioudg.listcentros.ui.CentroList;
import com.example.isaac.directorioudg.pdfView;
import com.example.isaac.directorioudg.radio.RadioList.RadioList;
import com.example.isaac.directorioudg.util.Helper;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Helper helper = new Helper(this);
    SharedPreferences prefs;
    @Bind(R.id.CmbToolbar)
    public Spinner CmbToolbar;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nav_view)
    NavigationView navView;
    int isPrepa;
    PrepaList fragmentPrepaList = new PrepaList();
    CentroList fragmentCentroList = new CentroList();

    public void setupSpinner(){
        String[] datos= new String[]{"Todo", "Metropolitanas", "Regionales"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getSupportActionBar().getThemedContext(),
                android.R.layout.simple_spinner_item,
                datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CmbToolbar.setAdapter(adapter);
    }

    public void loadPrepaList( ){

        isPrepa=0;
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.content_main, fragmentPrepaList);
        fragmentTransaction.commit();
        setTitle("Prepas");
        setupSpinner();
        CmbToolbar.setVisibility(View.VISIBLE);
    }

    public void loadCentroList(){
        isPrepa = 1;
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.content_main, fragmentCentroList);
        fragmentTransaction.commit();
        setTitle("Centros");
        setupSpinner();
        CmbToolbar.setVisibility(View.VISIBLE);
    }

    public void loadRadioList(){

        RadioList fragmentRadioList = new RadioList();
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, fragmentRadioList);
        fragmentTransaction.commit();
        setTitle("Radio");
        CmbToolbar.setVisibility(View.GONE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {
            SharedPreferences.Editor editor = prefs.edit();
            if (helper.isConect()) {
                fragmentPrepaList.getPresenter().descargarPrepas();
                fragmentCentroList.getPresenter().descargarCentros();
            }
            editor.putBoolean("firstStart", false);
            // commits your edits
            editor.commit();
        }

        loadPrepaList();

        CmbToolbar.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        if (isPrepa==0){
                            fragmentPrepaList.setPrepaList(position,fragmentPrepaList.getPrepaListAdapter());
                        }else if(isPrepa==1){
                            fragmentCentroList.setCentroList(position,fragmentCentroList.getCentroListAdapter());
                        }
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });


        /*Opciones del navigation drawer*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
    }

    /*Cuando presiona el navigation drawer*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Menu de los 3 puntitos o settings
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Seleccionar Opciones del menu de los 3 puntitos
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_actualizar) {
            if (helper.isConect()) {

                fragmentPrepaList.getPresenter().descargarPrepas();
                fragmentCentroList.getPresenter().descargarCentros();

                if(isPrepa==0){
                    showSnackbar("Se esta actualizando la informacion");
                }else if(isPrepa==1){
                    showSnackbar("Se esta actualizando la informacion");
                }
            }else{
                showSnackbar("Necesita conexion a internet");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSnackbar(String msg) {
        Snackbar.make(getWindow().findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
    }
    /**
     * Presionar las opciones del navigation drawer
     **/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_Prepas) {
            loadPrepaList();
        } else if (id == R.id.nav_Centros) {
            loadCentroList();
        }else if(id == R.id.nav_Radio){
            loadRadioList();
        } else if (id == R.id.nav_Map) {

            Intent intent = new Intent(this, MapActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putBoolean("coordenadaVacia",true);
            intent.putExtras(bundle);//ponerlos en el intent
            startActivity(intent);
        } else if (id == R.id.nav_manage) {/*Tools PDF*/
            Intent intent = new Intent(this, pdfView.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } else if (id == R.id.nav_share) {



        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
