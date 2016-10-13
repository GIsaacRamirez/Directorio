package com.example.isaac.directorioudg.gaceta.listGacetas;



import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import com.example.isaac.directorioudg.gaceta.listGacetas.adapters.GacetasAdapter;

import java.util.List;


/**
 * Created by isaac on 21/09/16.
 */

public class GacetaListPresenterImpl  implements GacetaListPresenter{

    GacetaListInteractor interactor;
    ContenidosGacetaRepository  repository;

    public GacetaListPresenterImpl(GacetasAdapter adapter) {
        repository= new ContenidosGacetaRepositoryImpl(adapter);
        interactor= new GacetaListInteractorImpl(repository);
    }
    @Override
    public List<ContenidoGaceta> getContenidoGacetas() {
        return interactor.execute();
    }

    @Override
    public void descargarContenidoGacetas() {
            interactor.saveContenidoGacetas();
    }

    @Override
    public void descargarContenidoGacetas(GacetasAdapter adapter) {
        repository= new ContenidosGacetaRepositoryImpl(adapter);
        interactor= new GacetaListInteractorImpl(repository);
        interactor.saveContenidoGacetas();
    }
}
