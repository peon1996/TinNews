package com.company.homework9.activity.fragment.ItemDetailActivity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.homework9.R;
import com.company.homework9.activity.fragment.BaseFragment;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ItemPhotosFragment extends BaseFragment {
    private String photoData;
    private RecyclerView recyclerView;
    private List<String> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_photos, container, false);
        photoData = getArguments().getString("photos");
        recyclerView = v.findViewById(R.id.photo_recycler);
        list = new ArrayList<>();
        return v;
    }

    private void parseJSON(String data) throws JSONException {

    }

    @Override
    protected  void loadData() {

    }

    @Override
    protected  View initView() {
        return null;
    }
}
