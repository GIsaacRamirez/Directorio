package com.example.isaac.directorioudg.gaceta.listGacetas;


import com.example.isaac.directorioudg.entities.ContenidoGaceta;

import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public class GacetaListInteractorImpl  implements GacetaListInteractor{
    ContenidosGacetaRepository repository;

    public GacetaListInteractorImpl(ContenidosGacetaRepository repository) {
        this.repository = repository;
    }
    @Override
    public List<ContenidoGaceta> execute() {
        return repository.getList();
    }
    @Override
    public void saveContenidoGacetas() {
        repository.descargarDatosContenidoGacetaCompletos();
    }

    @Override
    public void getPorFecha(int anyo, int mes) {
         repository.getPorFecha(anyo,mes);
    }
}
