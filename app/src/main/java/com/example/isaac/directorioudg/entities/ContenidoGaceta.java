package com.example.isaac.directorioudg.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.isaac.directorioudg.db.DirectorioDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.Model;

/**
 * Created by Usuario on 10/10/2016.
 */
@Table( database= DirectorioDataBase.class)
public class ContenidoGaceta implements Parcelable, Model {

    public ContenidoGaceta() {
    }

    @Column
    @PrimaryKey
    private int id;
    @Column private String fecha;
    @Column private String urlContenido;
    @Column private String urlImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUrlContenido() {
        return urlContenido;
    }

    public void setUrlContenido(String urlContenido) {
        this.urlContenido = urlContenido;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal=false;
        if(obj instanceof Prepa){
            ContenidoGaceta contenidoGaceta= (ContenidoGaceta) obj;
            if (this.id==contenidoGaceta.getId()) {
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.fecha);
        dest.writeString(this.urlContenido);
        dest.writeString(this.urlImage);
    }

    protected ContenidoGaceta(Parcel in) {
        this.id = in.readInt();
        this.fecha = in.readString();
        this.urlContenido = in.readString();
        this.urlImage = in.readString();
    }

    public static final Parcelable.Creator<ContenidoGaceta> CREATOR = new Parcelable.Creator<ContenidoGaceta>() {
        @Override
        public ContenidoGaceta createFromParcel(Parcel source) {
            return new ContenidoGaceta(source);
        }

        @Override
        public ContenidoGaceta[] newArray(int size) {
            return new ContenidoGaceta[size];
        }
    };
}
