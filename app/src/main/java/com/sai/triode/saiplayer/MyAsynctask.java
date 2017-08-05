package com.sai.triode.saiplayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Subhajit Mondal on 05-08-2017.
 */

public class MyAsynctask extends AsyncTask<String,Void,Bitmap> {

    private ImageView imageView;

    public MyAsynctask(ImageView imageView) {
        this.imageView=imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap;
        if(strings[0]!=null) {
            bitmap = BitmapFactory.decodeFile(strings[0]);
        }
        else{
            bitmap=null;
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap!=null&&imageView!=null) {
            imageView.setImageBitmap(bitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setVisibility(View.VISIBLE);
        }
    }
}
