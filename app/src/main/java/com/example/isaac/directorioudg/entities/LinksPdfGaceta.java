package com.example.isaac.directorioudg.entities;

/**
 * Created by Usuario on 19/10/2016.
 */

public class LinksPdfGaceta {
    int numeroGaceta;
    String linkPdf;
    String titulo;
    String descripcion;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroGaceta() {
        return numeroGaceta;

    }

    public void setNumeroGaceta(int numeroGaceta) {
        this.numeroGaceta = numeroGaceta;
    }

    public String getLinkPdf() {
        return linkPdf;
    }

    public void setLinkPdf(String linkPdf) {
        this.linkPdf = linkPdf;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
