package com.example.currentnews.ui.headlines;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.currentnews.ui.news.NewsFragment;
import com.example.currentnews.network.NewsApi;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final NewsFragment[] newsFragments;


    public ViewPagerAdapter(FragmentManager fm, String[] categories) {
        super(fm);
        newsFragments = new NewsFragment[categories.length];
        for (int i = 0; i < categories.length; i++) {
            newsFragments[i] = NewsFragment.newInstance(NewsApi.Category.valueOf(categories[i]));
        }
    }

    @Override
    public Fragment getItem(int i) {
        return newsFragments[i];
    }

    @Override
    public int getCount() {
        return newsFragments == null ? 0 : newsFragments.length;
    }
}