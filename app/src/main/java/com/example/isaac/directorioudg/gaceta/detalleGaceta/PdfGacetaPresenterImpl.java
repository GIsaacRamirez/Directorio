package com.example.isaac.directorioudg.gaceta.detalleGaceta;



import android.content.Context;

import com.example.isaac.directorioudg.gaceta.detalleGaceta.adapter.PdfGacetaAdapter;


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
