package com.sai.triode.saiplayer;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import de.hdodenhof.circleimageview.CircleImageView;

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
        ViewHolder viewHolder;
        String s = allAlbums.get(position);
        String s1 = "", s2 = "", s3 = "",s4="";
        int flag = 1;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '$') {
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
                    case 4:
                        s4 += s.charAt(i);
                        break;
                }
            } else {
                flag++;
            }
        }
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layoutId, null);
            viewHolder=new ViewHolder();
            viewHolder.albumName = (TextView) convertView.findViewById(R.id.album_name);
            viewHolder.artistName = (TextView) convertView.findViewById(R.id.artist_name);
            viewHolder.numSongs = (TextView) convertView.findViewById(R.id.numSongs);
            viewHolder.albumArt = (ImageView) convertView.findViewById(R.id.album_art);
            viewHolder.myAsyncTask=new MyAsynctask(viewHolder.albumArt).execute(s4);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder)convertView.getTag();
            if(viewHolder.myAsyncTask.getStatus()== AsyncTask.Status.RUNNING)
            {
                viewHolder.myAsyncTask.cancel(true);
            }
        }
        viewHolder.albumName.setText(s1);
        viewHolder.artistName.setText(s2);
        if(s3.equals("1"))
            viewHolder.numSongs.setText(s3+" Song");
        else
            viewHolder.numSongs.setText(s3+" Songs");
        viewHolder.albumArt.setVisibility(View.INVISIBLE);
        //if(viewHolder.position==position)
        //Toast.makeText(getContext(),String.valueOf(viewHolder.position),Toast.LENGTH_SHORT).show();
        //viewHolder.albumArt.setImageBitmap(BitmapFactory.decodeFile(s4));
        viewHolder.myAsyncTask=new MyAsynctask(viewHolder.albumArt).execute(s4);
        convertView.setTag(viewHolder);
        return convertView;
    }

    static class ViewHolder{
        TextView albumName;
        TextView artistName;
        TextView numSongs;
        ImageView albumArt;
        AsyncTask myAsyncTask;
    }
}
