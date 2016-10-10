package com.example.isaac.directorioudg.gaceta;

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
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by Usuario on 10/10/2016.
 */

public class ContenidosGacetaRepositoryImpl {
    Context context;
    Boolean adapterisEmpty=true;
    public ContenidosGacetaRepositoryImpl() {
        this.context = getContext().getApplicationContext();
    }

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


    /*public void descargarDatosPrepaCompletos(PrepasAdapter adapteraux) {
        adapterisEmpty=false;
        adapter=adapteraux;
        String ruta= getContext().getResources().getString(R.string.prefijoWebService)+"preparatorias.php";
        descargarDatosPrepa(ruta);
    }*/
    public void descargarDatosContenidoGacetaCompletos() {
        adapterisEmpty=true;
        String ruta= getContext().getResources().getString(R.string.prefijoWebService)+"contenidosGaceta.php";
        descargarDatosContenidoGaceta(ruta);
    }

    public void parsearDatosDBFlow(String json) {
        try {

            Object objetoJson = JSONValue.parse(json);
            JSONArray jsonArrayObject = (JSONArray) objetoJson;
            ArrayList<ContenidoGaceta> list = new ArrayList();
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
                                //adapter.setPrepaList(getListContenidosGaceta(0));
                            }
                        }
                    }).build().execute();

        } catch (SQLiteException e) {
            Log.e("llenarBaseDatos: ", e.getMessage());
        }
    }

}
