package com.example.isaac.directorioudg;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
/**
 * Created by isaac on 15/08/16.
 */
public class DirectorioApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBTearDown();
    }

    private void DBTearDown() {
        FlowManager.destroy();
    }

    private void initDB() {
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
