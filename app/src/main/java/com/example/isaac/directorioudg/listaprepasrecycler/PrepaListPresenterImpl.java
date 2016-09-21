package com.example.isaac.directorioudg.listaprepasrecycler;

import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.listaprepasrecycler.adapters.PrepasAdapter;
import com.example.isaac.directorioudg.listaprepasrecycler.ui.PrepaList;
import com.example.isaac.directorioudg.listaprepasrecycler.ui.PrepaListView;

import java.util.List;

import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * Created by isaac on 21/09/16.
 */

public class PrepaListPresenterImpl implements PrepaListPresenter {


    PrepaListInteractor interactor;
    PrepaListRepository repository;


    public PrepaListPresenterImpl(PrepasAdapter adapter) {
        repository= new PrepaListRepositoryImpl(getContext().getApplicationContext(),adapter);
        interactor= new PrepaListInteractorImpl(repository);
    }

    @Override
    public List<Prepa> getPrepas(int filter) {
        return interactor.execute(filter);
    }

    @Override
    public void descargarPrepas() {
        interactor.savePrepas();
    }

}
