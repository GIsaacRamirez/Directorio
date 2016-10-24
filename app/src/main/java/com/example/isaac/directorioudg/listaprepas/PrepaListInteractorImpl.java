package com.example.isaac.directorioudg.listaprepas;

import com.example.isaac.directorioudg.entities.Prepa;
import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public class PrepaListInteractorImpl  {
    PrepaListRepositoryImpl repository;

    public PrepaListInteractorImpl(PrepaListRepositoryImpl repository) {
        this.repository = repository;
    }


    public List<Prepa> execute(int filter) {
        return repository.getListPrepas(filter);
    }


    public void savePrepas() {
        repository.descargarDatosPrepaCompletos();
    }
}
