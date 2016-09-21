package com.example.isaac.directorioudg.radio;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;
import com.example.isaac.directorioudg.R;
import com.example.isaac.directorioudg.radio.parser.UrlParser;
import com.example.isaac.directorioudg.util.Helper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RadioActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    @Bind(R.id.play)
    public ImageButton btnplay;
    @Bind(R.id.progressBar2)
    public ProgressBar progressBar2;
    @Bind(R.id.seekBar)
    public SeekBar seekBar;
    @Bind(R.id.textView1)
    ImageView imagenRadio;
    String name;
    int image;

    Helper helper = new Helper(this);
    View view;

    public String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        ButterKnife.bind(this);
        MusicService.removeNotification(getApplicationContext());
        view=getWindow().findViewById(android.R.id.content);

        Bundle bundle = this.getIntent().getExtras();
        if(bundle!=null){
            //Es para averiguar si hay una estacion reproduciendose
            //Cargar la adecuada si se viene desde la notificacion
            name=bundle.getString("name");
            image=bundle.getInt("image");
            URL = bundle.getString("url");
        }else {
            //Si no viene de la notificacion se cargan los datos pero no se guardan para usar en la notificacion
            //hasta que se reproduce
            image=MusicService.getSourceImagen();
            imagenRadio.setImageResource(image);
            name=MusicService.getStationName();
            URL=MusicService.getDataSource();
        }
        imagenRadio.setImageResource(image);
        setTitle(name);

        if (null == MusicService.getPlayer()) {
            startService(new Intent(getApplicationContext(), MusicService.class));
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnplay.setBackgroundResource(R.drawable.ic_play_circle_outline_black_24px);

        if (null != MusicService.getPlayer() && MusicService.getPlayer().isPlaying()) {
            if (!MusicService.getDataSource().equals(URL)) {
                Snackbar.make(view, getResources().getString(R.string.radio_playing_other), Snackbar.LENGTH_LONG).show();
            } else {
                btnplay.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_24px);
            }
        }


        seekBar.setProgress(100);//Manejar la barra de volumen
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volumen = (float) (seekBar.getProgress()) / 100;
                MusicService.getPlayer().setVolume(volumen, volumen);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        //Si hay una estacion reproduciendo al salir se lanza la notificacion
    }

    @OnClick(R.id.play)
    public void onClick() {

            if (null != MusicService.getPlayer() && !MusicService.getPlayer().isPlaying()) {
                if(helper.isConect()){
                    btnplay.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_24px);
                    progressBar2.setVisibility(View.VISIBLE);
                    MusicService.setStationName(name);
                    MusicService.setSourceImagen(image);
                    MusicService.setMediaActivity(this);
                    MusicService.getPlayer().setOnPreparedListener(this);
                    MusicService.getPlayer().setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                        Snackbar.make(view,"Error",Snackbar.LENGTH_SHORT).show();
                            return false;
                        }
                    });

                    //Verificar volumen
                    AudioManager am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
                    int volume_level = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                    if (volume_level < 3) {
                        Toast.makeText(this, getResources().getString(R.string.volume_low), Toast.LENGTH_SHORT).show();
                    }
                    startPlaying();
                } else {
                    Snackbar.make(view, getResources().getString(R.string.isnotConnected), Snackbar.LENGTH_LONG).show();
                }
            } else {
                btnplay.setBackgroundResource(R.drawable.ic_play_circle_outline_black_24px);
                stopPlaying();
            }


    }

    private void startPlaying() {
        Log.v("INFO", "Start playing");
        try {
            URL = UrlParser.getUrl(URL);
            MusicService.setDataSource(URL);

            MusicService.getPlayer().prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //MusicService.getPlayer().prepareAsync();
    }

    private void stopPlaying() {
        MusicService.resetPlayer(this);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        progressBar2.setVisibility(View.GONE);
        MusicService.startPlaying(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(MusicService.getPlayer().isPlaying())
        {
            MusicService.startNotification(getApplicationContext());
        }
    }


    /**
     *Para que al momento de presionar la flecha de back en la toolbar
     *  me regrese exactamente a la activity anterior
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
