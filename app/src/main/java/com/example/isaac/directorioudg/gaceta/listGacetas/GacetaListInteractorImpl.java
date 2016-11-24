package com.example.isaac.directorioudg.gaceta.listGacetas;

import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public class GacetaListInteractorImpl {
    ContenidosGacetaRepositoryImpl repository;

    public GacetaListInteractorImpl(ContenidosGacetaRepositoryImpl repository) {
        this.repository = repository;
    }

    public List<ContenidoGaceta> execute() {
        return repository.getList();
    }

    public void saveContenidoGacetas() {
        repository.descargarDatosContenidoGacetaCompletos();
    }


    public void getPorFecha(int anyo, int mes) {
         repository.getPorFecha(anyo,mes);
    }
    public ContenidoGaceta getPorId(int id) {
        return repository.getPorId(id);
    }
}
