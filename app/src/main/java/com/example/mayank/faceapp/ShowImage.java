package com.example.mayank.faceapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class ShowImage extends AppCompatActivity {

    ImageView imageView;
    String uri;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        bundle=this.getIntent().getExtras();
            uri=bundle.getString("uripass");

        if(bundle!=null && bundle.containsKey("uripass")) {
            //Log.v("geturi", uri);
            imageView.setImageURI(Uri.parse(uri));
        }


    }
}
