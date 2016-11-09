package com.example.isaac.directorioudg.gaceta.detalleGaceta.adapter;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.LinksPdfGaceta;
import com.example.isaac.directorioudg.entities.LinksPdfGaceta_Table;
import com.example.isaac.directorioudg.gaceta.pdfView;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.io.File;
import java.util.List;
import java.util.StringTokenizer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Isaac on 11/10/2016.
 */

public class PdfGacetaAdapter extends RecyclerView.Adapter<PdfGacetaAdapter.ViewHolder> {
    public List<LinksPdfGaceta> List = null;
    Context context;
    String nombre_archivo;

    public PdfGacetaAdapter() {
    }

    public void setContext(Context context) {
        this.context = context;
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
        holder.linksPdfGaceta = linksPdfGaceta;
        LinksPdfGaceta auxLinkPdf = new Select().from(LinksPdfGaceta.class).where(LinksPdfGaceta_Table.id.is(linksPdfGaceta.getId())).querySingle();
        if(auxLinkPdf==null){
            holder.btnEliminar.setVisibility(View.INVISIBLE);
            holder.btnVisualizar.setVisibility(View.INVISIBLE);
            holder.btnDescargar.setVisibility(View.VISIBLE);
        }else {
            holder.btnEliminar.setVisibility(View.VISIBLE);
            holder.btnVisualizar.setVisibility(View.VISIBLE);
            holder.btnDescargar.setVisibility(View.INVISIBLE);
            holder.linksPdfGaceta=auxLinkPdf;
        }

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

        @Bind(R.id.btnVisualizar)
        Button btnVisualizar;
        @Bind(R.id.btnDescargar)
        Button btnDescargar;
        @Bind(R.id.btnEliminar)
        Button btnEliminar;

         LinksPdfGaceta linksPdfGaceta =new LinksPdfGaceta();



        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }


        @OnClick({R.id.btnVisualizar, R.id.btnDescargar, R.id.btnEliminar})
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.btnVisualizar:
                    Bundle bundle = new Bundle();
                    bundle.putString("nombreArchivo", linksPdfGaceta.getNombreArchivo());
                    Intent intent = new Intent(context, pdfView.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtras(bundle);
                   context.startActivity(intent);

                    break;
                case R.id.btnDescargar:
                    String servicestring = Context.DOWNLOAD_SERVICE;

                    StringTokenizer st = new StringTokenizer(linksPdfGaceta.getLinkPdf());
                    while (st.hasMoreTokens()) {
                        nombre_archivo = st.nextToken("/");
                    }
                    nombre_archivo = nombre_archivo.replaceAll("%20", "");//Remplaza los espacios en blanco por %20 para el link

                    linksPdfGaceta.setNombreArchivo(nombre_archivo);
                    linksPdfGaceta.save();
                    DownloadManager downloadmanager;
                    downloadmanager = (DownloadManager) context.getSystemService(servicestring);
                    Uri uri = Uri.parse(linksPdfGaceta.getLinkPdf());
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setVisibleInDownloadsUi(false);//Oculta la descarga
                    request.setDestinationInExternalFilesDir(context, null, nombre_archivo);

                    Long reference = downloadmanager.enqueue(request);
                    context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

                    break;
                case R.id.btnEliminar:
                    String pathArchivo= Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/Android/data/com.example.isaac.directorioudg/files/";
                    //pathArchivo+=linksPdfGaceta.getNombreArchivo();

                    linksPdfGaceta.delete();
                    try {
                        File archivo = new File(pathArchivo,linksPdfGaceta.getNombreArchivo());
                        archivo.delete();
                    }catch (Exception e){}


                    btnEliminar.setVisibility(View.INVISIBLE);
                    btnVisualizar.setVisibility(View.INVISIBLE);
                    btnDescargar.setVisibility(View.VISIBLE);

                    break;
            }
        }

        //Accion a ejecutar cuando se completa la descarga
        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                intent = new Intent(ctxt, pdfView.class);
                Bundle bundle = new Bundle();
                bundle.putString("nombreArchivo", nombre_archivo);
                intent.putExtras(bundle);
                btnDescargar.setVisibility(View.INVISIBLE);
                btnVisualizar.setVisibility(View.VISIBLE);
                btnEliminar.setVisibility(View.VISIBLE);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                ctxt.startActivity(intent);
            }
        };
    }
}
