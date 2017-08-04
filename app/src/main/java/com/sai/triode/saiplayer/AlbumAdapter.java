package com.sai.triode.saiplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subhajit Mondal on 04-08-2017.
 */

public class AlbumAdapter extends ArrayAdapter<String> {

    private ArrayList<String> allAlbums;
    private Context context;
    private int layoutId;

    public AlbumAdapter(Context context,int layoutId,ArrayList<String> allAlbums) {
        super(context,layoutId,allAlbums);
        this.allAlbums = allAlbums;
        this.context = context;
        this.layoutId=layoutId;
    }

    @Override
    public int getCount() {
        return allAlbums.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(layoutId,null);
        }
        String s=allAlbums.get(position);
        String s1="",s2="",s3="";
        int flag=1;
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)!='$') {
                switch (flag) {
                    case 1:
                        s1 += s.charAt(i);
                        break;
                    case 2:
                        s2 += s.charAt(i);
                        break;
                    case 3:
                        s3 += s.charAt(i);
                        break;
                }
            }
            else {
                flag++;
            }
        }
        TextView albumName=(TextView)convertView.findViewById(R.id.album_name);
        albumName.setText(s1);
        TextView artistName=(TextView)convertView.findViewById(R.id.artist_name);
        artistName.setText(s2);
        Bitmap bm= BitmapFactory.decodeFile(s3);
        ImageView albumArt=(ImageView)convertView.findViewById(R.id.album_art);
        albumArt.setScaleType(ImageView.ScaleType.CENTER_CROP);
        albumArt.setImageBitmap(bm);
        return convertView;
    }
}
