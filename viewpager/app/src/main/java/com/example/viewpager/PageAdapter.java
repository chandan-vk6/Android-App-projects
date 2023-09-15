package com.example.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

public class PageAdapter extends PagerAdapter {
    final Context context;

    public PageAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PageModel model = PageModel.values()[position];
        ViewGroup layout = (ViewGroup) LayoutInflater.from(container.getContext()).inflate(model.getLayout(),container,false);
        container.addView(layout);
                return  layout;
    }

    @Override
    public int getCount() {
        return PageModel.values().length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        PageModel model = PageModel.values()[position];
         return context.getString(model.getTitle());
    }
}

