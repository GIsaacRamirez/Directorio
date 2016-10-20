package com.example.isaac.directorioudg.gaceta.detalleGaceta.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.LinksPdfGaceta;
import com.example.isaac.directorioudg.gaceta.detalleGaceta.PdfGacetaPresenterImpl;
import com.example.isaac.directorioudg.gaceta.detalleGaceta.adapter.PdfGacetaAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class detalleGaceta extends AppCompatActivity {

    @Bind(R.id.toolbarDetalleGaceta)
    Toolbar toolbarDetalleGaceta;
    @Bind(R.id.recyclerViewDetalleGaceta)
    RecyclerView recyclerViewDetalleGaceta;
    int idGaceta;
    PdfGacetaAdapter adapter;
    PdfGacetaPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_gaceta);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarDetalleGaceta);
        adapter= new PdfGacetaAdapter();
        adapter.setList( null);
        presenter= new PdfGacetaPresenterImpl(getApplicationContext());

        Bundle bundle = this.getIntent().getExtras();
        idGaceta=bundle.getInt("idGaceta");
        setTitle("Gaceta No." + idGaceta);
        presenter.CargarLinksPdfGacetas(adapter,idGaceta);
        setupRecyclerView();

    }

    private void setupRecyclerView() {
        recyclerViewDetalleGaceta.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerViewDetalleGaceta.setAdapter(adapter);
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
}
