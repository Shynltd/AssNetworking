package com.example.assnetworking;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assnetworking.adapter.ViewPagerAdapter;
import com.example.assnetworking.model.Photo;

import java.util.List;
import java.util.PriorityQueue;

public class ImageFragment extends Fragment {
    private List<Photo> photoList;
    private int position;

    public ImageFragment(List<Photo> photoList, int position) {
        this.photoList = photoList;
        this.position = position;
    }

    public ImageFragment(List<Photo> photoList) {
        this.photoList = photoList;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        ViewPager viewPager = view.findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(photoList, getContext());


        Log.e("position", String.valueOf(position));
        viewPager.setCurrentItem(position);
        viewPager.setAdapter(viewPagerAdapter);
        return view;
    }
}