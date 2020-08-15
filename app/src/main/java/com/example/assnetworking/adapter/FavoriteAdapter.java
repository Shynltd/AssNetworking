package com.example.assnetworking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assnetworking.R;
import com.example.assnetworking.model.Photo;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {
    private Context context;
    private List<Photo> photoList;


    public FavoriteAdapter(Context context, List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_favorite, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, final int position) {
        holder.tvViewCount.setText(photoList.get(position).getViews()+"");
        Glide.with(context).load(photoList.get(position).getUrlC()).into(holder.imgFavorite);
    }

    @Override
    public int getItemCount() {
        if (photoList == null) return 0;
        return photoList.size();
    }

    public static class FavoriteHolder extends RecyclerView.ViewHolder {
        private TextView tvViewCount;
        private ImageView imgFavorite;
        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
            imgFavorite = itemView.findViewById(R.id.imgFavorite);
        }
    }

}
