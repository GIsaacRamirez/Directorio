package com.example.isaac.directorioudg.detallecentro;

import com.example.isaac.directorioudg.detallecentro.adapters.TrabajadorCentrosAdapter;
import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.entities.TrabajadorCentro;

import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public class TrabajadorCentroListPresenterImpl {

    TrabajadorCentroListInteractorImpl interactor;
    TrabajadorCentroListRepositoryImpl repository;

    public TrabajadorCentroListPresenterImpl() {
        repository= new TrabajadorCentroListRepositoryImpl();
        interactor= new TrabajadorCentroListInteractorImpl(repository);
    }

    public void descargarTrabajadoresCentros() {
        interactor.saveTrabajadoresCentros();
    }

    public List<TrabajadorCentro> getList(int id){
        return interactor.execute(id);
    }
    public List<TrabajadorCentro> getSearch(String cadena,int idcentro){
        return interactor.search(cadena,idcentro);
    }

}
