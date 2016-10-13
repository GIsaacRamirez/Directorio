package com.example.isaac.directorioudg.gaceta.listGacetas;


import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import com.example.isaac.directorioudg.gaceta.listGacetas.adapters.GacetasAdapter;

import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public interface GacetaListPresenter {
    List<ContenidoGaceta> getContenidoGacetas();
    void descargarContenidoGacetas();
    void descargarContenidoGacetas(GacetasAdapter adapter);

    void getPorFecha(int anyo, int mes);
}
