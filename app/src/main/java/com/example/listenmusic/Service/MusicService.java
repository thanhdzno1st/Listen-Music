// Updated MusicService
package com.example.listenmusic.Service;

import static com.example.listenmusic.Activity.Music_Activity.mangSong;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.listenmusic.Models.Song;

import java.util.ArrayList;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    IBinder mBinder = new MyBinder();
    MediaPlayer mediaPlayer;
    ArrayList<Song> musicFiles = new ArrayList<>();
    Uri uri;
    int position = -1;
    ActionPlaying actionPlaying;
    @Override
    public void onCreate() {
        super.onCreate();
        musicFiles = mangSong;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("MusicService", "Service Bound");
        return mBinder;
    }
    public class MyBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int myPosition = intent.getIntExtra("servicePosition",-1);
        String actionName = intent.getStringExtra("ActionName");
        if(myPosition!=-1){
            playMedia(myPosition);
        }
        if(actionName!=null){
            switch (actionName){
                case "Play/Pause":
                    Toast.makeText(this,"Play/Pause",Toast.LENGTH_SHORT).show();
                    if(actionPlaying != null){
                        actionPlaying.btn_play_pauseClicked();
                    }
                    break;
                case "Next":
                    Toast.makeText(this,"Next",Toast.LENGTH_SHORT).show();
                    if(actionPlaying != null){
                        actionPlaying.btn_nextClicked();
                    }
                    break;
                case "Previous":
                    Toast.makeText(this,"Previous",Toast.LENGTH_SHORT).show();
                    if(actionPlaying != null){
                        actionPlaying.btn_prevClicked();
                    }
                    break;
            }
        }
        return START_STICKY;
    }

    public void playMedia(int StartPosition) {
        musicFiles = mangSong;
        position = StartPosition;
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            if(musicFiles!=null){
                createMediaPlayer(position);
                mediaPlayer.start();
            }
        }else {
            createMediaPlayer(position);
            mediaPlayer.start();
        }

    }

    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    void release(){
        mediaPlayer.release();
    }
    public int getDuration() {
        return mediaPlayer != null ? mediaPlayer.getDuration() : 0;
    }
    public void seekTo(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }
    public void createMediaPlayer(int positionInner){
        position = positionInner;
        uri = Uri.parse(musicFiles.get(position).getLinkBaiHat());
        mediaPlayer = MediaPlayer.create(getBaseContext(),uri);
    }
    public int getCurrentPosition() {
        return mediaPlayer != null ? mediaPlayer.getCurrentPosition() : 0;
    }
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }
    public void OnCompleted(){
        mediaPlayer.setOnCompletionListener(this);
    }
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(actionPlaying != null){
            actionPlaying.btn_nextClicked();
        }
        createMediaPlayer(position);
        mediaPlayer.start();
        OnCompleted();
    }
    public void setCallBack(ActionPlaying actionPlaying){
        this.actionPlaying = actionPlaying;
    }
}
