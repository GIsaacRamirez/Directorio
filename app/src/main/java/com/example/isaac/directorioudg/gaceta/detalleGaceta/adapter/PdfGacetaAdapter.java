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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.entities.LinksPdfGaceta;
import com.example.isaac.directorioudg.gaceta.pdfView;

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

    public void setContext(Context context){
        this.context=context;
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
                    String servicestring = Context.DOWNLOAD_SERVICE;

                    StringTokenizer st = new StringTokenizer(linkDescarga);
                    while (st.hasMoreTokens()){
                        nombre_archivo=st.nextToken("/");
                    }
                    nombre_archivo=nombre_archivo.replaceAll("%20","");
                    Log.e("nombre",nombre_archivo);

                    DownloadManager downloadmanager;
                    downloadmanager = (DownloadManager) context.getSystemService(servicestring);
                    Uri uri = Uri.parse(linkDescarga);
                    DownloadManager.Request request = new DownloadManager.Request(uri);

                    request.setDestinationInExternalFilesDir(context, null,nombre_archivo);
                    Long reference = downloadmanager.enqueue(request);
                    context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                    break;
            }
        }

        BroadcastReceiver onComplete=new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                intent = new Intent(ctxt, pdfView.class);
                Bundle bundle = new Bundle();
                bundle.putString("nombreArchivo",nombre_archivo);
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                ctxt.startActivity(intent);
            }
        };
    }
}
