package com.example.isaac.directorioudg.listaprepas;

import com.example.isaac.directorioudg.entities.Prepa;
import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public interface PrepaListInteractor {
    List<Prepa> execute(int filter);
    void savePrepas();
}
