package com.company.homework9.activity.fragment.MainActivity;

import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.company.homework9.activity.fragment.BaseFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    public static final int FRAGMENT_NUMBER = 2;
    public static final String[] MAIN_TITLES = {"SEARCH", "WISH LIST"};
    public String[] title;

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        title = MAIN_TITLES;
    }
    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        SpannableString spannableString = new SpannableString(" "+title[position]);
        return spannableString;
    }

    @Override
    public BaseFragment getItem(int position) {
        BaseFragment fragment = MainFragmentFactory.createFragments(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return FRAGMENT_NUMBER;
    }
}
