package com.example.isaac.directorioudg.entities;

import com.example.isaac.directorioudg.db.DirectorioDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Comparator;

/**
 * Created by Isaac on 19/10/2016.
 */
@Table( database= DirectorioDataBase.class)
public class LinksPdfGaceta extends BaseModel {
    @Column
    @PrimaryKey
    private int id;

    @Column private int numeroGaceta;
    @Column private String linkPdf;
    @Column private String titulo;
    @Column private String descripcion;
    @Column private String nombreArchivo;

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public LinksPdfGaceta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
