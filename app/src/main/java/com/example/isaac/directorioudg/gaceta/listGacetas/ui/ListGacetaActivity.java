package com.example.isaac.directorioudg.gaceta.listGacetas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import com.example.isaac.directorioudg.gaceta.detalleGaceta.ui.detalleGaceta;
import com.example.isaac.directorioudg.gaceta.listGacetas.GacetaListPresenterImpl;
import com.example.isaac.directorioudg.gaceta.listGacetas.adapters.GacetasAdapter;
import com.example.isaac.directorioudg.gaceta.listGacetas.adapters.OnItemClickListener;
import com.example.isaac.directorioudg.util.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListGacetaActivity extends AppCompatActivity implements OnItemClickListener{

    Helper helper = new Helper(this);
    private GacetaListPresenterImpl presenter;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.spinnerYears)
    Spinner spinnerYears;
    @Bind(R.id.spinnerMonts)
    Spinner spinnerMonts;
    private List<ContenidoGaceta> GacetaList = new ArrayList<>();
    private  GacetasAdapter adapter = null;

    List<String> listyear = new ArrayList<String>();
    List<String> listmonts = new ArrayList<String>();
    String anyo;
    int anyoactual;


    public GacetasAdapter getAdapter() {
        return adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_gaceta);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //Se guarda el año actual para llenar el spinner hasta dicho año
        anyo = (DateFormat.format("yyyy", new java.util.Date()).toString());
        anyo=anyo.trim();
        anyoactual=Integer.parseInt(anyo);



        setupSpinnerYears(anyoactual);
        setupSpinnerMonts();

        setupGacetaListAdapter();
        presenter = new GacetaListPresenterImpl(getAdapter());
        GacetaList= presenter.getContenidoGacetas();

        if (GacetaList.isEmpty()) {
            if (helper.isConect()) {
                presenter.descargarContenidoGacetas();
            } else {
                Toast.makeText(this,
                        R.string._listaprepasrecycle_error_conexion,
                        Toast.LENGTH_SHORT).show();
            }
        }
        spinnerYears.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        //Se carga cada vez el spinner de meses para que al seleccionar el año actual solo se carguen los que ya pasaron
                        setupSpinnerMonts();
                        mostrarporfecha(Integer.parseInt(spinnerYears.getSelectedItem().toString()),spinnerMonts.getSelectedItemPosition()+1);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
        spinnerMonts.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent,
                                               android.view.View v, int position, long id) {
                        mostrarporfecha(Integer.parseInt(spinnerYears.getSelectedItem().toString()),spinnerMonts.getSelectedItemPosition()+1);
                    }
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

        setupRecyclerView();
    }

    public void setupSpinnerYears(int anyolimite) {
        for (int i = anyolimite; i >= 1995; i--) {
            listyear.add("" + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinnertitle, listyear);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYears.setAdapter(adapter);
    }

    public void setupSpinnerMonts() {
        listmonts.clear();
        int mesLimite=12;
        //Si esta seleccionado el año actual entonces se verifica el mes actual y hasta ese se carga
        if(Integer.parseInt(spinnerYears.getSelectedItem().toString())==anyoactual){
            String mes= (DateFormat.format("MM ", new java.util.Date()).toString().trim());
            mesLimite=Integer.parseInt(mes);
        }
        for (int i = 0; i < mesLimite; i++) {
            String aux = numMestoText(i);
            if (aux != null) {
                listmonts.add(aux);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinnertitle, listmonts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonts.setAdapter(adapter);

        String mesS  = (DateFormat.format("MM", new java.util.Date()).toString());
        mesS.trim();
        int mes=Integer.parseInt(mesS)-1;
        spinnerMonts.setSelection(mes);
    }

    /////////////////////////////////////

    public void setupGacetaListAdapter() {
        adapter = new GacetasAdapter(GacetaList,getApplicationContext(),this);
    }


    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }

    public void mostrarporfecha(int anyo, int mes){

        presenter.getPorFecha(anyo, mes);
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

    @Override
    public void onItemClick(ContenidoGaceta contenidoGaceta) {
        Intent intent = new Intent(this.getApplicationContext(), detalleGaceta.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putInt("idGaceta",contenidoGaceta.getId());
        intent.putExtras(bundle);//ponerlos en el intent
        startActivity(intent);//iniciar la actividad
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
}
