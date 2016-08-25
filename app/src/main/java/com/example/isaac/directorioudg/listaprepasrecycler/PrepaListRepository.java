package com.example.isaac.directorioudg.listaprepasrecycler;

import com.example.isaac.directorioudg.entities.Prepa;
import java.util.List;

/**
 * Created by isaac on 14/07/16.
 */
public interface PrepaListRepository {
    void descargarDatosPrepa(String url);
    void descargarDatosPrepaCompletos();

    List<Prepa> getListPrepas(int filter);

    Prepa getPrepa(int id);
}