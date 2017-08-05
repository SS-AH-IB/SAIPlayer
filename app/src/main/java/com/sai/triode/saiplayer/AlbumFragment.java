package com.sai.triode.saiplayer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumFragment extends Fragment {



    public AlbumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetMusic getMusic=new GetMusic();
        ArrayList<String> allAlbums=new ArrayList<>();
        allAlbums=getMusic.getAlbums(allAlbums,getActivity().getContentResolver());
        GridView gridView=(GridView)view.findViewById(R.id.album_grid);
        final AlbumAdapter albumAdapter=new AlbumAdapter(getContext(),R.layout.album_cell,allAlbums);
        gridView.setAdapter(albumAdapter);
    }
}
