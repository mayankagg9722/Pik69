package com.example.mayank.faceapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class DetectShow extends AppCompatActivity {

    byte[] BYTE;
    ByteArrayOutputStream bytearrayoutputstream;
    FaceOverlayView mFaceOverlayView;
    Bitmap bitmap;
    Bitmap bitmaptwo;
    Uri paths;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect_show);
        mFaceOverlayView = (FaceOverlayView) findViewById( R.id.face_overlay );

        InputStream stream=getResources().openRawResource(R.raw.sixthimage);

        bitmap = BitmapFactory.decodeStream(stream);

        bitmap=getResizedBitmap(bitmap,600,450);

       /* MainActivity.Getdata getdata=new MainActivity.Getdata();
        paths=getdata.gallerydata;

        Log.e("uridata",paths.toString());

        try
        {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() ,paths);
            bitmap=getResizedBitmap(bitmap,600,450);
            mFaceOverlayView.setBitmap(bitmap);
        }
        catch (Exception e)
        {
            Toast.makeText(DetectShow.this,"error",Toast.LENGTH_SHORT).show();
        }
*/
       //InputStream stream=getResources().openRawResource(R.raw.newimage);
        //bitmap=getResizedBitmap(bitmap,600,450);
        mFaceOverlayView.setBitmap(bitmap);
        //bitmap.compress(Bitmap.CompressFormat.JPEG,40, bytearrayoutputstream);
        //BYTE=bytearrayoutputstream.toByteArray();
        //bitmaptwo=Bitmap.createScaledBitmap(bitmap,120,120,false);
        //bitmaptwo = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);



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
