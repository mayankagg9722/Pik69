package com.example.mayank.faceapp;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.util.Random;

public class ProgressBar extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textView,comment;

    int number;

    String[]zero={"Beloved user, please don't ask questions you don't want the answers to.","May I interest you with other " +
            "jokes rather than you showing me your face?"};
    String[]ten={"I don’t believe in plastic surgery. But in your case, go ahead. Only if, you can afford it.",
             "Do you really want the truth? Let it pass."};
    String[]thirty={"Sometimes I don't know whether to laugh at you or pity you.",
             "Sometimes, it is better to keep things secret."};
    String[]fifty={ "I never forget a face, but in your case I’ll be glad to make an exception.",
             "Nothing a good make-up can't rectify."};
    String[]seventy={"You look pretty, just a little more care and you will be making a hell lot of people jealous.",
             "On the way to pretty. Just a little more push and you are already there."};
    String[]ninety={ "This face is something worth dying for.",
            "You might be bored of hearing these 3 words, but i can’t resist saying it again - You look beautiful."};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_bar);
        textView=(TextView)findViewById(R.id.textView);
        comment=(TextView)findViewById(R.id.textView4);
      animateTextView(0,FaceOverlayView.finalvalue,textView);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

    }

    public void animateTextView(int initialValue, int finalValue, final TextView textview) {
        DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(0.8f);
        int start = Math.min(initialValue, finalValue);
        int end = Math.max(initialValue, finalValue);
        int difference = Math.abs(finalValue - initialValue);
        Handler handler = new Handler();
        for (int count = start; count <= end; count++) {
            int time = Math.round(decelerateInterpolator.getInterpolation((((float) count) / difference)) * 100) * count;
            final int finalCount = ((initialValue > finalValue) ? initialValue - count : count);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textview.setText(finalCount + "%");
                    RandArray randArray=new RandArray();
                    int number=randArray.getRandArrayElement();
                    if(FaceOverlayView.finalvalue>=0 && FaceOverlayView.finalvalue<=10 )
                        comment.setText(zero[number]);
                    else if(FaceOverlayView.finalvalue>=10 && FaceOverlayView.finalvalue<=30 )
                        comment.setText(ten[number]);
                    else if(FaceOverlayView.finalvalue>=30 && FaceOverlayView.finalvalue<=50 )
                        comment.setText(thirty[number]);
                    else if(FaceOverlayView.finalvalue>=50 && FaceOverlayView.finalvalue<=70 )
                        comment.setText(fifty[number]);
                    else if(FaceOverlayView.finalvalue>=70 && FaceOverlayView.finalvalue<=90 )
                        comment.setText(seventy[number]);
                    else if(FaceOverlayView.finalvalue>=90 && FaceOverlayView.finalvalue<=100 )
                        comment.setText(ninety[number]);
                }
            }, time);
        }
    };

    public class RandArray {
        private int[] items = new int[]{0,1};

        private Random rand = new Random();

        public int getRandArrayElement(){
            return items[rand.nextInt(items.length)];
        }
    }
}
