package com.aaratechnologies.wallpaperapp.retrofitServices;

import com.aaratechnologies.wallpaperapp.models.WallpaperResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GetDataServices {

    @GET("curated")
    Call<WallpaperResponse> getWallpaper(
            @Header("Authorization") String ApiKey,
            @Query("page") int pageCount,
            @Query("per_page") int per_page

    );

    @GET("search")
    Call<WallpaperResponse> searchWallpapers(
            @Header("Authorization") String ApiKey,
            @Query("page") int pageCount,
            @Query("per_page") int per_page,
            @Query("query") String query

    );
}
