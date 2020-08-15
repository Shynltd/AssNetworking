package com.example.assnetworking.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.assnetworking.EndlessRecyclerViewScrollListener;
import com.example.assnetworking.ImageFragment;
import com.example.assnetworking.ItemClickSupport;
import com.example.assnetworking.MainActivity;
import com.example.assnetworking.R;
import com.example.assnetworking.adapter.GalleryAdapter;
import com.example.assnetworking.adapter.ViewPagerAdapter;
import com.example.assnetworking.model.Galleries;
import com.example.assnetworking.model.Photo;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private List<Galleries> galleriesList;
    private String METHOD = "flickr.galleries.getList";
    private String API_KEY = "1fc1d51cdab4089c967fb002b32d1679";
    private String USER_ID = "187041022@N07";
    private String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";
    private String PER_PAGE = "5";
    private String PAGE = "1";
    private String FORMAT = "json";
    private String NOJSONCALLBACK = "1";
    private RecyclerView rvGallery;
    private ViewPager viewPager;
    private GalleryViewModel galleryViewModel;
    private String link_info = "https://www.flickr.com/services/rest/?method=flickr.galleries.getInfo&api_key=1fc1d51cdab4089c967fb002b32d1679&gallery_id=72157715377744466&format=json&nojsoncallback=1";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        rvGallery = root.findViewById(R.id.rv_gallery);
        viewPager = root.findViewById(R.id.viewPager);
        galleriesList = new ArrayList<>();
        AndroidNetworking.initialize(getActivity());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
        AndroidNetworking.get("https://www.flickr.com/services/rest/")
                .addQueryParameter("method", METHOD)
                .addQueryParameter("api_key", API_KEY)
                .addQueryParameter("user_id", USER_ID)
                .addQueryParameter("extras", FULL_EXTRAS)
                .addQueryParameter("per_page", PER_PAGE)
                .addQueryParameter("page", PAGE)
                .addQueryParameter("format", FORMAT)
                .addQueryParameter("nojsoncallback", NOJSONCALLBACK)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (int i = 0; i < response.getJSONObject("galleries").getJSONArray("gallery").length(); i++) {
                                String id_gallery = response.getJSONObject("galleries").getJSONArray("gallery").getJSONObject(i).getString("gallery_id");
                                int count_views = response.getJSONObject("galleries").getJSONArray("gallery").getJSONObject(i).getInt("count_views");
                                int count_photo = response.getJSONObject("galleries").getJSONArray("gallery").getJSONObject(i).getInt("count_photos");
                                String linkImgCover = response.getJSONObject("galleries").getJSONArray("gallery").getJSONObject(i).getString("url");
                                galleriesList.add(new Galleries(id_gallery, linkImgCover, count_photo, count_views));
                            }
                            GalleryAdapter galleryAdapter = new GalleryAdapter(getActivity(), galleriesList);
                            rvGallery.setHasFixedSize(true);
                            rvGallery.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                            rvGallery.setAdapter(galleryAdapter);
                            galleryAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("AAAAABBBBBBBBBB", e.getMessage());
                        }

                    }

                    @Override
                    public void onError(ANError error) {
                        Log.e("BBBBBBBBBBBBBBBB", error.getMessage());
                        // handle error
                    }
                });
        ItemClickSupport.addTo(rvGallery).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                String id_galle = galleriesList.get(position).getGalleryId();
                Log.e("id_galle", id_galle);
                AndroidNetworking.get("https://www.flickr.com/services/rest/")
                        .addQueryParameter("method", "flickr.galleries.getPhotos")
                        .addQueryParameter("api_key", API_KEY)
                        .addQueryParameter("gallery_id", id_galle)
                        .addQueryParameter("extras", FULL_EXTRAS)
                        .addQueryParameter("per_page", "20")
                        .addQueryParameter("page", PAGE)
                        .addQueryParameter("format", FORMAT)
                        .addQueryParameter("nojsoncallback", NOJSONCALLBACK)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                ArrayList<Photo> photoList = new ArrayList<>();
                                try {

                                    for (int i = 0; i < response.getJSONObject("photos").getJSONArray("photo").length(); i++) {
                                        String linkImgCover = response.getJSONObject("photos").getJSONArray("photo").getJSONObject(i).getString("url_c");
                                        Log.e("linkImgCover", linkImgCover);
                                        photoList.add(new Photo(linkImgCover));
                                    }
                                    FragmentManager fragmentManager = getFragmentManager();
                                    ImageFragment imageFragment = new ImageFragment(photoList);
                                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                                    transaction.replace(R.id.nav_host_fragment, imageFragment).commit();
//                                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                                    Bundle bundle = new Bundle();
//                                    bundle.putSerializable("data", photoList);
//                                    intent.putExtra("bundle", bundle);
//                                    startActivity(intent);




                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("AAAAABBBBBBBBBB", e.getMessage());
                                }

                            }

                            @Override
                            public void onError(ANError error) {
                                Log.e("BBBBBBBBBBBBBBBB", error.getMessage());
                                // handle error
                            }
                        });
            }
        });


        return root;
    }
}