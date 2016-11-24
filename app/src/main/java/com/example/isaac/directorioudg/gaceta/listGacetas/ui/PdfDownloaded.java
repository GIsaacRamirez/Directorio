package com.example.isaac.directorioudg.gaceta.listGacetas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import com.example.isaac.directorioudg.entities.LinksPdfGaceta;
import com.example.isaac.directorioudg.gaceta.detalleGaceta.PdfGacetaPresenterImpl;
import com.example.isaac.directorioudg.gaceta.detalleGaceta.ui.detalleGaceta;
import com.example.isaac.directorioudg.gaceta.listGacetas.GacetaListPresenterImpl;
import com.example.isaac.directorioudg.gaceta.listGacetas.adapters.GacetasAdapter;
import com.example.isaac.directorioudg.gaceta.listGacetas.adapters.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PdfDownloaded extends AppCompatActivity implements OnItemClickListener {
    List<LinksPdfGaceta> list;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private GacetaListPresenterImpl presenter=null;
    private List<ContenidoGaceta> GacetaList = new ArrayList<>();
    private GacetasAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_downloaded);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("Gacetas descargadas");

        PdfGacetaPresenterImpl presenterPdf = new PdfGacetaPresenterImpl(getApplicationContext());
        setupGacetaListAdapter();
        presenter= new GacetaListPresenterImpl(adapter);
        list = presenterPdf.getPdfDownloaded();
        for (int i=0;i<list.size();i++){
            LinksPdfGaceta linksPdfGaceta=list.get(i);
            ContenidoGaceta contenidoGaceta = presenter.getPorId(linksPdfGaceta.getNumeroGaceta());
            GacetaList.add(contenidoGaceta);
        }
        adapter.setList(GacetaList);
        setupRecyclerView();
    }

    public void setupGacetaListAdapter() {
        adapter = new GacetasAdapter(GacetaList, getApplicationContext(), this);
    }


    private void setupRecyclerView() {
         recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
         recyclerView.setAdapter(adapter);
     }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(ContenidoGaceta contenidoGaceta) {
        Intent intent = new Intent(this.getApplicationContext(), detalleGaceta.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putInt("idGaceta", contenidoGaceta.getId());
        bundle.putBoolean("isdownloaded",true);
        intent.putExtras(bundle);//ponerlos en el intent
        startActivity(intent);//iniciar la actividad
    }
}
