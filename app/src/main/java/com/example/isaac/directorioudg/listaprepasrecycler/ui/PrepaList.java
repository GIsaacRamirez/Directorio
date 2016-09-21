package com.example.isaac.directorioudg.listaprepasrecycler.ui;


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
import com.example.isaac.directorioudg.detalleprepa.DetallePrepaActivity;
import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListPresenter;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListPresenterImpl;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListRepository;
import com.example.isaac.directorioudg.listaprepasrecycler.PrepaListRepositoryImpl;
import com.example.isaac.directorioudg.listaprepasrecycler.adapters.OnItemClickListener;
import com.example.isaac.directorioudg.listaprepasrecycler.adapters.PrepasAdapter;
import com.example.isaac.directorioudg.util.Helper;

import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class PrepaList extends Fragment implements PrepaListView, OnItemClickListener {
    public static final int todas=0;
    public static final int metropolitanas=1;
    public static final int regionales=2;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private PrepaListPresenter presenter;

    private  PrepasAdapter adapter = null;
    private Helper helper;
    private  View view = null;
    private List<Prepa> prepaList = new ArrayList<>();

    public final PrepasAdapter getPrepaListAdapter() {
        return adapter;
    }

    public PrepaList() {    }

    public void setupPrepaListAdapter() {
        adapter = new PrepasAdapter(prepaList,provideImageLoader(getActivity()), this);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);
    }

    public final void setPrepaList(int filter,PrepasAdapter adapter){
        adapter.setPrepaList(presenter.getPrepas(filter));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_prepalist, container, false);
        ButterKnife.bind(this, view);

        helper= new Helper(this.getContext());
        setupPrepaListAdapter();
        presenter = new PrepaListPresenterImpl(getPrepaListAdapter());

        prepaList = presenter.getPrepas(todas);
        if (prepaList.isEmpty()) {
            if (helper.isConect()) {
                presenter.descargarPrepas();
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
    public void onItemClick(Prepa prepa) {
        Intent intent = new Intent(getActivity(), DetallePrepaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
