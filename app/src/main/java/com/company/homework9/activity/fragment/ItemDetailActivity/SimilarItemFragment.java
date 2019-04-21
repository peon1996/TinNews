package com.company.homework9.activity.fragment.ItemDetailActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.homework9.R;
import com.company.homework9.activity.fragment.BaseFragment;

public class SimilarItemFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_similar_items, container, false);
        String data = getArguments().getString("similar");
        return v;
    }

    @Override
    protected  void loadData() {

    }

    @Override
    protected  View initView() {
        return null;
    }
}
