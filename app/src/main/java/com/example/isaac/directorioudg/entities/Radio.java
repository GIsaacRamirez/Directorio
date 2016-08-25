package com.example.isaac.directorioudg.entities;

/**
 * Created by isaac on 26/07/16.
 */
public class Radio {
    private String StationName;
    private String UrlRadio;
    int sourceImagen;

    public Radio() {
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getUrlRadio() {
        return UrlRadio;
    }

    public void setUrlRadio(String urlRadio) {
        UrlRadio = urlRadio;
    }

    public int getSourceImagen() {
        return sourceImagen;
    }

    public void setSourceImagen(int sourceImagen) {
        this.sourceImagen = sourceImagen;
    }
}
