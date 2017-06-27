package com.example.isaac.directorioudg.entities;

import com.example.isaac.directorioudg.db.DirectorioDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Isaac on 14/11/2016.
 */
@Table( database= DirectorioDataBase.class)
public class TrabajadorCentro extends BaseModel {
    public TrabajadorCentro() {
    }

    @PrimaryKey
    @Column(name = "puesto") private String puesto;
    @PrimaryKey
    @Column (name = "nombre")private String nombre;
    @Column private int idcentro;

    @Column private String imagen;

    @Column private String telefono;
    @Column private String correo;
    @Column private int Estatus;
    @Column private int numerodeOrdenamiento;


    public int getIdcentro() {
        return idcentro;
    }

    public void setIdcentro(int idcentro) {
        this.idcentro = idcentro;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int estatus) {
        Estatus = estatus;
    }

    public int getNumerodeOrdenamiento() {
        return numerodeOrdenamiento;
    }

    public void setNumerodeOrdenamiento(int numerodeOrdenamiento) {
        this.numerodeOrdenamiento = numerodeOrdenamiento;
    }

}
