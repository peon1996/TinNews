package com.company.homework9.activity.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.company.homework9.activity.fragment.BaseFragment;
import com.company.homework9.activity.fragment.ItemDetailActivity.ItemDetailFragmentsFactory;

public class ItemDetailPagerAdapter extends FragmentPagerAdapter {
    public static final int TAB_COUNT = 4;
    private Context mContext;
    private Bundle mBundle;

    public ItemDetailPagerAdapter(FragmentManager fm, Context context, Bundle bundle) {
        super(fm);
        mBundle = bundle;
        mContext = context;
    }

    @Override
    public BaseFragment getItem(int position) {
        BaseFragment fragment = ItemDetailFragmentsFactory.createFragment(position, mBundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
