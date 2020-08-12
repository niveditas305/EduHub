package com.example.eduhub.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.eduhub.R;


public class MyViewPagerAdapter extends PagerAdapter {

    ImageView imageView;
    private Context context;
    int[] image_resources;
    public MyViewPagerAdapter(Context context, int[] image_resources) {
        this.context = context;
        this.image_resources=image_resources;


    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
         ViewGroup layout = (ViewGroup) inflater.inflate( R.layout.welcome_pager_row, collection, false);


imageView=layout.findViewById( R.id.iv_bg );

     /*   Glide.with(context).load(R.drawable.ic_background_login)
                .into(imageView);*/

        imageView.setImageResource(image_resources[position]);
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}