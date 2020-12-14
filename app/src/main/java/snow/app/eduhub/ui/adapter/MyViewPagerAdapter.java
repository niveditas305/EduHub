package snow.app.eduhub.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

import snow.app.eduhub.R;
import snow.app.eduhub.ui.network.responses.homedatares.Banner;
import snow.app.eduhub.util.AppUtils;


public class MyViewPagerAdapter extends PagerAdapter {

    ImageView imageView;
    private Context context;
    List<Banner> bannersList;

    public MyViewPagerAdapter(Context context, List<Banner> bannersList) {
        this.context = context;
        this.bannersList = bannersList;
    }

    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.welcome_pager_row, collection, false);


        imageView = layout.findViewById(R.id.iv_bg);

     /*   Glide.with(context).load(R.drawable.ic_background_login)
                .into(imageView);*/

        AppUtils.ImageWithGlide(imageView, bannersList.get(position).getBannerImage());
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public int getCount() {
        return bannersList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}