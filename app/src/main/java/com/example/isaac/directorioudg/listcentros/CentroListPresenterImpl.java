package com.example.isaac.directorioudg.listcentros;

import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.listcentros.adapters.CentrosAdapter;
import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public class CentroListPresenterImpl{

    CentroListInteractorImpl interactor;
    CentroListRepositoryImpl repository;

    public CentroListPresenterImpl(CentrosAdapter adapter) {
        repository= new CentroListRepositoryImpl(adapter);
        interactor= new CentroListInteractorImpl(repository);
    }


    public List<Centro> getCentros(int filter) {
        return interactor.execute(filter);
    }

    public void descargarCentros() {
        interactor.saveCentros();
    }

}
