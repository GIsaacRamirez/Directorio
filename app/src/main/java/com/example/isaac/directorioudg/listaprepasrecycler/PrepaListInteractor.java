package com.example.isaac.directorioudg.listaprepasrecycler;

import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.listaprepasrecycler.adapters.PrepasAdapter;

import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public interface PrepaListInteractor {
    List<Prepa> execute(int filter);
    void savePrepas();
}
