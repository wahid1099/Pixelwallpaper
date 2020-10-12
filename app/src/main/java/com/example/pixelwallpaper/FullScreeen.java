package com.example.pixelwallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;
import java.net.URI;

public class FullScreeen extends AppCompatActivity {
    String originalUrl="";
    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screeen);
        getSupportActionBar().hide();
        Intent intent=getIntent();
        originalUrl=intent.getStringExtra("orginalurl");
        photoView=findViewById(R.id.photoview);
        Glide.with(this).load(originalUrl).into(photoView);
    }

    public void setWallpaperevent(View view) {
        WallpaperManager wallpaperManager=WallpaperManager.getInstance(this);
        Bitmap bitmap=((BitmapDrawable)photoView.getDrawable()).getBitmap();
        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(this, "Wallpaper Applied Succesfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DownloadwallpaperEvent(View view) {
        DownloadManager downloadManager=(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri=Uri.parse(originalUrl);
        DownloadManager.Request request=new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
        Toast.makeText(this, "Downloading Start", Toast.LENGTH_SHORT).show();
    }
}