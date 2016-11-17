package com.example.isaac.directorioudg.detallecentro.ui;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.detallecentro.TrabajadorCentroListPresenterImpl;
import com.example.isaac.directorioudg.detallecentro.adapters.TrabajadorCentrosAdapter;
import com.example.isaac.directorioudg.entities.TrabajadorCentro;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;
import com.example.isaac.directorioudg.util.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class trabajador_centro extends Fragment  implements TrabajadorCentroListView {

    @Bind(R.id.recyclerViewTrabajador)
    RecyclerView recyclerViewTrabajador;
    int idCentro;
    private Helper helper;
    private View view = null;
    private TrabajadorCentroListPresenterImpl presenter;
    TrabajadorCentrosAdapter adapter=null;
    private List<TrabajadorCentro> trabajadorCentroList = new ArrayList<>();

    public trabajador_centro() { }


    public void setTrabajadorCentroList(List<TrabajadorCentro> trabajadorCentroList) {
        this.trabajadorCentroList = trabajadorCentroList;
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trabajador_centro, container, false);
        ButterKnife.bind(this, view);
        helper = new Helper(this.getContext());
        setupTrabajadorCentroListAdapter();
        Bundle bundle=getArguments();
        idCentro=bundle.getInt("idcentro");
        presenter = new TrabajadorCentroListPresenterImpl();
        trabajadorCentroList=presenter.getList(idCentro);
        setTrabajadorCentroList(idCentro,adapter);


        setupRecyclerView();

        return view;
    }

    private void setupRecyclerView() {
        recyclerViewTrabajador.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        recyclerViewTrabajador.setHasFixedSize(true);
        recyclerViewTrabajador.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewTrabajador.setAdapter(adapter);
        recyclerViewTrabajador.setNestedScrollingEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setTrabajadorCentroList(int idCentro, TrabajadorCentrosAdapter adapter) {
        adapter.setTrabajadorCentroList(presenter.getList(idCentro));
        Log.v("id", String.valueOf(idCentro));
    }

    @Override
    public TrabajadorCentrosAdapter getTrabajadorCentroListAdapter() {
        return adapter;
    }

    @Override
    public void setupTrabajadorCentroListAdapter() {
        adapter = new TrabajadorCentrosAdapter(provideImageLoader(getActivity()));
        adapter.setContext(getContext());
    }

    public void search(String cadena){
        adapter.setTrabajadorCentroList(presenter.getSearch(cadena,idCentro));
    }

    ImageLoader provideImageLoader(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (activity != null) {
            imageLoader.setLoaderContext(activity);
        }
        return imageLoader;
    }
}
