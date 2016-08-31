package com.example.isaac.directorioudg.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.isaac.directorioudg.db.DirectorioDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;


/**
 * Created by isaac on 7/04/16.
 */
@Table( database= DirectorioDataBase.class)
public class Prepa extends BaseModel implements Parcelable{

    @Column
    @PrimaryKey
    private int idPrepa;

    @Column
    private String Preparatoria;
    @Column private int Metropolitana;
    @Column private String Direccion;
    @Column private String Municipio;
    @Column private int CP;
    @Column private String Telefono1;
    @Column private String Telefono2;
    @Column private Double Latitud;
    @Column private Double Longitud;
    @Column private String imagenURL;
    @Column private String WEB;

    @Column private String Director;
    @Column private String FotoDirectorURL;
    @Column private String CorreoDirector;
    @Column private String Secretario;
    @Column private String CorreoSecretario;

    public Prepa() {
    }

    public int getIdPrepa() {
        return idPrepa;
    }

    public void setIdPrepa(int idPrepa) {
        this.idPrepa = idPrepa;
    }

    public String getPreparatoria() {
        return Preparatoria;
    }

    public void setPreparatoria(String preparatoria) {
        Preparatoria = preparatoria;
    }

    public int getMetropolitana() {
        return Metropolitana;
    }

    public void setMetropolitana(int metropolitana) {
        Metropolitana = metropolitana;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public int getCP() {
        return CP;
    }

    public void setCP(int CP) {
        this.CP = CP;
    }

    public String getTelefono1() {
        return Telefono1;
    }

    public void setTelefono1(String telefono1) {
        Telefono1 = telefono1;
    }

    public String getTelefono2() {
        return Telefono2;
    }

    public void setTelefono2(String telefono2) {
        Telefono2 = telefono2;
    }

    public Double getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud = latitud;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }

    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public String getWEB() {
        return WEB;
    }

    public void setWEB(String WEB) {
        this.WEB = WEB;
    }

    public String getDirector() {
        return Director;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public String getFotoDirectorURL() {
        return FotoDirectorURL;
    }

    public void setFotoDirectorURL(String fotoDirectorURL) {
        FotoDirectorURL = fotoDirectorURL;
    }

    public String getCorreoDirector() {
        return CorreoDirector;
    }

    public void setCorreoDirector(String correoDirector) {
        CorreoDirector = correoDirector;
    }

    public String getSecretario() {
        return Secretario;
    }

    public void setSecretario(String secretario) {
        Secretario = secretario;
    }

    public String getCorreoSecretario() {
        return CorreoSecretario;
    }

    public void setCorreoSecretario(String correoSecretario) {
        CorreoSecretario = correoSecretario;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal=false;
        if(obj instanceof Prepa){
            Prepa prepa= (Prepa)obj;
            if (this.idPrepa==prepa.getIdPrepa()) {
                equal=true;
            }
        }
        return equal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(this.idPrepa);
        parcel.writeString(this.Preparatoria);
        parcel.writeInt(this.Metropolitana);
        parcel.writeString(this.Direccion);
        parcel.writeString(this.Municipio);
        parcel.writeInt(this.CP);
        parcel.writeString(this.Telefono1);
        parcel.writeString(this.Telefono2);
        parcel.writeDouble(this.Latitud);
        parcel.writeDouble(this.Longitud);
        parcel.writeString(this.imagenURL);
        parcel.writeString(this.WEB);

        parcel.writeString(this.Director);
        parcel.writeString(this.FotoDirectorURL);
        parcel.writeString(this.CorreoDirector);
        parcel.writeString(this.Secretario);
        parcel.writeString(this.CorreoSecretario);
    }

    protected Prepa(Parcel in){
        this.idPrepa= in.readInt();
        this.Preparatoria=in.readString();
        this.Metropolitana=in.readInt();
        this.Direccion=in.readString();
        this.Municipio=in.readString();
        this.CP=in.readInt();
        this.Telefono1=in.readString();
        this.Telefono2=in.readString();
        this.Latitud=in.readDouble();
        this.Longitud=in.readDouble();
        this.imagenURL=in.readString();
        this.WEB=in.readString();

        this.Director=in.readString();
        this.FotoDirectorURL=in.readString();
        this.CorreoDirector=in.readString();
        this.Secretario=in.readString();
        this.CorreoSecretario=in.readString();
    }

    public static final Parcelable.Creator<Prepa> CREATOR = new Parcelable.Creator<Prepa>(){
        public  Prepa createFromParcel(Parcel source){
            return new Prepa(source);
        }
        public Prepa[] newArray(int size){
            return new Prepa[size];
        }
    };
}

