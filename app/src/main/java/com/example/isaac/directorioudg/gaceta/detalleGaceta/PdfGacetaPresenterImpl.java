package com.example.isaac.directorioudg.gaceta.detalleGaceta;



import android.content.Context;

import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import com.example.isaac.directorioudg.gaceta.detalleGaceta.PdfGacetaRepositoryImpl;
import com.example.isaac.directorioudg.gaceta.detalleGaceta.adapter.PdfGacetaAdapter;
import com.example.isaac.directorioudg.gaceta.listGacetas.ContenidosGacetaRepository;
import com.example.isaac.directorioudg.gaceta.listGacetas.ContenidosGacetaRepositoryImpl;
import com.example.isaac.directorioudg.gaceta.listGacetas.GacetaListInteractor;
import com.example.isaac.directorioudg.gaceta.listGacetas.GacetaListInteractorImpl;
import com.example.isaac.directorioudg.gaceta.listGacetas.GacetaListPresenter;
import com.example.isaac.directorioudg.gaceta.listGacetas.adapters.GacetasAdapter;

import java.util.List;


/**
 * Created by isaac on 21/09/16.
 */

public class PdfGacetaPresenterImpl {
    PdfGacetaRepositoryImpl repository;
    public PdfGacetaPresenterImpl(Context context) {
        repository= new PdfGacetaRepositoryImpl(context);
    }
    public void CargarLinksPdfGacetas(PdfGacetaAdapter adapter, int numerogaceta) {
        repository.cargarPdfGaceta(adapter, numerogaceta);
    }


}
