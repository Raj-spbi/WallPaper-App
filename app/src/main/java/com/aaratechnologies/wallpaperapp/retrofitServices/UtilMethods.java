package com.aaratechnologies.wallpaperapp.retrofitServices;

import android.content.Context;

import com.aaratechnologies.wallpaperapp.models.WallpaperResponse;

import org.json.JSONException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtilMethods {
    public interface ApiCallBack {
        void onSucess(Object object);
    }

    public interface ApiCallBackTwoMethod {
        void onSucess(Object object) throws JSONException;

        void onError(String errorMsg);
    }

    public static void LoadWalls(final Context context, String apiKey, int pageCount, int perPage, final ApiCallBackTwoMethod apiCallBack) {
        Call<WallpaperResponse> bodyCall = RetrofitClient.getInstance().getDataServices().getWallpaper(apiKey, pageCount, perPage);
        bodyCall.enqueue(new Callback<WallpaperResponse>() {
            @Override
            public void onResponse(Call<WallpaperResponse> call, Response<WallpaperResponse> response) {
                if (response.isSuccessful() && null != response) {
                    try {
//                        apiCallBack.onSucess(response.body().getPhotosLists());
                        WallpaperResponse response1 = response.body();
                        apiCallBack.onSucess(response1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    apiCallBack.onError("noRecordFound");
                }
            }


            @Override
            public void onFailure(Call<WallpaperResponse> call, Throwable t) {
                apiCallBack.onError("Something Went Wrong :" + t.getMessage());
            }
        });
    }

    public static void LoadSearchedWalls(final Context context, String apiKey, int pageCount, int perPage, String query, final ApiCallBackTwoMethod apiCallBack) {
        Call<WallpaperResponse> bodyCall = RetrofitClient.getInstance().getDataServices().searchWallpapers(apiKey, pageCount, perPage, "Alone Boy");
        bodyCall.enqueue(new Callback<WallpaperResponse>() {
            @Override
            public void onResponse(Call<WallpaperResponse> call, Response<WallpaperResponse> response) {
                if (response.isSuccessful() && null != response) {
                    try {
//                        apiCallBack.onSucess(response.body().getPhotosLists());
                        WallpaperResponse response1 = response.body();
                        apiCallBack.onSucess(response1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    apiCallBack.onError("noRecordFound");
                }
            }


            @Override
            public void onFailure(Call<WallpaperResponse> call, Throwable t) {
                apiCallBack.onError("Something Went Wrong :" + t.getMessage());
            }
        });
    }


}
