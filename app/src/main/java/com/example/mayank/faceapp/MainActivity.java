package com.example.mayank.faceapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    FloatingActionButton add,gallery,capture;
    boolean isOpen = false;

    Animation fabOpen, fabClose, fabClockwise, fabAnticlockwise;

    private static final int img=1;
    private static final int cap=2;

    //Uri urigallery,uricap;
   // Bitmap uricapture;
    ImageView imageView;

   // Getdata getdata=new Getdata();

    FaceOverlayView mFaceOverlayView;
    //Bitmap bitmap;

    ImageButton button;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        capture=(FloatingActionButton)findViewById(R.id.camera);
        add=(FloatingActionButton)findViewById(R.id.add);
        gallery=(FloatingActionButton)findViewById(R.id.gallery);

        fabOpen = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fab_close);
        fabClockwise = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_clockwise);
        fabAnticlockwise = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_anticlockwise);

        imageView=(ImageView)findViewById(R.id.imageView3);

        button=(ImageButton)findViewById(R.id.button);



        mFaceOverlayView = (FaceOverlayView) findViewById( R.id.face_overlay );

        InputStream stream=getResources().openRawResource(R.raw.sixthimage);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isOpen) {
                    add.startAnimation(fabAnticlockwise);
                    capture.startAnimation(fabClose);
                    gallery.startAnimation(fabClose);
                    capture.setClickable(false);
                    gallery.setClickable(false);
                    isOpen = false;
                } else {
                    add.startAnimation(fabClockwise);
                    capture.startAnimation(fabOpen);
                    gallery.startAnimation(fabOpen);
                    gallery.setClickable(true);
                    capture.setClickable(true);
                    isOpen = true;
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i,"Select Picture"),img);
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,cap);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageView.getDrawable()!=null) {
                    startActivity(new Intent(MainActivity.this, ProgressBar.class));
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Seect image first.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public static class Getdata{
        Uri gallerydata;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==img&&resultCode==RESULT_OK && data !=null && data.getData()!=null){
            data.getData();
            Log.e("uridata",data.getData().toString());
          //getdata.gallerydata=data.getData();
          imageView.setImageURI(data.getData());

            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            mFaceOverlayView.setBitmap(bitmap);

        }
        else if(requestCode==cap && resultCode==RESULT_OK ){
            Bundle extra=data.getExtras();
            Bitmap photo=(Bitmap)extra.get("data");
            //bitmap = BitmapFactory.decodeStream(stream);

           // photo=getResizedBitmap(photo,600,450);

            imageView.setImageBitmap(photo);

            mFaceOverlayView.setBitmap(photo);
        }
    }

 /*   public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    */

}