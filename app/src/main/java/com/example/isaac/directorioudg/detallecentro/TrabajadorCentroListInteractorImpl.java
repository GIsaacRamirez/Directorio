package com.example.isaac.directorioudg.detallecentro;

import com.example.isaac.directorioudg.entities.TrabajadorCentro;

import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public class TrabajadorCentroListInteractorImpl {
    TrabajadorCentroListRepositoryImpl repository;

    public TrabajadorCentroListInteractorImpl(TrabajadorCentroListRepositoryImpl repository) {
        this.repository = repository;
    }


    public List<TrabajadorCentro> execute(int id) {
        return  repository.getListTrabajadoresCentro(id);
    }


    public void saveTrabajadoresCentros() {
        repository.descargarDatosTrabajadorCentroCompletos();
    }
}
