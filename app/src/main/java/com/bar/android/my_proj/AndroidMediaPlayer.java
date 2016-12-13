package com.bar.android.my_proj;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;


public class AndroidMediaPlayer extends Activity {

    private SongTrackerDataSource songds;
    private NextSongDataSource nexts;
    NextSongTracker nextPlay;
    String usergrp;
    String nextsong;

    private MediaPlayer mediaPlayer;
    public TextView songName, duration;
    private double timeElapsed = 0, finalTime = 0;
    private int forwardTime = 2000, backwardTime = 2000;
    private Handler durationHandler = new Handler();
    private SeekBar seekbar;
    private String filename = null;
    //private String searchPath = "/storage/ext_sd/Music/";
    //private String searchPath = "/storage/self/primary/Music/Rum/";
    private  String searchPath = "/system/media/audio/alarms/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //set the layout of the Activity
        setContentView(R.layout.activity_media_player);
        //initialize views

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            usergrp = extras.getString("usergrp");
            nextsong = extras.getString("nextsong");
        }

        songds = new SongTrackerDataSource(this);
        nexts = new NextSongDataSource(this);
        nexts.open();
        songds.open();


        initializeViews();


    }

    public void initializeViews() {
        songName = (TextView) findViewById(R.id.songName);
        mediaPlayer = MediaPlayer.create(this, R.raw.sample_song);//" R.raw.sample_song);
        //filename="ringtone1.mp3";
        //filename="Argon.ogg";

        //Toast.makeText(AndroidMediaPlayer.this,"/storage/ext_sd/Music/ringtone1.mp3", Toast.LENGTH_SHORT).show();
        //Toast.makeText(AndroidMediaPlayer.this, searchPath + filename, Toast.LENGTH_SHORT).show();
        //mediaPlayer = MediaPlayer.create(this, Uri.parse(nextsong));
        //mediaPlayer = MediaPlayer.create(this, Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Music/ringtone1.mp3"));

        finalTime = mediaPlayer.getDuration();
        duration = (TextView) findViewById(R.id.songDuration);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        songName.setText("Sample_Song.mp3");
        seekbar.setMax((int) finalTime);
        seekbar.setClickable(false);
    }

    // play mp3 song
    public void play(View view) {
        mediaPlayer.start();
        timeElapsed = mediaPlayer.getCurrentPosition();
        seekbar.setProgress((int) timeElapsed);
        durationHandler.postDelayed(updateSeekBarTime, 100);

    }

    //handler to change seekBarTime
    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            //get current position
            timeElapsed = mediaPlayer.getCurrentPosition();
            //set seekbar progress
            seekbar.setProgress((int) timeElapsed);
            double timeRemaining = finalTime - timeElapsed;
            duration.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            //repeat yourself that again in 100 miliseconds
            durationHandler.postDelayed(this, 100);
            if(timeElapsed > 10)
            {
                //Code for setting Count or Like to 1
            }
        }
    };

    // pause mp3 song
    public void pause(View view) {
        mediaPlayer.pause();
    }

    // go forward at forwardTime seconds
    public void forward(View view) {
        //check if we can go forward at forwardTime seconds before song endes
        if ((timeElapsed + forwardTime) <= 0){
            timeElapsed = timeElapsed - backwardTime;
            //seek to the exact second of the track
            mediaPlayer.seekTo((int) timeElapsed);
        }
    }

    // go backwards at backwardTime seconds
    public void rewind(View view) {
        //check if we can go back at backwardTime seconds after song starts
        if ((timeElapsed - backwardTime) > 0) {
            timeElapsed = timeElapsed - backwardTime;

            //seek to the exact second of the track
            mediaPlayer.seekTo((int) timeElapsed);
        }
    }
}

