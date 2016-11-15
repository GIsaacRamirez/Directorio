package com.example.isaac.directorioudg.detallecentro.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.TrabajadorCentro;
import com.example.isaac.directorioudg.lib.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by isaac on 29/08/16.
 */
public class TrabajadorCentrosAdapter extends RecyclerView.Adapter<TrabajadorCentrosAdapter.ViewHolder> {
    public List<TrabajadorCentro> trabajadorCentroList;
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    private ImageLoader imageLoader;


    public TrabajadorCentrosAdapter(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public void setTrabajadorCentroList(List<TrabajadorCentro> trabajadorCentroList) {
        this.trabajadorCentroList = trabajadorCentroList;
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
                .inflate(R.layout.item_centro_trabajador, parent, false);
        return new ViewHolder(v);
    }


    /**
     * Encargado de actualizar
     * los datos de un ViewHolder ya existente.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TrabajadorCentro trabajadorCentro = trabajadorCentroList.get(position);
        String url = trabajadorCentro.getImagen().toString();
        imageLoader.load(holder.imageTrabajador, url, false);
        holder.puestoTrabajador.setText(trabajadorCentro.getPuesto());
        holder.lblNombreTrabajador.setText(trabajadorCentro.getNombre());
        holder.txtTelefonoTrabajador.setText("Tel. " + trabajadorCentro.getTelefono());
        holder.txtCorreoTrabajador.setText(trabajadorCentro.getCorreo());
    }

    /**
     * Indica el número de elementos
     * de la colección de datos.
     */
    @Override
    public int getItemCount() {
        if (trabajadorCentroList == null) {
            return 0;
        }
        return trabajadorCentroList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imageTrabajador)
        ImageView imageTrabajador;
        @Bind(R.id.puestoTrabajador)
        TextView puestoTrabajador;
        @Bind(R.id.lblNombreTrabajador)
        TextView lblNombreTrabajador;
        @Bind(R.id.txtTelefonoTrabajador)
        TextView txtTelefonoTrabajador;
        @Bind(R.id.txtCorreoTrabajador)
        TextView txtCorreoTrabajador;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }
        @OnClick(R.id.txtCorreoTrabajador)
        public void onClick() {
        }

        private void sendEmail(String emailTo) {
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:"));
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
            context.startActivity(Intent.createChooser(email, "Seleccionar aplicación"));
        }

    }

}
