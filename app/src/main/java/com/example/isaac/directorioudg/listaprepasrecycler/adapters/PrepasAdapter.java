package com.example.isaac.directorioudg.listaprepasrecycler.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.Prepa;
import com.example.isaac.directorioudg.lib.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by isaac on 11/07/16.
 */
public class PrepasAdapter extends RecyclerView.Adapter<PrepasAdapter.ViewHolder> {


    public List<Prepa> prepaList;

    private ImageLoader imageLoader;
    OnItemClickListener onItemClickListener;


    public PrepasAdapter(List<Prepa> prepaList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.prepaList = prepaList;
        this.onItemClickListener = onItemClickListener;
        this.imageLoader = imageLoader;
    }
    public PrepasAdapter( ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.imageLoader = imageLoader;
    }

    public void setPrepaList(List<Prepa> prepaList) {
        this.prepaList = prepaList;
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

        Prepa prepa = prepaList.get(position);
        holder.setOnItemClickListener(prepa, onItemClickListener);

        String url = prepa.getImagenURL().toString();
        if (url.equalsIgnoreCase("No Disponible")) {
            url = "http://appdirectorioudg.com/photo.jpg";
        }
        imageLoader.load(holder.imagePrepa, url, true);

        holder.txtPrepa.setText(prepa.getPreparatoria());
        holder.lbldireccion.setText(prepa.getDireccion()+", "+prepa.getMunicipio());

    }

    /**
     * Indica el número de elementos
     * de la colección de datos.
     */
    @Override
    public int getItemCount() {
        return prepaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imagen)
        CircleImageView imagePrepa;
        @Bind(R.id.txtItem)
        TextView txtPrepa;
        @Bind(R.id.lbldireccion)
        TextView lbldireccion;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        public void setOnItemClickListener(final Prepa prepa,
                                           final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    listener.onItemClick(prepa);
                }
            });
        }
    }

}
