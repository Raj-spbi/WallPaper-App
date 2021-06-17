package com.aaratechnologies.wallpaperapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WallpaperResponse {

    @SerializedName("photos")
    private List<Wallpaper> photosLists;

    public WallpaperResponse(List<Wallpaper> photosLists) {
        this.photosLists = photosLists;
    }

    public List<Wallpaper> getPhotosLists() {
        return photosLists;
    }

    public void setPhotosLists(List<Wallpaper> photosLists) {
        this.photosLists = photosLists;
    }
}
