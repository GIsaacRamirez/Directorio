package com.example.isaac.directorioudg.gaceta.detalleGaceta;

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
import com.example.isaac.directorioudg.entities.LinksPdfGaceta;
import com.example.isaac.directorioudg.gaceta.detalleGaceta.adapter.PdfGacetaAdapter;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;

/**
 * Created by Isaac on 10/10/2016.
 */

public class PdfGacetaRepositoryImpl {
    PdfGacetaAdapter adapter=null;
    Context context;

    public PdfGacetaRepositoryImpl(Context contex) {
        this.context = contex;
    }


    private void cargarDatosPdfGaceta(String url) {
        try {

            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    parsearDatos(response);
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


    public void cargarPdfGaceta(PdfGacetaAdapter adapteraux, int numerogaceta) {
        adapter=adapteraux;
        String ruta= context.getResources().getString(R.string.prefijoWebService)+"PdfGacetas.php?id="+numerogaceta;
        cargarDatosPdfGaceta(ruta);
    }

    private void parsearDatos(String json) {
        try {

            Object objetoJson = JSONValue.parse(json);
            JSONArray jsonArrayObject = (JSONArray) objetoJson;
            final ArrayList<LinksPdfGaceta> list = new ArrayList();
            for (int i = 0; i < jsonArrayObject.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArrayObject.get(i);
                //Crea sentencias sql para agregar a una lista
                LinksPdfGaceta linksPdfGaceta = new LinksPdfGaceta();
                linksPdfGaceta.setId(Integer.parseInt(jsonObject.get("id").toString()));
                linksPdfGaceta.setNumeroGaceta(Integer.parseInt(jsonObject.get("numeroGaceta").toString()));
                linksPdfGaceta.setLinkPdf(jsonObject.get("linkPdf").toString());

                linksPdfGaceta.setTitulo(jsonObject.get("titulo").toString());
                linksPdfGaceta.setDescripcion(jsonObject.get("descripcion").toString());
                list.add(linksPdfGaceta);
            }

            adapter.setList(list);
        } catch (SQLiteException e) {}
    }
    private void parsearDatosDBFLOW(String json) {
        try {

            Object objetoJson = JSONValue.parse(json);
            JSONArray jsonArrayObject = (JSONArray) objetoJson;
            final ArrayList<LinksPdfGaceta> list = new ArrayList();
            for (int i = 0; i < jsonArrayObject.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArrayObject.get(i);
                //Crea sentencias sql para agregar a una lista
                LinksPdfGaceta linksPdfGaceta = new LinksPdfGaceta();
                linksPdfGaceta.setId(Integer.parseInt(jsonObject.get("id").toString()));
                linksPdfGaceta.setNumeroGaceta(Integer.parseInt(jsonObject.get("numeroGaceta").toString()));
                linksPdfGaceta.setLinkPdf(jsonObject.get("linkPdf").toString());

                linksPdfGaceta.setTitulo(jsonObject.get("titulo").toString());
                linksPdfGaceta.setDescripcion(jsonObject.get("descripcion").toString());
                list.add(linksPdfGaceta);
            }
            //adapter.setList(list);

            FlowManager.getDatabase(DirectorioDataBase.class)
                    .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                            new ProcessModelTransaction.ProcessModel<LinksPdfGaceta>() {
                                @Override
                                public void processModel(LinksPdfGaceta linksPdfGaceta1) {
                                    // do work here -- i.e. user.delete() or user.update()
                                    linksPdfGaceta1.save();
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
                            if(adapter!=null){
                                adapter.setList(list);
                            }
                        }
                    }).build().execute();

        } catch (SQLiteException e) {
            Log.e("llenarBaseDatos: ", e.getMessage());
        }
    }
}
