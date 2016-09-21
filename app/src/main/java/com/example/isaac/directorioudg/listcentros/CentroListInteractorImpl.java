package com.example.isaac.directorioudg.listcentros;

import com.example.isaac.directorioudg.entities.Centro;
import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public class CentroListInteractorImpl implements CentroListInteractor {
    CentroListRepository repository;

    public CentroListInteractorImpl(CentroListRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Centro> execute(int filter) {
        return repository.getListCentros(filter);
    }

    @Override
    public void saveCentros() {
        repository.descargarDatosCentroCompletos();
    }
}
