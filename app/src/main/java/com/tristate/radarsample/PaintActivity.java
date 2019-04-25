package com.tristate.radarsample;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class PaintActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView mImgSource;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        mImgSource = (ImageView) findViewById(R.id.mImgSource);
        mImgSource.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();

                final Point p1 = new Point();
                p1.x = (int) x; //x co-ordinate where the user touches on the screen
                p1.y = (int) y; //y co-ordinate where the user touches on the screen
                Bitmap bitmap = ((BitmapDrawable) mImgSource.getDrawable()).getBitmap();
                final int sourceColor = bitmap.getPixel((int) x, (int) y);
                new ColorFillOpt(bitmap, p1, Color.parseColor("#D81B60"), sourceColor).doInBackground();
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public class ColorFillOpt extends AsyncTask<Void, Void, Void> {

        private final Bitmap bitmap;
        private final Point p1;
        private final int titleColor;
        private final int sourceColor;

        public ColorFillOpt(Bitmap bitmap, Point p1, int titleColor, int sourceColor) {
            this.bitmap = bitmap;
            this.p1 = p1;
            this.titleColor = titleColor;
            this.sourceColor = sourceColor;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            BitmapUtil.floodFill(bitmap, p1, getTitleColor(), sourceColor);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mImgSource.setImageBitmap(bitmap);
            super.onPostExecute(aVoid);

        }
    }
}
