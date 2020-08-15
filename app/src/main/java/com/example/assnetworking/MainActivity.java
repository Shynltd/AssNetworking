package com.example.assnetworking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.assnetworking.adapter.ViewPagerAdapter;
import com.example.assnetworking.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private String METHOD = "flickr.galleries.getList";
    private String API_KEY = "1fc1d51cdab4089c967fb002b32d1679";
    private String USER_ID = "187041022@N07";
    private String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";
    private String PER_PAGE = "5";
    private String PAGE = "1";
    private String FORMAT = "json";
    private String NOJSONCALLBACK = "1";
    String linkUrl;

    private String linkTest = "http://192.168.1.8:3000/api";
    private String libraryLink = "https://www.flickr.com/services/rest/?method=flickr.galleries.getList&api_key=1fc1d51cdab4089c967fb002b32d1679&user_id=187041022%40N07&per_page=10&page=1&format=json&nojsoncallback=1";
    private TextView tvName;
    private String linkFavorite = "https://www.flickr.com/services/rest/?method=flickr.favorites.getList&api_key=1fc1d51cdab4089c967fb002b32d1679&user_id=187041022%40N07&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&per_page=5&page=1&format=json&nojsoncallback=1";
    String name;

    private ViewPager viewPager1;
    List<Photo> photoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        photoList = new ArrayList<>();
        photoList.add(new Photo("https://live.staticflickr.com/4380/37238874681_b150f39513_b.jpg"));
        photoList.add(new Photo("https://live.staticflickr.com/4254/35035242002_c25de70cb7_b.jpg"));
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(photoList,getApplicationContext());
        Log.e("aaaaaaaaaaaaa", String.valueOf(photoList.size()));
        viewPager1.setAdapter(viewPagerAdapter);
    }



    private void initView() {
        viewPager1 = (ViewPager) findViewById(R.id.viewPager1);
    }
}