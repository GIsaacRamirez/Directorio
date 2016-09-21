package com.example.isaac.directorioudg.listaprepasrecycler;

import com.example.isaac.directorioudg.entities.Prepa;
import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public interface PrepaListPresenter {
    List<Prepa> getPrepas(int filter);
    void descargarPrepas();
}
