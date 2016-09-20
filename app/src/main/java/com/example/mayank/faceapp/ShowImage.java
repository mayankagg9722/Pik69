package com.example.mayank.faceapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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

            Bitmap photo= null;
            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver()
                        ,Uri.parse(uri));
                photo=getResizedBitmap(photo,900,1200);
                mFaceOverlayView.setBitmap(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }

         /*   if(MainActivity.flag==2) {
                imageView.setImageURI(Uri.parse(uri));
                imageView.setVisibility(View.INVISIBLE);
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                mFaceOverlayView.setBitmap(bitmap);
            }*/
        }

        if(MainActivity.flag==1) {

            Bitmap photo= null;
            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver()
                        ,MainActivity.capturedImageUri);
                photo=getResizedBitmap(photo,900,1200);
                mFaceOverlayView.setBitmap(photo);
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
}

