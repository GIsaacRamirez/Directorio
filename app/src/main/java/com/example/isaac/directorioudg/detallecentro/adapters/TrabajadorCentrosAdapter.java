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

        holder.trabajadorCentro = trabajadorCentroList.get(position);

        imageLoader.load(holder.imageTrabajador, holder.trabajadorCentro.getImagen().toString(), false);
        holder.puestoTrabajador.setText(holder.trabajadorCentro.getPuesto());
        holder.lblNombreTrabajador.setText(holder.trabajadorCentro.getNombre());
        holder.txtTelefonoTrabajador.setText("Tel. " + holder.trabajadorCentro.getTelefono());
        holder.txtCorreoTrabajador.setText(holder.trabajadorCentro.getCorreo());
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

        TrabajadorCentro trabajadorCentro;
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
            sendEmail(trabajadorCentro.getCorreo());
        }

        private void sendEmail(String emailTo) {
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:"));
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
            context.startActivity(Intent.createChooser(email, "Seleccionar aplicación"));
        }

    }

}
