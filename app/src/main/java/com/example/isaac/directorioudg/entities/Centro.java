package com.example.isaac.directorioudg.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.isaac.directorioudg.db.DirectorioDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by isaac on 29/08/16.
 */
@Table( database= DirectorioDataBase.class)
public class Centro extends BaseModel implements Parcelable {
    @Column
    @PrimaryKey private int IdCentro;

    @Column private String Sigla;
    @Column private String NombreCentro;
    @Column private int Tematico;
    @Column private String Direccion;
    @Column private String Municipio;
    @Column private int CP;

    public Centro() {
    }

    @Column private double Latitud;
    @Column private double Longitud;

    @Column private String ImagenURL;
    @Column private String Web;

    /*Getters*/
    public int getIdCentro() {
        return IdCentro;
    }

    public String getSigla() {
        return Sigla;
    }

    public String getNombreCentro() {
        return NombreCentro;
    }

    public int getTematico() {
        return Tematico;
    }

    public String getDireccion() {
        return Direccion;
    }

    public String getMunicipio() {
        return Municipio;
    }

    public int getCP() {
        return CP;
    }

    public double getLatitud() {
        return Latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public String getImagenURL() {
        return ImagenURL;
    }

    public String getWeb() {
        return Web;
    }


    /**Setters**/
    public void setIdCentro(int idCentro) {
        IdCentro = idCentro;
    }

    public void setSigla(String sigla) {
        Sigla = sigla;
    }

    public void setNombreCentro(String nombreCentro) {
        NombreCentro = nombreCentro;
    }

    public void setTematico(int tematico) {
        Tematico = tematico;
    }

    public void setDireccion(String direccion) {
        Direccion = direccion;
    }

    public void setMunicipio(String municipio) {
        Municipio = municipio;
    }

    public void setCP(int CP) {
        this.CP = CP;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }

    public void setImagenURL(String imagenURL) {
        ImagenURL = imagenURL;
    }

    public void setWeb(String web) {
        Web = web;
    }




    @Override
    public boolean equals(Object obj) {
        boolean equal=false;
        if(obj instanceof Prepa){
            Centro centro= (Centro) obj;
            if (this.IdCentro==centro.getIdCentro()) {
                equal=true;
            }
        }
        return equal;
    }


    /**Para hacerla parcelable y pasar el objeto centro como parametro en un bundle**/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(this.IdCentro);

        parcel.writeString(this.Sigla);
        parcel.writeString(this.NombreCentro);
        parcel.writeInt(this.Tematico);
        parcel.writeString(this.Direccion);
        parcel.writeString(this.Municipio);
        parcel.writeInt(this.CP);
        parcel.writeDouble(this.Latitud);
        parcel.writeDouble(this.Longitud);

        parcel.writeString(this.ImagenURL);
        parcel.writeString(this.Web);
    }

    protected Centro(Parcel in) {
        IdCentro = in.readInt();
        Sigla = in.readString();
        NombreCentro = in.readString();
        Tematico = in.readInt();
        Direccion = in.readString();
        Municipio = in.readString();
        CP = in.readInt();
        Latitud = in.readDouble();
        Longitud = in.readDouble();
        ImagenURL = in.readString();
        Web = in.readString();
    }

    public static final Creator<Centro> CREATOR = new Creator<Centro>() {
        @Override
        public Centro createFromParcel(Parcel in) {
            return new Centro(in);
        }

        @Override
        public Centro[] newArray(int size) {
            return new Centro[size];
        }
    };
}
