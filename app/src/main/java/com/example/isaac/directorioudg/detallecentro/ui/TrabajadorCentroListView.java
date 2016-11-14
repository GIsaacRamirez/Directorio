package com.example.isaac.directorioudg.detallecentro.ui;

import com.example.isaac.directorioudg.detallecentro.adapters.TrabajadorCentrosAdapter;

/**
 * Created by isaac on 21/09/16.
 */

public interface TrabajadorCentroListView {
    void setTrabajadorCentroList(int filter, TrabajadorCentrosAdapter adapter);
    TrabajadorCentrosAdapter getTrabajadorCentroListAdapter();
    void setupTrabajadorCentroListAdapter();
}
