package com.aaratechnologies.wallpaperapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.aaratechnologies.wallpaperapp.Adapter.AdapterWallpapers;
import com.aaratechnologies.wallpaperapp.Adapter.RecycleViewClickInterface;
import com.aaratechnologies.wallpaperapp.databinding.ActivityMainBinding;
import com.aaratechnologies.wallpaperapp.models.WallpaperResponse;
import com.aaratechnologies.wallpaperapp.retrofitServices.UtilMethods;
import com.aaratechnologies.wallpaperapp.retrofitServices.WebURLS;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements RecycleViewClickInterface {

    ActivityMainBinding binding;
    private int pageCount = 1;
    private static int per_page = 60;

    WallpaperResponse response;
    NestedScrollView nestedScrollView;
    private String queryTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initRecyclerView();
        queryTxt="Alone Boy";
        setUpPagination(true);

    }

    private void setUpPagination(boolean isPaginationAllowed) {
        if (isPaginationAllowed) {
            binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                        fetchData(++pageCount);
//                        Toast.makeText(MainActivity.this, "" + pageCount, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            binding.nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                }
            });
        }
    }

    private void initRecyclerView() {
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setLayoutManager(new GridLayoutManager(this, 2));
        fetchData(pageCount);
    }

    private void fetchData(int pageCount) {
        UtilMethods.LoadSearchedWalls(MainActivity.this, WebURLS.Api_Key, pageCount, per_page, queryTxt, new UtilMethods.ApiCallBackTwoMethod() {
            @Override
            public void onSucess(Object object) throws JSONException {
                response = (WallpaperResponse) object;
                AdapterWallpapers adapterWallpapers = new AdapterWallpapers(response.getPhotosLists(), getApplicationContext(), MainActivity.this);
                binding.recycler.setAdapter(adapterWallpapers);
                adapterWallpapers.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(MainActivity.this, "" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), FullImageActivity.class);
        intent.putExtra("image", response.getPhotosLists().get(position).getSrc().getLarge());
        startActivity(intent);
    }
}