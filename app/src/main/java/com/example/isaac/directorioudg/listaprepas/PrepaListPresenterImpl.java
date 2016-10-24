package com.example.isaac.directorioudg.listaprepas;

import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.listaprepas.adapters.PrepasAdapter;
import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public class PrepaListPresenterImpl {

    PrepaListInteractorImpl interactor;
    PrepaListRepositoryImpl repository;

    public PrepaListPresenterImpl(PrepasAdapter adapter) {
        repository= new PrepaListRepositoryImpl(adapter);
        interactor= new PrepaListInteractorImpl(repository);
    }


    public List<Prepa> getPrepas(int filter) {
        return interactor.execute(filter);
    }


    public void descargarPrepas() {
        interactor.savePrepas();
    }

}
