package com.example.isaac.directorioudg.listaprepasrecycler;


import android.app.Activity;
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
import com.example.isaac.directorioudg.detalleprepa.DetallePrepaActivity;
import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;
import com.example.isaac.directorioudg.listaprepasrecycler.adapters.OnItemClickListener;
import com.example.isaac.directorioudg.listaprepasrecycler.adapters.PrepasAdapter;
import com.example.isaac.directorioudg.util.Helper;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class PrepaList extends Fragment implements  OnItemClickListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    PrepaListRepository repository;

    public static PrepasAdapter adapter;
    Helper helper;
    int tipo;

    View view = null;
    public  static List<Prepa> prepaList = new ArrayList<>();
    public PrepaList() { }

    public final void setPrepaList(int filter){


        adapter.setPrepaList(repository.getListPrepas(filter));
        Log.v("PrepaList", "metropolitana");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        helper= new Helper(this.getContext());
        view = inflater.inflate(R.layout.fragment_prepalist, container, false);
        ButterKnife.bind(this, view);
        repository= new PrepaListRepositoryImpl(getContext());
        prepaList = repository.getListPrepas(0);
        if (prepaList.isEmpty()) {
            if (helper.isConect()) {
                repository.descargarDatosPrepaCompletos();
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

    public void setupAdapter() {
        adapter = new PrepasAdapter( repository.getListPrepas(0), provideImageLoader(getActivity()), this);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(Prepa prepa) {
        Intent intent = new Intent(getActivity(), DetallePrepaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                );
        Bundle bundle = new Bundle();
        bundle.putParcelable("prepa",prepa);
        intent.putExtras(bundle);//ponerlos en el intent
        startActivity(intent);//iniciar la actividad
    }



    ImageLoader provideImageLoader(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (activity != null) {
            imageLoader.setLoaderContext(activity);
        }
        return imageLoader;
    }
}
