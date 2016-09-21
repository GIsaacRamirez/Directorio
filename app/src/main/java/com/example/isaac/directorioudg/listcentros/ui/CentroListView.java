package com.example.isaac.directorioudg.listcentros.ui;

import com.example.isaac.directorioudg.listcentros.adapters.CentrosAdapter;

/**
 * Created by isaac on 21/09/16.
 */

public interface CentroListView {
    void setCentroList(int filter, CentrosAdapter adapter);
    CentrosAdapter getCentroListAdapter();
    void setupCentroListAdapter();
}
