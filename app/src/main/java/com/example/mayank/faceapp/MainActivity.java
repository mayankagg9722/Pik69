package com.example.mayank.faceapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {


    FloatingActionButton add,gallery,capture;
    boolean isOpen = false;

    Animation fabOpen, fabClose, fabClockwise, fabAnticlockwise;

    private static final int img=1;
    private static final int cap=2;

    ImageView imageView;


   // FaceOverlayView mFaceOverlayView;

    ImageButton button;

    Intent i;

    static Uri capturedImageUri = null;
    static  int flag=0;

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

        i=new Intent(MainActivity.this,ShowImage.class);

        //mFaceOverlayView = (FaceOverlayView) findViewById( R.id.face_overlay );




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
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),img);
            }
        });

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                Calendar cal = Calendar.getInstance();
                File dir=new File(Environment.getExternalStorageDirectory()+"/Pik69");
                if(!dir.exists()){
                    dir.mkdir();
                }
                File file = new File(dir,(cal.getTimeInMillis() + ".jpg"));
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                capturedImageUri = Uri.fromFile(file);
                Log.v("uri",capturedImageUri.toString());
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, capturedImageUri);
                startActivityForResult(intent,cap);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageView.getDrawable()!=null) {
                    //startActivity(new Intent(MainActivity.this, ProgressBar.class));
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"First,select any image. ",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==img&&resultCode==RESULT_OK && data !=null && data.getData()!=null){
            data.getData();
            //Log.e("uridata",data.getData().toString());

            if(data.getData()!=null){
                flag=0;
                i.putExtra("passuri",data.getData().toString());
                Log.v("uri",data.getData().toString());
                imageView.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                //mFaceOverlayView.setBitmap(bitmap);
            }

        }
        else if(requestCode==cap && resultCode==RESULT_OK ){
            //Bundle extra=data.getExtras();
          //  Bitmap photo=(Bitmap)extra.get("data");
           //photo=getResizedBitmap(photo,600,450);
            try {
                flag=1;
                imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(getContentResolver()
                        ,capturedImageUri));
                //mFaceOverlayView.setBitmap(MediaStore.Images.Media.getBitmap(getContentResolver()
                  //      ,capturedImageUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //imageView.setImageBitmap(photo);

            //mFaceOverlayView.setBitmap(photo);
        }
    }

   /*
   public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}