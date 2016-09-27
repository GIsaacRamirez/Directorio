package com.example.isaac.directorioudg.radio;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.example.isaac.directorioudg.R;

import java.io.IOException;


/**
 * Created by isaac on 26/07/16.
 */


public class MusicService extends Service  {

    private static MediaPlayer player;
    private static RadioActivity mediaActivity;
    private static String url;
    public static PhoneStateListener phoneStateListener;
    static int NOTIFICATION_ID = 2015;
    public static String NOTIFICATION_FILTER = "stop_playing";

    private static int sourceImagen;
    private static String StationName;
    public MusicService() {}

    public static int getSourceImagen() {
        return sourceImagen;
    }

    public static void setSourceImagen(int sourceImagen) {
        MusicService.sourceImagen = sourceImagen;
    }

    public static String getStationName() {
        return StationName;
    }

    public static void setStationName(String stationName) {
        StationName = stationName;
    }
    public static String getDataSource(){
        return url;
    }
    public static MediaPlayer getPlayer() {
        return player;
    }
    public static RadioActivity getMediaActivity() {
        return mediaActivity;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // if the mediaPlayer object is null, create it
        if(player == null) {
            player = new MediaPlayer();
        }

        // acquire partial wake lock to allow background playback
        player.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);

        registerReceiver(stopServiceReceiver, new IntentFilter(NOTIFICATION_FILTER));

        //Listener para cuando se recibe llamada
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    resetPlayer(MusicService.this);
                } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                    //Could resume music here, but listener is removed.
                } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    resetPlayer(MusicService.this);
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };
    }
    // release mediaPlayer object on service destruction
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("INFO","Destroyed");
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public static void startPlaying(Context context){
        player.start();
        mediaActivity.btnPlay.setBackgroundResource(R.drawable.ic_pause_circle_outline_black_24px);
    }

    public static void startNotification(Context context){


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.logo_udg)
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .setContentTitle(context.getResources().getString(R.string.notification_playing))
                        .setContentText(context.getResources().getString(R.string.notification_stop));

        Intent notificationIntent = new Intent(getMediaActivity(), RadioActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        builder.setContentIntent(contentIntent);

        //Para llamar al broadcast y detener el player al pulsar el la notificacion
        /**PendingIntent contentIntent =
         PendingIntent.getBroadcast(context, 0, new Intent(NOTIFICATION_FILTER),
         PendingIntent.FLAG_UPDATE_CURRENT);*/

        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());

        TelephonyManager mgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
    public static void removeNotification(Context context){
        NotificationManager nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.cancel(NOTIFICATION_ID);

        TelephonyManager mgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        if(mgr != null) {
            mgr.listen(phoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }
    public static void resetPlayer(Context context){

        removeNotification(context);
        player.reset();
    }

    public static void setDataSource(String source)  {
        try {
            url = source;
            player.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setMediaActivity(RadioActivity radioActivity){
        mediaActivity = radioActivity;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        resetPlayer(this);
    }

    //Detener en el click de la notificacion cuando se llama al broadcast
    protected BroadcastReceiver stopServiceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            resetPlayer(context);
        }
    };


    //No se usa
    /*static class Player extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            Boolean prepared;
            try {
                player.setDataSource(params[0]);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        //isinitial = false;
                        mp.stop();
                        mp.reset();
                    }
                });
                player.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                prepared = false;
                e.printStackTrace();
            }
            //isinitial = true;
            return prepared;
        }
        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            Log.d("Prepared", "//" + result);
            player.start();
           // isinitial = false;
            if(mediaActivity!=null){
                mediaActivity.progressBar3.setVisibility(View.GONE);
            }
            else {
                mediaActivity.progressBar3.setVisibility(View.GONE);
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(mediaActivity!=null){
                mediaActivity.progressBar3.setVisibility(View.VISIBLE);
            }
            else {
                mediaActivity.progressBar3.setVisibility(View.VISIBLE);
            }
        }
    }*/

}
