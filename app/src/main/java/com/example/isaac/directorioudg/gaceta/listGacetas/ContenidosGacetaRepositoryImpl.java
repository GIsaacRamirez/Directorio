package com.example.isaac.directorioudg.gaceta.listGacetas;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.db.DirectorioDataBase;
import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import com.example.isaac.directorioudg.entities.ContenidoGaceta_Table;
import com.example.isaac.directorioudg.gaceta.listGacetas.ContenidosGacetaRepository;
import com.example.isaac.directorioudg.gaceta.listGacetas.adapters.GacetasAdapter;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by Usuario on 10/10/2016.
 */

public class ContenidosGacetaRepositoryImpl implements ContenidosGacetaRepository{
    Context context;
    Boolean adapterisEmpty=true;
    GacetasAdapter adapter=null;
    public ContenidosGacetaRepositoryImpl() {
        this.context = getContext().getApplicationContext();

    }
    public ContenidosGacetaRepositoryImpl(GacetasAdapter adapter) {
        this.context = getContext().getApplicationContext();
        this.adapter=adapter;
    }

    @Override
    public void descargarDatosContenidoGaceta(String url) {
        try {

            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    parsearDatosDBFlow(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error al cargar datos", Toast.LENGTH_LONG).show();
                }
            });
            Volley.newRequestQueue(context).add(request);

        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }


    public void descargarDatosContenidoGaceta(GacetasAdapter adapteraux) {
        adapterisEmpty=false;
        adapter=adapteraux;
        String ruta= getContext().getResources().getString(R.string.prefijoWebService)+"contenidosGaceta.php";
        descargarDatosContenidoGaceta(ruta);
    }

    @Override
    public void descargarDatosContenidoGacetaCompletos() {
        adapterisEmpty=true;
        String ruta= getContext().getResources().getString(R.string.prefijoWebService)+"contenidosGaceta.php";
        descargarDatosContenidoGaceta(ruta);
    }

    @Override
    public void parsearDatosDBFlow(String json) {
        try {

            Object objetoJson = JSONValue.parse(json);
            JSONArray jsonArrayObject = (JSONArray) objetoJson;
            final ArrayList<ContenidoGaceta> list = new ArrayList();
            for (int i = 0; i < jsonArrayObject.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArrayObject.get(i);
                //Crea sentencias sql para agregar a una lista
                ContenidoGaceta contenidoGaceta = new ContenidoGaceta();
                contenidoGaceta.setId( Integer.parseInt(jsonObject.get("id").toString()));
                contenidoGaceta.setFecha(jsonObject.get("fecha").toString());
                contenidoGaceta.setUrlContenido(jsonObject.get("urlContenido").toString());
                contenidoGaceta.setUrlImage(jsonObject.get("urlImage").toString());
                list.add(contenidoGaceta);
            }

            FlowManager.getDatabase(DirectorioDataBase.class)
                    .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                            new ProcessModelTransaction.ProcessModel<ContenidoGaceta>() {
                                @Override
                                public void processModel(ContenidoGaceta contenidoGaceta) {
                                    // do work here -- i.e. user.delete() or user.update()
                                    contenidoGaceta.save();
                                }
                            }).addAll(list).build())  // add elements (can also handle multiple)
                    .error(new Transaction.Error() {
                        @Override
                        public void onError(Transaction transaction, Throwable error) {

                        }
                    })
                    .success(new Transaction.Success() {
                        @Override
                        public void onSuccess(Transaction transaction) {
                            if(!adapterisEmpty){
                                adapter.setList(list);
                            }
                        }
                    }).build().execute();

        } catch (SQLiteException e) {
            Log.e("llenarBaseDatos: ", e.getMessage());
        }
    }

    @Override
   public List<ContenidoGaceta> getList(/*int filter*/) {
        List<ContenidoGaceta> List;
            List = new Select().from(ContenidoGaceta.class).where(ContenidoGaceta_Table.id.greaterThan(800)).orderBy(ContenidoGaceta_Table.id,false).queryList();
        return List;
    }

    @Override
    public ContenidoGaceta getContenidoGaceta(int id) {
        ContenidoGaceta contenidoGaceta = new Select().from(ContenidoGaceta.class).where(ContenidoGaceta_Table.id.is(id)).querySingle();
        return contenidoGaceta;
    }
    @Override
    public int getMaxId() {
        int max = (int) new Select().from(ContenidoGaceta.class).query().getCount();
        return max;
    }

}
