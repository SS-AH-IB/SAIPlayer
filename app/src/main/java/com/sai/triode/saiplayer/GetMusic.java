package com.sai.triode.saiplayer;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Subhajit Mondal on 04-08-2017.
 */

public class GetMusic {
    public ArrayList<String> getAlbums(ArrayList<String> allAlbums,ContentResolver contentResolver){
        Uri uri= MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor=contentResolver.query(uri,new String[]{MediaStore.Audio.Albums.ALBUM, MediaStore.Audio.Albums.ARTIST
        , MediaStore.Audio.Albums.ALBUM_ART},null,null,null);
        if(cursor!=null&&cursor.moveToFirst())
        {
            int colAlbum=cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM);
            int colArtist=cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST);
            int colArt=cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART);

            do{
                String album=cursor.getString(colAlbum);
                String artist=cursor.getString(colArtist);
                String art=cursor.getString(colArt);

                allAlbums.add(album+"$"+artist+"$"+art);

            }while (cursor.moveToNext());
        }
        Collections.sort(allAlbums);
        return allAlbums;
    }
}
