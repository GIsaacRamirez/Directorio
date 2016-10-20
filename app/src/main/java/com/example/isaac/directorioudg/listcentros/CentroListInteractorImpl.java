package com.example.isaac.directorioudg.listcentros;

import com.example.isaac.directorioudg.entities.Centro;
import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public class CentroListInteractorImpl{
    CentroListRepositoryImpl repository;

    public CentroListInteractorImpl(CentroListRepositoryImpl repository) {
        this.repository = repository;
    }


    public List<Centro> execute(int filter) {
        return repository.getListCentros(filter);
    }


    public void saveCentros() {
        repository.descargarDatosCentroCompletos();
    }
}
