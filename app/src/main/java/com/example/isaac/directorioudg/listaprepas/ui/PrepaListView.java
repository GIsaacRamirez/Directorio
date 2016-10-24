package com.example.isaac.directorioudg.listaprepas.ui;

import com.example.isaac.directorioudg.listaprepas.adapters.PrepasAdapter;



/**
 * Created by isaac on 14/07/16.
 */
public interface PrepaListView {

    void setPrepaList(int filter, PrepasAdapter adapter);
    PrepasAdapter getPrepaListAdapter();
    void setupPrepaListAdapter();
}
