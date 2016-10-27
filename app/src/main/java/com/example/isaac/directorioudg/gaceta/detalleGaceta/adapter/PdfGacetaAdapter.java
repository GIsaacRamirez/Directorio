package com.example.isaac.directorioudg.gaceta.detalleGaceta.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.LinksPdfGaceta;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Isaac on 11/10/2016.
 */

public class PdfGacetaAdapter extends RecyclerView.Adapter<PdfGacetaAdapter.ViewHolder> {
    public List<LinksPdfGaceta> List = null;

    public PdfGacetaAdapter() {
    }

    public void setList(List<LinksPdfGaceta> list) {
        this.List = list;
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
                .inflate(R.layout.item_gaceta_detalle, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Encargado de actualizar
     * los datos de un ViewHolder ya existente.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LinksPdfGaceta linksPdfGaceta = List.get(position);
        holder.lblTitulo.setText(linksPdfGaceta.getTitulo());
        holder.lblidGaceta.setText(linksPdfGaceta.getDescripcion());
        holder.linkDescarga = linksPdfGaceta.getLinkPdf();
    }

    @Override
    public int getItemCount() {
        if (List == null) {
            return 0;
        }
        return List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        @Bind(R.id.lblTitulo)
        TextView lblTitulo;
        @Bind(R.id.lblidGaceta)
        TextView lblidGaceta;


        protected String linkDescarga;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.btnDescargar})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnDescargar:


                    break;
            }
        }

    }
}
