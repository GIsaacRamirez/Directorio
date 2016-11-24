package com.example.isaac.directorioudg.gaceta.detalleGaceta;



import android.content.Context;

import com.example.isaac.directorioudg.entities.LinksPdfGaceta;
import com.example.isaac.directorioudg.gaceta.detalleGaceta.adapter.PdfGacetaAdapter;

import java.util.List;


/**
 * Created by isaac on 21/09/16.
 */

public class PdfGacetaPresenterImpl {
    PdfGacetaRepositoryImpl repository;
    public PdfGacetaPresenterImpl(Context context) {
        repository= new PdfGacetaRepositoryImpl(context);
    }
    public void CargarLinksPdfGacetas(PdfGacetaAdapter adapter, int numerogaceta,boolean option_isdownloaded) {
        repository.cargarPdfGaceta(adapter, numerogaceta,option_isdownloaded);
    }

    public List<LinksPdfGaceta> getPdfDownloaded() {
        return repository.getPdfDownloaded();
    }


}
