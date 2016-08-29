package com.example.isaac.directorioudg.listcentros.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.Centro;
import com.example.isaac.directorioudg.lib.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by isaac on 29/08/16.
 */
public class CentrosAdapter extends RecyclerView.Adapter<CentrosAdapter.ViewHolder>{
    public List<Centro> centroList;

    private ImageLoader imageLoader;
    OnItemClickListener onItemClickListener;

    public CentrosAdapter(List<Centro> centroList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.centroList = centroList;
        this.onItemClickListener = onItemClickListener;
        this.imageLoader = imageLoader;
    }

    public CentrosAdapter( ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.imageLoader = imageLoader;
    }

    public void setCentroList(List<Centro> centroList) {
        this.centroList = centroList;
        notifyDataSetChanged();
    }

    /**
     * Encargado de crear
     * los nuevos objetos ViewHolder
     * necesarios para los elementos de la colección.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }


    /**
     * Encargado de actualizar
     * los datos de un ViewHolder ya existente.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Centro centro = centroList.get(position);
        holder.setOnItemClickListener(centro, onItemClickListener);


        String url = centro.getImagenURL().toString();
        if (url.equalsIgnoreCase("No Disponible")) {
            url = "http://appdirectorioudg.com/photo.jpg";
        }
        imageLoader.load(holder.imageCentro, url, true);

        holder.txtCentro.setText(centro.getSigla());
        holder.lbldireccion.setText(centro.getDireccion()+", "+centro.getMunicipio());

    }

    /**
     * Indica el número de elementos
     * de la colección de datos.
     */
    @Override
    public int getItemCount() {
        return centroList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imagen)
        CircleImageView imageCentro;
        @Bind(R.id.txtItem)
        TextView txtCentro;
        @Bind(R.id.lbldireccion)
        TextView lbldireccion;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        public void setOnItemClickListener(final Centro centro,
                                           final com.example.isaac.directorioudg.listcentros.adapters.OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    listener.onItemClick(centro);
                }
            });
        }
    }

}
