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
import com.example.assnetworking.model.Galleries;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {
    private Context context;
    private List<Galleries> galleryList;

    public GalleryAdapter(Context context, List<Galleries> galleryList) {
        this.context = context;
        this.galleryList = galleryList;
    }

    @NonNull
    @Override
    public GalleryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_list_galleries, parent, false);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryHolder holder, int position) {
        holder.tvNumberImage.setText(galleryList.get(position).getCountPhotos()+"");
        holder.tvViewCount.setText(galleryList.get(position).getCountViews()+"");
        Glide.with(context).load(galleryList.get(position).getUrl()).into(holder.imgCover);



    }

    @Override
    public int getItemCount() {
        if (galleryList == null) return 0;
        return galleryList.size();
    }

    public static class GalleryHolder extends RecyclerView.ViewHolder {
        private TextView tvNumberImage, tvViewCount;
        private ImageView imgCover;
        public GalleryHolder(@NonNull View itemView) {
            super(itemView);
            tvNumberImage = itemView.findViewById(R.id.tvNumberImage);
            tvViewCount = itemView.findViewById(R.id.tvViewCount);
            imgCover = itemView.findViewById(R.id.imgCover);
        }
    }

}
