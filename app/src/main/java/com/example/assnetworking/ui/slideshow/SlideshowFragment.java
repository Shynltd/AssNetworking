package com.example.assnetworking.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.assnetworking.EndlessRecyclerViewScrollListener;
import com.example.assnetworking.ImageFragment;
import com.example.assnetworking.ItemClickSupport;
import com.example.assnetworking.R;
import com.example.assnetworking.adapter.FavoriteAdapter;
import com.example.assnetworking.adapter.ViewPagerAdapter;
import com.example.assnetworking.model.Photo;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {
    private String METHOD = "flickr.favorites.getList";
    private String API_KEY = "1fc1d51cdab4089c967fb002b32d1679";
    private String USER_ID = "187041022@N07";
    private String FULL_EXTRAS = "description ,license ,date_upload ,date_taken ,owner_name ,icon_server ,original_format ,last_update ,geo ,tags ,machine_tags ,o_dims ,views ,media ,path_alias ,url_sq ,url_t ,url_s ,url_q ,url_m ,url_n ,url_z ,url_c ,url_l ,url_o";
    private String PER_PAGE = "10";
    private int PAGE = 1;
    private String FORMAT = "json";
    private String NOJSONCALLBACK = "1";
    private RecyclerView rvFavorite;
    GridLayoutManager staggeredGridLayoutManager;
    private List<Photo> photoList;
    FavoriteAdapter favoriteAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        rvFavorite = root.findViewById(R.id.rvFavorite);
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());


        photoList = new ArrayList<>();
         staggeredGridLayoutManager = new GridLayoutManager(getActivity(),2);
         favoriteAdapter = new FavoriteAdapter(getActivity(), photoList);
        Log.e("list 1", String.valueOf(photoList.size()));
        rvFavorite.setLayoutManager(staggeredGridLayoutManager);
        rvFavorite.setAdapter(favoriteAdapter);
        getPhotoFavorite(1);
//        rvFavorite.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                PAGE++;
//                getPhotoFavorite(PAGE);
//
//
//            }
//        });
        ItemClickSupport.addTo(rvFavorite).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                ImageFragment imageFragment = new ImageFragment(photoList,position);
                transaction.replace(R.id.nav_host_fragment, imageFragment).commit();



            }
        });
        return root;
    }

    private void getPhotoFavorite(int page) {
        AndroidNetworking.get("https://www.flickr.com/services/rest/")
                .addQueryParameter("method", METHOD)
                .addQueryParameter("api_key", API_KEY)
                .addQueryParameter("user_id", USER_ID)
                .addQueryParameter("extras", FULL_EXTRAS)
                .addQueryParameter("per_page", PER_PAGE)
                .addQueryParameter("page", String.valueOf(page))
                .addQueryParameter("format", FORMAT)
                .addQueryParameter("nojsoncallback", NOJSONCALLBACK)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    for (int i = 0; i < response.getJSONObject("photos").getJSONArray("photo").length(); i++) {
                        String count_views = response.getJSONObject("photos").getJSONArray("photo").getJSONObject(i).getString("views");
                        String linkImgCover = response.getJSONObject("photos").getJSONArray("photo").getJSONObject(i).getString("url_n");
                        photoList.add(new Photo(count_views,linkImgCover));

                        Log.e("list 2", String.valueOf(photoList.size()));
                    }
                    favoriteAdapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("BBBBBBBBBB", e.getMessage());
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.e("AAAAABBBBBBBBBB", anError.getMessage());
            }

        });


    }
}