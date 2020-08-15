package com.example.assnetworking.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.assnetworking.R;
import com.example.assnetworking.adapter.FavoriteAdapter;
import com.example.assnetworking.model.Photo;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private EditText edtSearch;
    private RecyclerView rvSearch;
    private Button btnSearch;
    private String FORMAT = "json";
    private String NOJSONCALLBACK = "1";
    private List<Photo> photoList;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        edtSearch = root.findViewById(R.id.edtSearch);
        btnSearch = root.findViewById(R.id.btnSearch);
        rvSearch = root.findViewById(R.id.rvSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidNetworking.initialize(getActivity());
                AndroidNetworking.setParserFactory(new JacksonParserFactory());
                AndroidNetworking.get("https://www.flickr.com/services/rest/")
                        .addQueryParameter("method", "flickr.photos.search")
                        .addQueryParameter("api_key", "1fc1d51cdab4089c967fb002b32d1679")
                        .addQueryParameter("text", edtSearch.getText().toString().trim())
                        .addQueryParameter("extras", "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o")
                        .addQueryParameter("per_page", "20")
                        .addQueryParameter("page", "1")
                        .addQueryParameter("format", FORMAT)
                        .addQueryParameter("nojsoncallback", NOJSONCALLBACK)
                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {Log.e("ASAAAA", response.toString());

                        photoList = new ArrayList<>();
                        for (int i = 0; i < response.getJSONObject("photos").getJSONArray("photo").length(); i++) {
                            String count_views = response.getJSONObject("photos").getJSONArray("photo").getJSONObject(i).getString("views");
                            String linkImgCover = response.getJSONObject("photos").getJSONArray("photo").getJSONObject(i).getString("url_n");
                            photoList.add(new Photo(count_views, linkImgCover));
                        }
                            FavoriteAdapter favoriteAdapter = new FavoriteAdapter(getActivity(), photoList);
                            rvSearch.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                            rvSearch.setAdapter(favoriteAdapter);
                        } catch (JSONException e){

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ABBBBBBB", anError.getMessage());
                    }
                });
            }
        });
        return root;
    }
}