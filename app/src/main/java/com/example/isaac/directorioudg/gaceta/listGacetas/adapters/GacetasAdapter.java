package com.example.isaac.directorioudg.gaceta.listGacetas.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.ContenidoGaceta;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Usuario on 11/10/2016.
 */

public class GacetasAdapter extends RecyclerView.Adapter<GacetasAdapter.ViewHolder> {

    public List<ContenidoGaceta> List = null;
    private ImageLoader imageLoader;

    public GacetasAdapter(List<ContenidoGaceta> List, Context context) {
        this.List = List;
        this.imageLoader = new GlideImageLoader(context);
    }

    public void setList(List<ContenidoGaceta> list) {
        List = list;
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
                .inflate(R.layout.item_gaceta, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Encargado de actualizar
     * los datos de un ViewHolder ya existente.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ContenidoGaceta contenidoGaceta=List.get(position);
        String url= contenidoGaceta.getUrlImage();
        holder.txtidGaceta.setText(""+contenidoGaceta.getId());
        holder.txtFecha.setText(contenidoGaceta.getFecha());
        holder.linkDescarga=contenidoGaceta.getUrlContenido();
        imageLoader.load(holder.imgPortada,url,false);
    }

    @Override
    public int getItemCount() {
        if (List.isEmpty()) {
            return 0;
        }
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        protected String linkDescarga;
        @Bind(R.id.imgPortada)
        ImageView imgPortada;
        @Bind(R.id.txtidGaceta)
        TextView txtidGaceta;
        @Bind(R.id.txtFecha)
        TextView txtFecha;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.imgDownload, R.id.imgDelete})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imgDownload:
                    Log.v("Gaceta","click");
                    break;
                case R.id.imgDelete:
                    break;
            }
        }

    }
}
