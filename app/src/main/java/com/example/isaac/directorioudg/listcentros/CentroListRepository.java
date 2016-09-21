package com.example.isaac.directorioudg.listcentros;

import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.listcentros.adapters.CentrosAdapter;

import java.util.List;

/**
 * Created by isaac on 29/08/16.
 */
public interface CentroListRepository {

    void descargarDatosCentroCompletos();

    void descargarDatosCentroCompletos(CentrosAdapter adapteraux);

    void descargarDatosCentro(String url);

    void parsearDatosCentroDBFlow(String json);

    List<Centro> getListCentros(int filter);

    Centro getCentro(int id);
}
