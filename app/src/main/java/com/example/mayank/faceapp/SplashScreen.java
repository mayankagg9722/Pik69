package com.example.mayank.faceapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class SplashScreen extends AppCompatActivity {

    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        videoView=(VideoView)findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/copy.mp4"));
        Log.v("location",Environment.getExternalStorageDirectory().getPath());
       // MediaController mediaController=new MediaController(this);
       // videoView.setMediaController(mediaController);
       // mediaController.setAnchorView(videoView);


        videoView.start();
        videoView.requestFocus();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
            }
        });



    }
}
