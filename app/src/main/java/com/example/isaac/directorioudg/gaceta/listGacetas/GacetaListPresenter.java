package com.example.isaac.directorioudg.gaceta.listGacetas;


import com.example.isaac.directorioudg.entities.ContenidoGaceta;

import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public interface GacetaListPresenter {
    List<ContenidoGaceta> getContenidoGacetas();
    void descargarContenidoGacetas();
}
