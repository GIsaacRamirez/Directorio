package com.example.isaac.directorioudg.radio.RadioList;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.radio.RadioActivity;
import com.example.isaac.directorioudg.entities.Radio;
import com.example.isaac.directorioudg.lib.GlideImageLoader;
import com.example.isaac.directorioudg.lib.ImageLoader;
import com.example.isaac.directorioudg.radio.RadioList.adapters.OnItemClickListener;
import com.example.isaac.directorioudg.radio.RadioList.adapters.RadioAdapter;
import com.example.isaac.directorioudg.radio.UrlParser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RadioList extends Fragment implements OnItemClickListener,RadioListView {


    OnItemClickListener onItemClickListener;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    private RadioAdapter adapter;

    View view = null;
    public List<Radio> radioList = new ArrayList<>();
    public RadioList() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_radio_cucei, container, false);
        ButterKnife.bind(this, view);

        Radio radioUdg = new Radio();
        radioUdg.setStationName("Radio Udg");
        radioUdg.setUrlRadio(UrlParser.getUrl("http://148.202.165.1:8000/;stream/1"));
        radioUdg.setSourceImagen(R.drawable.radiougd);

        Radio radioCucei = new Radio();
        radioCucei.setStationName("Radio CUCEI");
        radioCucei.setUrlRadio("http://s3.streammonster.com:8225/autodj.m3u");
        radioCucei.setSourceImagen(R.drawable.radiocucei);

        Radio radioAmeca = new Radio();
        radioAmeca.setStationName("Radio Ameca");
        radioAmeca.setUrlRadio("http://148.202.87.222:8000/;stream/1");
        radioAmeca.setSourceImagen(R.drawable.ameca);

        Radio radioAutlan = new Radio();
        radioAutlan.setStationName("Radio Autlán");
        radioAutlan.setUrlRadio("http://148.202.114.39:8000/;stream/1");
        radioAutlan.setSourceImagen(R.drawable.ameca);//**

        Radio radioGuzman = new Radio();
        radioGuzman.setStationName("Radio Guzman");
        radioGuzman.setUrlRadio("http://148.202.119.233:8080/;stream/1");
        radioGuzman.setSourceImagen(R.drawable.ameca);//**

        Radio radioColotlan = new Radio();
        radioColotlan.setStationName("Radio Colotlan");
        radioColotlan.setUrlRadio("http://148.202.79.112:8000/;stream/1");
        radioColotlan.setSourceImagen(R.drawable.ameca);//**

        Radio radioLagosMoreno = new Radio();
        radioLagosMoreno.setStationName("Lagos de Moreno");
        radioLagosMoreno.setUrlRadio("http://148.202.62.3:8000/;stream/1");
        radioLagosMoreno.setSourceImagen(R.drawable.ameca);//**

        Radio radioOcotlan = new Radio();
        radioOcotlan.setStationName("Radio Ocotlan");
        radioOcotlan.setUrlRadio("http://148.202.62.3:8000/;stream/1");
        radioOcotlan.setSourceImagen(R.drawable.ameca);//**

        Radio radioVallarta = new Radio();
        radioVallarta.setStationName("Radio Vallarta");
        radioVallarta.setUrlRadio("http://148.202.110.152:8000/;stream/1");
        radioVallarta.setSourceImagen(R.drawable.ameca);//**


        radioList.add(radioUdg);
        radioList.add(radioCucei);
        radioList.add(radioAmeca);
        radioList.add(radioAutlan);
        radioList.add(radioGuzman);
        radioList.add(radioColotlan);
        radioList.add(radioLagosMoreno);
        radioList.add(radioOcotlan);
        radioList.add(radioVallarta);
        setupAdapter();
        setupRecyclerView();
        return view;
    }

    private void setupAdapter() {
        adapter = new RadioAdapter(getContext(), radioList, provideImageLoader(getActivity()), this);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(Radio radio) {
        Intent intent = new Intent(getActivity(), RadioActivity.class);
        Bundle bundle = new Bundle();
        //valores
        bundle.putString("url", radio.getUrlRadio());
        bundle.putInt("image",radio.getSourceImagen());
        bundle.putString("name",radio.getStationName());

        intent.putExtras(bundle);//ponerlos en el intent
        startActivity(intent);//iniciar la actividad
    }

    ImageLoader provideImageLoader(Activity activity) {
        GlideImageLoader imageLoader = new GlideImageLoader();
        if (activity != null) {
            imageLoader.setLoaderContext(activity);
        }
        return imageLoader;
    }
}
