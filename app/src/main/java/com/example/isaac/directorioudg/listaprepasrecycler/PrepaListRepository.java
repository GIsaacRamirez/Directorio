package com.example.isaac.directorioudg.listaprepasrecycler;

import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.listaprepasrecycler.adapters.PrepasAdapter;

import java.util.List;

/**
 * Created by isaac on 14/07/16.
 */
public interface PrepaListRepository {
    void descargarDatosPrepa(String url);
    void descargarDatosPrepaCompletos(PrepasAdapter adapter);
    void descargarDatosPrepaCompletos();

    List<Prepa> getListPrepas(int filter);

    Prepa getPrepa(int id);
}