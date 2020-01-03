package com.studio.yami.moviecataloguefinal.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DataBindingAdapter {

    @BindingAdapter("poster")
    public static void loadImageMedium(ImageView view, String url) {
        String imgUrl = "https://image.tmdb.org/t/p/w185/" + url;
        Glide.with(view.getContext())
                .load(imgUrl)
                .into(view);
    }

    @BindingAdapter("backdrop")
    public static void loadImageLarge(ImageView view, String url) {
        String imgUrl = "https://image.tmdb.org/t/p/w500/" + url;
        Glide.with(view.getContext())
                .load(imgUrl)
                .into(view);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
    }

    @BindingAdapter("srcDrawable")
    public static void loadDrawable(TextView view, int drawable) {
        Drawable draw = ContextCompat.getDrawable(view.getContext(), drawable);
        view.setBackground(draw);
    }

}
