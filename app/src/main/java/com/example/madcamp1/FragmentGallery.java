package com.example.madcamp1;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.util.List;

public class FragmentGallery extends Fragment {

    View v;
    Gallery simpleGallery;
    private ImageView selectedImageView;

    // To show the selected language, we need this
    // array of images, here taken 10 different kind of most popular programming languages
    private int[] images = {R.drawable.picture1, R.drawable.picture2, R.drawable.photo3,R.drawable.photo4, R.drawable.photo5};

    public FragmentGallery() {
    }

    PhotoViewAttacher mAttacher;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery, container, false);

        simpleGallery = v.findViewById(R.id.languagesGallery);
        selectedImageView = v.findViewById(R.id.img);

        // initialize the adapter
        CustomizedGalleryAdapter customGalleryAdapter = new CustomizedGalleryAdapter(getContext(), images);

        // set the adapter for gallery
        simpleGallery.setAdapter(customGalleryAdapter);

        simpleGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Whichever image is clicked, that is set in the  selectedImageView
                // position will indicate the location of image
                selectedImageView.setImageResource(images[position]);
            }
        });




        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public class CustomViewPager extends ViewPager {

        public CustomViewPager(Context context) {
            super(context);
        }

        public CustomViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            try {
                return super.onTouchEvent(ev);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }




}

