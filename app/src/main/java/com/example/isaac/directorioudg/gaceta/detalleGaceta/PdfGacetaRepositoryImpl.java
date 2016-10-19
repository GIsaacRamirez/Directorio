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
import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import com.example.isaac.directorioudg.entities.ContenidoGaceta_Table;
import com.example.isaac.directorioudg.entities.LinksPdfGaceta;
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
import java.util.StringTokenizer;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by Usuario on 10/10/2016.
 */

public class PdfGacetaRepositoryImpl {
    Context context;
    //GacetasAdapter adapter=null;


    public PdfGacetaRepositoryImpl() {
        this.context = getContext().getApplicationContext();

    }
    public PdfGacetaRepositoryImpl(GacetasAdapter adapter) {
        this.context = getContext().getApplicationContext();
        //this.adapter=adapter;
    }


    public void cargarDatosPdfGaceta(String url) {
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


    public void cargarPdfGaceta(GacetasAdapter adapteraux, int numerogaceta) {
        //adapter=adapteraux;
        String ruta= getContext().getResources().getString(R.string.prefijoWebService)+"PdfGacetas.php?id=+"+numerogaceta;
        cargarDatosPdfGaceta(ruta);
    }


    public void parsearDatosDBFlow(String json) {
        try {

            Object objetoJson = JSONValue.parse(json);
            JSONArray jsonArrayObject = (JSONArray) objetoJson;
            final ArrayList<LinksPdfGaceta> list = new ArrayList();
            for (int i = 0; i < jsonArrayObject.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArrayObject.get(i);
                //Crea sentencias sql para agregar a una lista
                LinksPdfGaceta linksPdfGaceta = new LinksPdfGaceta();
                linksPdfGaceta.setNumeroGaceta( Integer.parseInt(jsonObject.get("numeroGaceta").toString()));


                linksPdfGaceta.setLinkPdf(jsonObject.get("linkPdf").toString());
                linksPdfGaceta.setDescripcion(jsonObject.get("descripciom").toString());
                list.add(linksPdfGaceta);
            }

            //adapter.setList(list);

        } catch (SQLiteException e) {
            Log.e("llenarBaseDatos: ", e.getMessage());
        }
    }


}
