package com.example.isaac.directorioudg.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by isaac on 15/08/16.
 */
@Database(name = DirectorioDataBase.NAME, version = DirectorioDataBase.VERSION)
public class DirectorioDataBase {
    public static final int VERSION = 2;
    public static final String NAME = "Directorio";
}
