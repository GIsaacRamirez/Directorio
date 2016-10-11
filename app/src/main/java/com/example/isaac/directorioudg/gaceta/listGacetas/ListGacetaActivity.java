package com.example.isaac.directorioudg.gaceta.listGacetas;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import com.example.isaac.directorioudg.gaceta.ContenidosGacetaRepositoryImpl;
import com.example.isaac.directorioudg.gaceta.listGacetas.adapters.GacetasAdapter;
import com.example.isaac.directorioudg.util.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListGacetaActivity extends AppCompatActivity {

    Helper helper = new Helper(this);
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.spinnerYears)
    Spinner spinnerYears;
    @Bind(R.id.spinnerMonts)
    Spinner spinnerMonts;
    SharedPreferences prefs;
    private List<ContenidoGaceta> GacetaList = new ArrayList<>();
    private  GacetasAdapter adapter = null;


    private ContenidosGacetaRepositoryImpl repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gaceta);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupSpinnerYears();
        setupSpinnerMonts();

        setupGacetaListAdapter();

        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean firstStart = prefs.getBoolean("firstStartGaceta", true);
        if (firstStart) {
            SharedPreferences.Editor editor = prefs.edit();
            if (helper.isConect()) {
                repository = new ContenidosGacetaRepositoryImpl();
                repository.descargarDatosContenidoGacetaCompletos();
            }
            editor.putBoolean("firstStartGaceta", false);
            // commits your edits
            editor.commit();
        }
        setupRecyclerView();
        showSnackbar("max: " + repository.getMaxId());
    }

    public static String numMestoText(int num) {
        String result = "";
        switch (num) {
            case 0: {
                result = "Enero";
                break;
            }
            case 1: {
                result = "Febrero";
                break;
            }
            case 2: {
                result = "Marzo";
                break;
            }
            case 3: {
                result = "Abril";
                break;
            }
            case 4: {
                result = "Mayo";
                break;
            }
            case 5: {
                result = "Junio";
                break;
            }
            case 6: {
                result = "Julio";
                break;
            }
            case 7: {
                result = "Agosto";
                break;
            }
            case 8: {
                result = "Septiembre";
                break;
            }
            case 9: {
                result = "Octubre";
                break;
            }
            case 10: {
                result = "Noviembre";
                break;
            }
            case 11: {
                result = "Diciembre";
                break;
            }
            default: {
                result = null;
                break;
            }
        }
        return result;
    }

    public void setupSpinnerYears() {
        List<String> list = new ArrayList<String>();
        for (int i = 2016; i > 1995; i--) {
            list.add("" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinnertitle, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYears.setAdapter(adapter);
    }

    public void setupSpinnerMonts() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 12; i++) {
            String aux = numMestoText(i);
            if (aux != null) {
                list.add(aux);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinnertitle, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonts.setAdapter(adapter);

    }

    /////////////////////////////////////

    public void setupGacetaListAdapter() {
        adapter = new GacetasAdapter(GacetaList,getApplicationContext());
    }

    public final void setContenidoList(GacetasAdapter adapter){
        //adapter.setList(presenter.getPrepas(filter));
    }


    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
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
