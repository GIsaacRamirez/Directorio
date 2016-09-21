package com.example.isaac.directorioudg.listcentros;

import com.example.isaac.directorioudg.entities.Centro;
import java.util.List;

/**
 * Created by isaac on 21/09/16.
 */

public interface CentroListInteractor {
    List<Centro> execute(int filter);
    void saveCentros();
}
