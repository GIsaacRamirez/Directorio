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

import com.example.isaac.directorioudg.entities.LinksPdfGaceta;
import com.example.isaac.directorioudg.entities.LinksPdfGaceta_Table;
import com.example.isaac.directorioudg.gaceta.detalleGaceta.adapter.PdfGacetaAdapter;
import com.example.isaac.directorioudg.util.Helper;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Isaac on 10/10/2016.
 */

public class PdfGacetaRepositoryImpl {
    PdfGacetaAdapter adapter=null;
    Context context;
    List<LinksPdfGaceta> list = new ArrayList();

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
        Helper helper =new Helper(context);
        if(!helper.isConect()){
            List<LinksPdfGaceta> auxLinkPdf = new Select().from(LinksPdfGaceta.class).where(LinksPdfGaceta_Table.numeroGaceta.is(numerogaceta)).queryList();
            if(auxLinkPdf!=null){
                list=auxLinkPdf;
                adapter.setList(list);
            }
        }else {
            cargarDatosPdfGaceta(ruta);
        }
    }

    private void parsearDatos(String json) {
        try {

            Object objetoJson = JSONValue.parse(json);
            JSONArray jsonArrayObject = (JSONArray) objetoJson;

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

}
