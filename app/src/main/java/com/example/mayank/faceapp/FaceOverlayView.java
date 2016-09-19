package com.example.mayank.faceapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

/**
 * Created by mayank on 17-09-2016.
 */
public class FaceOverlayView extends View {

    private Bitmap mBitmap;
    private SparseArray<Face> mFaces;

    float a[][]=new float[9][2];
    float sum=0;
    float ex=0;
    float ey=0;
    float px=0;
    float py=0;
     static int finalvalue=0;
    int p=0;

    public FaceOverlayView(Context context) {
        this(context, null);
    }

    public FaceOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FaceOverlayView(Context context, AttributeSet attrs, int defStyleAttr){
    super(context, attrs, defStyleAttr);
}

    public void setBitmap( Bitmap bitmap ) {
        mBitmap = bitmap;
        FaceDetector detector = new FaceDetector.Builder( getContext() )
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

        if (!detector.isOperational()) {
            //Handle contingency
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            mFaces = detector.detect(frame);
           // Log.e("details","faces="+mFaces);
            detector.release();
        }
        logFaceData();
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if ((mBitmap != null) && (mFaces != null)) {
            double scale = drawBitmap(canvas);
           // Log.e("details","scale="+scale);
            drawFaceBox(canvas, scale);
            drawFaceLandmarks(canvas, scale);
        }
    }

    private double drawBitmap( Canvas canvas ) {
        double viewWidth = canvas.getWidth();
       // Log.e("details","canvas width="+viewWidth);
        double viewHeight = canvas.getHeight();
      //  Log.e("details","canvas hieght"+viewHeight);

        double imageWidth = mBitmap.getWidth();
       // Log.e("details","image width="+imageWidth);

        double imageHeight = mBitmap.getHeight();
      //  Log.e("details","image hieght="+imageHeight);

        double scale = Math.min( viewWidth / imageWidth, viewHeight / imageHeight );

        Rect destBounds = new Rect( 0, 0, (int) ( imageWidth * scale ), (int) ( imageHeight * scale ) );
        canvas.drawBitmap( mBitmap, null, destBounds, null );
        return scale;
    }

    private void drawFaceBox(Canvas canvas, double scale) {
        //paint should be defined as a member variable rather than
        //being created on each onDraw request, but left here for
        //emphasis.
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        float left = 0;
        float top = 0;
        float right = 0;
        float bottom = 0;

        for( int i = 0; i < mFaces.size(); i++ ) {
            Face face = mFaces.valueAt(i);

            left = (float) ( face.getPosition().x * scale );

            //Log.e("details","face x="+face.getPosition().x);
            top = (float) ( face.getPosition().y * scale );

            //Log.e("details","face y="+face.getPosition().y);
            //Log.e("details","euler y="+face.getEulerY());
            //Log.e("details","euler z="+face.getEulerZ());

            right = (float) scale * ( face.getPosition().x + face.getWidth() );
            bottom = (float) scale * ( face.getPosition().y + face.getHeight() );

            //Log.e("mayank","euler z="+face.getEulerZ());
            //Log.e("mayank","euler y="+face.getEulerY());
            //Log.e("mayank","left green="+face.getPosition().x*scale);
            //Log.e("mayank","right green="+ scale * ( face.getPosition().x + face.getWidth() ));
            //Log.e("mayank","top green="+face.getPosition().y*scale);
            //Log.e("mayank","bottom green="+scale * ( face.getPosition().y + face.getHeight() ));
            //Log.e("mayank","face hieght"+face.getHeight());
            //Log.e("mayank","face width"+face.getHeight());


            canvas.drawRect( left, top, right, bottom, paint );
        }

    }

    private void drawFaceLandmarks( Canvas canvas, double scale ) {
        Paint paint = new Paint();
        paint.setColor( Color.RED );
        paint.setStyle( Paint.Style.STROKE );
        paint.setStrokeWidth( 5 );

        for( int i = 0; i < mFaces.size(); i++ ) {
            Face face = mFaces.valueAt(i);

            p=0;
            ex=0;
            ey=0;
            px=0;
            py=0;
            float a[][]={{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
            for ( Landmark landmark : face.getLandmarks() ) {
                //Log.e("mayank","mayank cool="+landmark.getPosition().x+"---"+landmark.getPosition().y);
                float cx = (int) ( landmark.getPosition().x * scale );
                float cy = (int) ( landmark.getPosition().y * scale );
                    a[p][0]=cx;
                    a[p][1]=cy;
                    p=p+1;
                canvas.drawCircle( cx, cy, 10, paint );
            }

            ex= (float)Math.abs(((50)*(((a[0][1]-a[4][1]))/((a[4][1]-a[7][1])))));

            //Log.v("loop",String.valueOf(ex));

            ey= (float)Math.abs((1)*(((a[2][0]-a[3][0]))/((a[5][0]-a[6][0]))));

            //Log.v("loop",String.valueOf(ey));

            px=(float)(3.24-ex)/(float)3.24;
            py=(float)(1.31-ey)/(float)1.31;
            sum=sum+(ex+ey);

            //Log.v("loop",String.valueOf(sum));

        }
        if(mFaces.size()!=0) {
            finalvalue = ((100 - ((int) sum) / mFaces.size())+12);
            if(finalvalue>100){
                int t=finalvalue-100;
                finalvalue=finalvalue-t;
            }
        }
        else{
            finalvalue=0;
        }
       // Log.e("loop", String.valueOf(finalvalue));
        sum=0;
    }
    private void logFaceData() {
        float smilingProbability;
        float leftEyeOpenProbability;
        float rightEyeOpenProbability;
        float eulerY;
        float eulerZ;
        for( int i = 0; i < mFaces.size(); i++ ) {
            Face face = mFaces.valueAt(i);

            smilingProbability = face.getIsSmilingProbability();
            leftEyeOpenProbability = face.getIsLeftEyeOpenProbability();
            rightEyeOpenProbability = face.getIsRightEyeOpenProbability();
            eulerY = face.getEulerY();
            eulerZ = face.getEulerZ();

           // Log.e( "details", "Smiling: " + smilingProbability );
           // Log.e( "details", "Left eye open: " + leftEyeOpenProbability );
           // Log.e( "details", "Right eye open: " + rightEyeOpenProbability );
           // Log.e( "details", "Euler Y: " + eulerY );
           // Log.e( "details", "Euler Z: " + eulerZ );
        }
    }

}
