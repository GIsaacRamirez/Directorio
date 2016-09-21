package com.example.isaac.directorioudg.listcentros.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.detallecentro.DetalleCentroActivity;
import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;
import com.example.isaac.directorioudg.listcentros.CentroListPresenter;
import com.example.isaac.directorioudg.listcentros.CentroListPresenterImpl;
import com.example.isaac.directorioudg.listcentros.adapters.CentrosAdapter;
import com.example.isaac.directorioudg.listcentros.adapters.OnItemClickListener;
import com.example.isaac.directorioudg.util.Helper;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class CentroList extends Fragment implements CentroListView, OnItemClickListener {

    public static final int todos=0;
    public static final int metropolitanos=1;
    public static final int regionales=2;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private CentroListPresenter presenter;
    private  CentrosAdapter adapter = null;
    private Helper helper;
    private View view = null;
    private List<Centro> centrosList = new ArrayList<>();


    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }
    public CentroList() { /* Required empty public constructor*/ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_centro_list, container, false);
        ButterKnife.bind(this, view);

        helper= new Helper(this.getContext());


        setupCentroListAdapter();
        presenter = new CentroListPresenterImpl(getCentroListAdapter());

        centrosList = presenter.getCentros(todos);
        if (centrosList.isEmpty()) {
            if (helper.isConect()) {
                presenter.descargarCentros();
            } else {
                Toast.makeText(getContext(),
                        R.string._listaprepasrecycle_error_conexion,
                        Toast.LENGTH_SHORT).show();
            }
        }
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

    ImageLoader provideImageLoader(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (activity != null) {
            imageLoader.setLoaderContext(activity);
        }
        return imageLoader;
    }

    @Override
    public void setCentroList(int filter, CentrosAdapter adapter) {
        adapter.setCentroList(presenter.getCentros(filter));
    }

    @Override
    public CentrosAdapter getCentroListAdapter() {
        return adapter;
    }

    @Override
    public void setupCentroListAdapter() {
        adapter = new CentrosAdapter(centrosList, provideImageLoader(getActivity()), this);
    }
}
