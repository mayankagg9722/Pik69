package com.example.mayank.faceapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class ShowImage extends AppCompatActivity {

    ImageView imageView;
    //String uri;
    Bundle bundle;
    ImageButton button;

    FaceOverlayView mFaceOverlayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.getimage);
        button=(ImageButton)findViewById(R.id.button);

        mFaceOverlayView = (FaceOverlayView) findViewById( R.id.face_overlay );
        //bundle=this.getIntent().getExtras();
        //  uri=bundle.getString("uripass");

        /*
        if(bundle!=null && bundle.containsKey("uripass")) {

            //Log.v("geturi", uri);
            imageView.setImageURI(Uri.parse(uri));
        }
    */
        if(getIntent().hasExtra("passuri")){
           String uri=getIntent().getStringExtra("passuri");
            Log.v("uri",uri);
            if(MainActivity.flag==0) {
                imageView.setImageURI(Uri.parse(uri));
                imageView.setVisibility(View.INVISIBLE);
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                mFaceOverlayView.setBitmap(bitmap);
            }
        }

        if(MainActivity.flag==1) {
            try {
           /* imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver()
                    ,MainActivity.capturedImageUri));*/
                mFaceOverlayView.setBitmap(MediaStore.Images.Media.getBitmap(getContentResolver()
                        , MainActivity.capturedImageUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShowImage.this, ProgressBar.class));
            }
        });


    }
}

