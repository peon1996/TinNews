package com.company.homework9.activity.fragment.ItemDetailActivity;

import android.os.Bundle;

import com.company.homework9.activity.fragment.BaseFragment;

import java.util.HashMap;

public class ItemDetailFragmentsFactory {
    private static HashMap<Integer, BaseFragment> detailFragments = new HashMap<>();

    public static BaseFragment createFragment(int position, Bundle bundle) {
        BaseFragment fragment = null;
            switch(position) {
                case 0:
                    fragment = new ItemDetailFragment();
                    fragment.setArguments(bundle);
                    break;
                case 1:
                    fragment = new ItemShippingFragment();
                    fragment.setArguments(bundle);
                    break;
                case 2:
                    fragment = new ItemPhotosFragment();
                    fragment.setArguments(bundle);
                    break;
                case 3:
                    fragment = new SimilarItemFragment();
                    fragment.setArguments(bundle);
                    break;
            }

        return fragment;
    }
}