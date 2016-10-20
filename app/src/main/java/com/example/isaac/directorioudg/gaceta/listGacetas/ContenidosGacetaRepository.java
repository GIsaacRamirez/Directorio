package com.example.isaac.directorioudg.gaceta.listGacetas;

import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import java.util.List;

/**
 * Created by isaac on 14/07/16.
 */
public interface ContenidosGacetaRepository {

     void descargarDatosContenidoGaceta(String url);
     void descargarDatosContenidoGacetaCompletos() ;
     void parsearDatosDBFlow(String json) ;

     List<ContenidoGaceta> getList();

     void getPorFecha(int anyo, int mes);
}