package com.aaratechnologies.wallpaperapp.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.aaratechnologies.wallpaperapp.databinding.RowItemBinding;
import com.aaratechnologies.wallpaperapp.models.Wallpaper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class AdapterWallpapers extends RecyclerView.Adapter<AdapterWallpapers.mViewHolder> {

    private List<Wallpaper> wallpapers;
    private Context context;
    private RecycleViewClickInterface recycleViewClickInterface;

    public AdapterWallpapers(List<Wallpaper> wallpapers, Context context, RecycleViewClickInterface recycleViewClickInterface) {
        this.wallpapers = wallpapers;
        this.context = context;
        this.recycleViewClickInterface = recycleViewClickInterface;
    }

    @NonNull
    @Override
    public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new mViewHolder(RowItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull mViewHolder holder, int position) {

        Log.d("cghjk", "onBindViewHolder: " + wallpapers.get(position).getSrc().getMedium());
        Glide.with(context).load(wallpapers.get(position).getSrc().getMedium())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.rowItemBinding.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.rowItemBinding.wallpaper);
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder {
        RowItemBinding rowItemBinding;

        public mViewHolder(@NonNull RowItemBinding rowItemBinding) {
            super(rowItemBinding.getRoot());
            this.rowItemBinding = rowItemBinding;
            rowItemBinding.wallpaper.setOnClickListener(v -> {
                recycleViewClickInterface.onItemClick(getAdapterPosition());
            });
        }

    }
}
