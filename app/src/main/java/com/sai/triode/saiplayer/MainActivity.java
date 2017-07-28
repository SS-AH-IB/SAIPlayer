package com.sai.triode.saiplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView bg_img = (ImageView) findViewById(R.id.bg_img);
        blurImage(bg_img);
    }

    private void blurImage(ImageView bg_img) {
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s);
        Bitmap blurredBitmap = BlurBuilder.blur( this, originalBitmap );
        bg_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bg_img.setImageDrawable(new BitmapDrawable(getResources(),blurredBitmap));
    }
}
