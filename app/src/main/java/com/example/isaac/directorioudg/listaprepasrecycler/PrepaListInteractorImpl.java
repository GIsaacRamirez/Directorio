package com.example.isaac.directorioudg.listaprepasrecycler;

import com.example.isaac.directorioudg.entities.Prepa;
import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public class PrepaListInteractorImpl implements PrepaListInteractor {
    PrepaListRepository repository;

    public PrepaListInteractorImpl(PrepaListRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Prepa> execute(int filter) {
        return repository.getListPrepas(filter);
    }

    @Override
    public void savePrepas() {
        repository.descargarDatosPrepaCompletos();
    }
}
