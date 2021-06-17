package com.aaratechnologies.wallpaperapp;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aaratechnologies.wallpaperapp.databinding.ActivityFullImageBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.IOException;

public class FullImageActivity extends AppCompatActivity {

    ActivityFullImageBinding activityFullImageBinding;

    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFullImageBinding = ActivityFullImageBinding.inflate(getLayoutInflater());
        setContentView(activityFullImageBinding.getRoot());
        getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imageUrl = getIntent().getStringExtra("image");
        Glide.with(this).load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        activityFullImageBinding.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(activityFullImageBinding.imageView);
        activityFullImageBinding.setBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wallManager = WallpaperManager.getInstance(getApplicationContext());
                Bitmap bmpImg = ((BitmapDrawable) activityFullImageBinding.imageView.getDrawable()).getBitmap();
                try {
                    wallManager.setBitmap(bmpImg);
                    Toast.makeText(FullImageActivity.this, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(FullImageActivity.this, "Wallpaper Set Failed!!", Toast.LENGTH_SHORT).show();
                }

//                Toast.makeText(FullImageActivity.this, "Wallpaper Set Failed!!", Toast.LENGTH_SHORT).show();

            }
        });
    }

}