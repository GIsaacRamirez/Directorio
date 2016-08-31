package com.example.isaac.directorioudg.listcentros;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.detallecentro.DetalleCentroActivity;
import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;
import com.example.isaac.directorioudg.listcentros.adapters.CentrosAdapter;
import com.example.isaac.directorioudg.listcentros.adapters.OnItemClickListener;
import com.example.isaac.directorioudg.util.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CentroList extends Fragment implements OnItemClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    CentroListRepository repository;
    ImageLoader imageLoader;

    public static CentrosAdapter adapter;
    Helper helper;

    View view = null;
    public  static List<Centro> centrosList = new ArrayList<>();

    public final void setCentrosList(int filter){
        adapter.setCentroList(repository.getListCentro(filter));
        Log.v("CentroList", "centro");
    }

    public void setupAdapter() {
        adapter = new CentrosAdapter(repository.getListCentro(0), imageLoader, this);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }
    public CentroList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_centro_list, container, false);
        ButterKnife.bind(this, view);
        helper= new Helper(this.getContext());
        imageLoader= new GlideImageLoader(this.getContext());

        repository= new CentroListRepositoryImpl(getContext());
        centrosList = repository.getListCentro(0);
        if (centrosList.isEmpty()) {
            if (helper.isConect()) {
                repository.descargarDatosCentroCompletos();
            } else {
                Toast.makeText(getContext(),
                        R.string._listaprepasrecycle_error_conexion,
                        Toast.LENGTH_SHORT).show();
            }
        }
        setupAdapter();
        setupRecyclerView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(Centro centro) {
        Intent intent = new Intent(getActivity(), DetalleCentroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Bundle bundle = new Bundle();
        bundle.putParcelable("centro",centro);
        intent.putExtras(bundle);//ponerlos en el intent
        startActivity(intent);//iniciar la actividad
    }
}
