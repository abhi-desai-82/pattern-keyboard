package com.patternkeyboard.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SliderPageAdapter extends PagerAdapter {

    private int[] sliderLayoutIds;
    private Context context;

    public SliderPageAdapter(Context context, int[] sliderLayoutIds) {
        this.context = context;
        this.sliderLayoutIds = sliderLayoutIds;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(sliderLayoutIds[position], container, false);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return sliderLayoutIds.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
