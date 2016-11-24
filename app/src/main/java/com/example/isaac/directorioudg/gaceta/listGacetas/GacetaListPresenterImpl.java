package com.example.isaac.directorioudg.gaceta.listGacetas;



import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import com.example.isaac.directorioudg.gaceta.listGacetas.adapters.GacetasAdapter;

import java.util.List;


/**
 * Created by isaac on 21/09/16.
 */

public class GacetaListPresenterImpl{

    GacetaListInteractorImpl interactor;
    ContenidosGacetaRepositoryImpl  repository;

    public GacetaListPresenterImpl(GacetasAdapter adapter) {
        repository= new ContenidosGacetaRepositoryImpl(adapter);
        interactor= new GacetaListInteractorImpl(repository);
    }

    public List<ContenidoGaceta> getContenidoGacetas() {
        return interactor.execute();
    }


    public void descargarContenidoGacetas() {
            interactor.saveContenidoGacetas();
    }


    public void descargarContenidoGacetas(GacetasAdapter adapter) {
        repository= new ContenidosGacetaRepositoryImpl(adapter);
        interactor= new GacetaListInteractorImpl(repository);
        interactor.saveContenidoGacetas();
    }

    public ContenidoGaceta getPorId(int id) {
        return interactor.getPorId(id);
    }
    public void getPorFecha(int anyo, int mes) {
        interactor.getPorFecha(anyo, mes);
    }
}
