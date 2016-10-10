package com.example.isaac.directorioudg.gaceta;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.gaceta.ContenidosGacetaRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListGacetaActivity extends AppCompatActivity {

    @Bind(R.id.spinnerYears)
    Spinner spinnerYears;
    @Bind(R.id.spinnerMonts)
    Spinner spinnerMonts;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ContenidosGacetaRepositoryImpl repository;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gaceta);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        repository= new ContenidosGacetaRepositoryImpl();

        repository.descargarDatosContenidoGacetaCompletos();
        setupSpinnerYears();
        setupSpinnerMonts();
        showSnackbar("max: "+repository.getMaxId());


    }

    public void setupSpinnerYears(){
        List<String> list = new ArrayList<String>();
        for(int i=2016; i>1995;i--){
            list.add(""+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYears.setAdapter(adapter);
    }
    public void setupSpinnerMonts(){
        List<String> list = new ArrayList<String>();
        for(int i=1; i<=12;i++){
            list.add(""+i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonts.setAdapter(adapter);
    }
    private void showSnackbar(String msg) {
        Snackbar.make(getWindow().findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
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
