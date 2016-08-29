package com.example.isaac.directorioudg.listcentros;

import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.entities.Prepa;

import java.util.List;

/**
 * Created by isaac on 29/08/16.
 */
public interface CentroListRepository {

    void descargarDatosCentroCompletos();

    void descargarDatosCentro(String url);

    void parsearDatosCentroDBFlow(String json);

    List<Centro> getListCentro(int filter);

    Prepa getCentro(int id);
}
