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

    @Column private String Rector;
    @Column private String TelefonoRector;
    @Column private String CorreoRector;
    @Column private String FotoRectorURL;

    @Column private String SecretarioAcademico;
    @Column private String TelefonoSecAcademico;
    @Column private String CorreoSecAcademico;
    @Column private String FotoSecAcademicoURL;

    @Column private String SecretarioAdministrativo;
    @Column private String TelefonoSecAdministrativo;
    @Column private String CorreoSecAdministrativo;
    @Column private String FotoSecAdministrativoURL;


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

    public String getRector() {
        return Rector;
    }

    public String getTelefonoRector() {
        return TelefonoRector;
    }

    public String getCorreoRector() {
        return CorreoRector;
    }

    public String getFotoRectorURL() {
        return FotoRectorURL;
    }

    public String getSecretarioAcademico() {
        return SecretarioAcademico;
    }

    public String getTelefonoSecAcademico() {
        return TelefonoSecAcademico;
    }

    public String getCorreoSecAcademico() {
        return CorreoSecAcademico;
    }

    public String getFotoSecAcademicoURL() {
        return FotoSecAcademicoURL;
    }

    public String getSecretarioAdministrativo() {
        return SecretarioAdministrativo;
    }

    public String getTelefonoSecAdministrativo() {
        return TelefonoSecAdministrativo;
    }

    public String getCorreoSecAdministrativo() {
        return CorreoSecAdministrativo;
    }

    public String getFotoSecAdministrativoURL() {
        return FotoSecAdministrativoURL;
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

    public void setRector(String rector) {
        Rector = rector;
    }

    public void setTelefonoRector(String telefonoRector) {
        TelefonoRector = telefonoRector;
    }

    public void setCorreoRector(String correoRector) {
        CorreoRector = correoRector;
    }

    public void setFotoRectorURL(String fotoRectorURL) {
        FotoRectorURL = fotoRectorURL;
    }

    public void setSecretarioAcademico(String secretarioAcademico) {
        SecretarioAcademico = secretarioAcademico;
    }

    public void setTelefonoSecAcademico(String telefonoSecAcademico) {
        TelefonoSecAcademico = telefonoSecAcademico;
    }

    public void setCorreoSecAcademico(String correoSecAcademico) {
        CorreoSecAcademico = correoSecAcademico;
    }

    public void setFotoSecAcademicoURL(String fotoSecAcademicoURL) {
        FotoSecAcademicoURL = fotoSecAcademicoURL;
    }

    public void setSecretarioAdministrativo(String secretarioAdministrativo) {
        SecretarioAdministrativo = secretarioAdministrativo;
    }

    public void setTelefonoSecAdministrativo(String telefonoSecAdministrativo) {
        TelefonoSecAdministrativo = telefonoSecAdministrativo;
    }

    public void setCorreoSecAdministrativo(String correoSecAdministrativo) {
        CorreoSecAdministrativo = correoSecAdministrativo;
    }

    public void setFotoSecAdministrativoURL(String fotoSecAdministrativoURL) {
        FotoSecAdministrativoURL = fotoSecAdministrativoURL;
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

        parcel.writeString(this.Rector);
        parcel.writeString(this.TelefonoRector);
        parcel.writeString(this.CorreoRector);
        parcel.writeString(this.FotoRectorURL);

        parcel.writeString(this.SecretarioAcademico);
        parcel.writeString(this.TelefonoSecAcademico);
        parcel.writeString(this.CorreoSecAcademico);
        parcel.writeString(this.FotoSecAcademicoURL);

        parcel.writeString(this.SecretarioAdministrativo);
        parcel.writeString(this.TelefonoSecAdministrativo);
        parcel.writeString(this.CorreoSecAdministrativo);
        parcel.writeString(this.FotoSecAdministrativoURL);
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
        Rector = in.readString();
        TelefonoRector = in.readString();
        CorreoRector = in.readString();
        FotoRectorURL = in.readString();
        SecretarioAcademico = in.readString();
        TelefonoSecAcademico = in.readString();
        CorreoSecAcademico = in.readString();
        FotoSecAcademicoURL = in.readString();
        SecretarioAdministrativo = in.readString();
        TelefonoSecAdministrativo = in.readString();
        CorreoSecAdministrativo = in.readString();
        FotoSecAdministrativoURL = in.readString();
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
