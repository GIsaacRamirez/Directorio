package com.example.isaac.directorioudg.listcentros;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.isaac.directorioudg.db.DirectorioDataBase;
import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.entities.Centro_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaac on 29/08/16.
 */
public class CentroListRepositoryImpl implements CentroListRepository {
    Context context;

    public CentroListRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public void descargarDatosCentroCompletos() {
        String ruta = "http://s512984961.onlinehome.mx/DirectorioUDG/CentrosUniversitarios.php";
        descargarDatosCentro(ruta);
    }
    @Override
    public void descargarDatosCentro(String url) {
        try {

            StringRequest request = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    parsearDatosCentroDBFlow(response);
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

    @Override
    public void parsearDatosCentroDBFlow(String json) {
        try {

            Object objetoJson = JSONValue.parse(json);
            JSONArray jsonArrayObject = (JSONArray) objetoJson;
            ArrayList<Centro> listCentros = new ArrayList();
            for (int i = 0; i < jsonArrayObject.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArrayObject.get(i);
                //Crea sentencias sql para agregar a una lista
                Centro centro = new Centro();

                centro.setIdCentro(Integer.parseInt(jsonObject.get("IdCentro").toString()));
                centro.setSigla(jsonObject.get("Sigla").toString());
                centro.setNombreCentro(jsonObject.get("NombreCentro").toString());
                centro.setTematico(Integer.parseInt(jsonObject.get("Tematico").toString()));
                centro.setDireccion(jsonObject.get("Direccion").toString());
                centro.setMunicipio(jsonObject.get("Municipio").toString());
                centro.setCP(Integer.parseInt(jsonObject.get("CP").toString()));

                centro.setLatitud(Double.parseDouble(jsonObject.get("Latitud").toString()));
                centro.setLongitud(Double.parseDouble(jsonObject.get("Longitud").toString()));

                centro.setImagenURL(jsonObject.get("ImagenURL").toString());
                centro.setWeb(jsonObject.get("Web").toString());

                centro.setRector(jsonObject.get("Rector").toString());
                centro.setTelefonoRector(jsonObject.get("TelefonoRector").toString());
                centro.setCorreoRector(jsonObject.get("CorreoRector").toString());
                centro.setFotoRectorURL(jsonObject.get("FotoRectorURL").toString());

                centro.setSecretarioAcademico(jsonObject.get("SecretarioAcademico").toString());
                centro.setTelefonoSecAcademico(jsonObject.get("TelefonoSecAcademico").toString());
                centro.setCorreoSecAcademico(jsonObject.get("CorreoSecAcademico").toString());
                centro.setFotoSecAcademicoURL(jsonObject.get("FotoSecAcademicoURL").toString());

                centro.setSecretarioAdministrativo(jsonObject.get("SecretarioAdministrativo").toString());
                centro.setTelefonoSecAdministrativo(jsonObject.get("TelefonoSecAdministrativo").toString());
                centro.setCorreoSecAdministrativo(jsonObject.get("CorreoSecAdministrativo").toString());
                centro.setFotoSecAdministrativoURL(jsonObject.get("FotoSecAdministrativoURL").toString());


                listCentros.add(centro);
            }

            FlowManager.getDatabase(DirectorioDataBase.class)
                    .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                            new ProcessModelTransaction.ProcessModel<Centro>() {
                                @Override
                                public void processModel(Centro centro) {
                                    // do work here -- i.e. user.delete() or user.update()
                                    centro.save();
                                }
                            }).addAll(listCentros).build())  // add elements (can also handle multiple)
                    .error(new Transaction.Error() {
                        @Override
                        public void onError(Transaction transaction, Throwable error) {

                        }
                    })
                    .success(new Transaction.Success() {
                        @Override
                        public void onSuccess(Transaction transaction) {

                        }
                    }).build().execute();



        } catch (SQLiteException e) {
            Log.e("llenarBaseDatosCentro: ", e.getMessage());
        }
    }

    @Override
    public List<Centro> getListCentro(int filter) {
        List<Centro> centroList;
        if(filter==0){
            centroList = new Select().from(Centro.class).queryList();
        }else if (filter==1){
            centroList = new Select().from(Centro.class).where(Centro_Table.Tematico.is(1)).queryList();
        }
        else {
            centroList = new Select().from(Centro.class).where(Centro_Table.Tematico.is(0)).queryList();
        }
        return centroList;
    }

    @Override
    public Centro getCentro(int id) {
        Centro centro = new Select().from(Centro.class).where(Centro_Table.IdCentro.is(id)).querySingle();
        return centro;
    }
}
