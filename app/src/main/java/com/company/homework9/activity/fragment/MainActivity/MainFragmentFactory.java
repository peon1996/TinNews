package com.company.homework9.activity.fragment.MainActivity;

import com.company.homework9.activity.fragment.BaseFragment;

import java.util.HashMap;

public class MainFragmentFactory {
    private static HashMap<Integer, BaseFragment> mainFragments = new HashMap<>();

    public static BaseFragment createFragments(int position) {
        BaseFragment currentFragment = mainFragments.get(position);
        if(currentFragment == null) {
            switch(position) {
                case 0:
                    currentFragment = new SearchFragment();
                    break;
                case 1:
                    currentFragment = new WishListFragment();
                    break;
            }
            mainFragments.put(position, currentFragment);
        }
        return currentFragment;
    }
}
