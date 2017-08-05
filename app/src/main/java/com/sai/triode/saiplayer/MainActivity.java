package com.sai.triode.saiplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private static final int MY_PERMISSION_READ_EXTERNAL_STORAGE = 4567;
    GenreFragment genreFragment;
    AlbumFragment albumFragment;
    FolderFragment folderFragment;
    boolean isPermissionGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NavigationView navigationView=(NavigationView)findViewById(R.id.main_nav);
        final DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.main_content_ll);
        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,
                drawerLayout,R.string.nav_drawer_open,R.string.nav_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                linearLayout.setTranslationX(slideOffset * drawerView.getWidth());
                drawerLayout.bringChildToFront(drawerView);
                drawerLayout.requestLayout();
                drawerLayout.setScrimColor(Color.TRANSPARENT);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        ImageButton genre=(ImageButton)findViewById(R.id.genre);
        ImageButton album=(ImageButton)findViewById(R.id.album);
        ImageButton folder=(ImageButton)findViewById(R.id.folder);

        genre.setOnClickListener(this);
        album.setOnClickListener(this);
        folder.setOnClickListener(this);

        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container,new NoPermissionFragment());
        fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_READ_EXTERNAL_STORAGE);
        }
        else
        {
            if(!isPermissionGranted) {
                genreFragment = new GenreFragment();
                folderFragment = new FolderFragment();
                albumFragment = new AlbumFragment();

                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.container, albumFragment);
                fragmentTransaction2.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction2.commit();
            }
        }

        ImageView bg_img = (ImageView) findViewById(R.id.bg_img);
        blurImage(bg_img);
    }

    private void blurImage(ImageView bg_img) {
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.s);
        Bitmap blurredBitmap = BlurBuilder.blur( this, originalBitmap );
        bg_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bg_img.setImageDrawable(new BitmapDrawable(getResources(),blurredBitmap));
    }

    @Override
    public void onClick(View view) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            switch (view.getId())
            {
                case R.id.genre: fragmentTransaction.replace(R.id.container,genreFragment);
                    break;
                case R.id.album: fragmentTransaction.replace(R.id.container,albumFragment);
                    break;
                case R.id.folder: fragmentTransaction.replace(R.id.container,folderFragment);
                    break;
            }
            fragmentTransaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case MY_PERMISSION_READ_EXTERNAL_STORAGE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    genreFragment=new GenreFragment();
                    folderFragment=new FolderFragment();
                    albumFragment=new AlbumFragment();

                    isPermissionGranted=true;
                }
                break;
            default: super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isPermissionGranted)
        {
            FragmentTransaction fragmentTransaction2=getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.container,albumFragment);
            fragmentTransaction2.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction2.commit();
        }
    }
}
