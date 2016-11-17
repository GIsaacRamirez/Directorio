package com.example.isaac.directorioudg.detallecentro;

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
import com.example.isaac.directorioudg.detallecentro.adapters.TrabajadorCentrosAdapter;
import com.example.isaac.directorioudg.entities.TrabajadorCentro;
import com.example.isaac.directorioudg.entities.TrabajadorCentro_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.Delete;
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
 * Created by isaac on 29/08/16.
 */
public class TrabajadorCentroListRepositoryImpl {
    Context context;

    TrabajadorCentrosAdapter adapter=null;
    int id=-1;

    public TrabajadorCentroListRepositoryImpl() {
        this.context = getContext().getApplicationContext();
    }

    public TrabajadorCentroListRepositoryImpl(TrabajadorCentrosAdapter adapter) {
        this.adapter=adapter;
        this.context = getContext().getApplicationContext();
    }


    public void descargarDatosTrabajadorCentroCompletos() {
        String ruta= getContext().getResources().getString(R.string.prefijoWebService)+"TrabajadorCentro.php";
        descargarDatos(ruta);
    }
    public void descargarDatosTrabajadorCentroCompletos(int id, TrabajadorCentrosAdapter adapter) {
        this.adapter=adapter;
        this.id=id;
        String ruta= getContext().getResources().getString(R.string.prefijoWebService)+"TrabajadorCentro.php";
        descargarDatos(ruta);
    }


    private void descargarDatos(String url) {
        try {
                limpiar();
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


    private void parsearDatosCentroDBFlow(String json) {
        try {
            Object objetoJson = JSONValue.parse(json);
            JSONArray jsonArrayObject = (JSONArray) objetoJson;
            ArrayList<TrabajadorCentro> listTrabajadores = new ArrayList();
            for (int i = 0; i < jsonArrayObject.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArrayObject.get(i);
                //Crea sentencias sql para agregar a una lista
                TrabajadorCentro trabajador = new TrabajadorCentro();

                trabajador.setIdcentro(Integer.parseInt(jsonObject.get("idcentro").toString()));
                trabajador.setNombre(jsonObject.get("nombre").toString());
                trabajador.setImagen(jsonObject.get("imagen").toString());
                trabajador.setPuesto(jsonObject.get("puesto").toString());
                trabajador.setTelefono(jsonObject.get("telefono").toString());
                trabajador.setCorreo(jsonObject.get("correo").toString());
                trabajador.setEstatus(Integer.parseInt(jsonObject.get("Estatus").toString()));

                listTrabajadores.add(trabajador);
            }

            FlowManager.getDatabase(DirectorioDataBase.class)
                    .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                            new ProcessModelTransaction.ProcessModel<TrabajadorCentro>() {
                                @Override
                                public void processModel(TrabajadorCentro trabajador) {
                                    // do work here -- i.e. user.delete() or user.update()
                                    trabajador.save();
                                }
                            }).addAll(listTrabajadores).build())  // add elements (can also handle multiple)
                    .error(new Transaction.Error() {
                        @Override
                        public void onError(Transaction transaction, Throwable error) {

                        }
                    })
                    .success(new Transaction.Success() {
                        @Override
                        public void onSuccess(Transaction transaction) {
                            if(id>-1){
                                adapter.setTrabajadorCentroList(getListTrabajadoresCentro(id));
                            }
                        }
                    }).build().execute();



        } catch (SQLiteException e) {
            Log.e("llenarBaseDatosCentro: ", e.getMessage());
        }
    }

    public List<TrabajadorCentro> getListTrabajadoresCentro(int id) {
        return  new Select().from(TrabajadorCentro.class).where(TrabajadorCentro_Table.idcentro.is(id)).queryList();
    }

    public void limpiar(){
        Delete.table(TrabajadorCentro.class);
    }

    public List<TrabajadorCentro> getListSearch(String cadena, int idcentro) {

        ConditionGroup conditionGroup = ConditionGroup.clause()
                .and(ConditionGroup.clause()
                        .or(TrabajadorCentro_Table.nombre.like("%"+cadena+"%"))
                        .or(TrabajadorCentro_Table.puesto.like("%"+cadena+"%")))
                .and(TrabajadorCentro_Table.idcentro.is(idcentro));
        return  new Select().from(TrabajadorCentro.class).where(conditionGroup).queryList();
        //return  new Select().from(TrabajadorCentro.class).where(TrabajadorCentro_Table.nombre.like("%"+cadena+"%")).and(TrabajadorCentro_Table.idcentro.is(idcentro)).queryList();
    }
}
