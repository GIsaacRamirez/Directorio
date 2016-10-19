package com.example.isaac.directorioudg.gaceta.detalleGaceta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.ContenidoGaceta;

import butterknife.Bind;
import butterknife.ButterKnife;

public class detalleGaceta extends AppCompatActivity {

    @Bind(R.id.toolbarDetalleGaceta)
    Toolbar toolbarDetalleGaceta;
    @Bind(R.id.recyclerViewDetalleGaceta)
    RecyclerView recyclerViewDetalleGaceta;
    ContenidoGaceta contenidoGaceta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_gaceta);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarDetalleGaceta);
        Bundle bundle = this.getIntent().getExtras();
        contenidoGaceta=bundle.getParcelable("contenidoGaceta");
        setTitle("Gaceta No." + contenidoGaceta.getId());


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