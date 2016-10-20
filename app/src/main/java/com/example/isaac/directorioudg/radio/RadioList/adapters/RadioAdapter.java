package com.example.isaac.directorioudg.radio.RadioList.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.Radio;
import com.example.isaac.directorioudg.lib.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by isaac on 11/07/16.
 */
public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {


    private List<Radio> radioList;

    private ImageLoader imageLoader;
    OnItemClickListener onItemClickListener;


    public RadioAdapter(List<Radio> radioList,
                        ImageLoader imageLoader,
                        OnItemClickListener onItemClickListener) {
        this.radioList = radioList;
        this.onItemClickListener = onItemClickListener;
        this.imageLoader=imageLoader;
    }

    /** Encargado de crear
     * los nuevos objetos ViewHolder
     * necesarios para los elementos de la colección.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_radio, parent, false);
        return new ViewHolder(v);
    }

    /** Encargado de actualizar
     * los datos de un ViewHolder ya existente.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Radio radio = radioList.get(position);
        holder.setOnItemClickListener(radio,onItemClickListener);


        imageLoader.load(holder.imagePrepa,radio.getSourceImagen());
        holder.txtRadio.setText(radio.getStationName());
    }

    /**
     * Indica el número de elementos
     * de la colección de datos.
     */
    @Override
    public int getItemCount() {
        return radioList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imagePrepa)
        CircleImageView imagePrepa;
        @Bind(R.id.txtPrepa)
        TextView txtRadio;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        public void setOnItemClickListener(final Radio radio,
                                           final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    listener.onItemClick(radio);
                }
            });
        }
    }

}
