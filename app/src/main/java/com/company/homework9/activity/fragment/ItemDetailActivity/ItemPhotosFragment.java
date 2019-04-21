package com.company.homework9.activity.fragment.ItemDetailActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.homework9.R;
import com.company.homework9.activity.adapter.DetailPhotoAdapter;
import com.company.homework9.activity.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemPhotosFragment extends BaseFragment {
    private String photoData;
    private RecyclerView recyclerView;
    private List<String> list;
    private TextView noFound;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_photos, container, false);
        photoData = getArguments().getString("photos");
        recyclerView = v.findViewById(R.id.photo_recycler);
        list = new ArrayList<>();
        noFound = v.findViewById(R.id.no_photo);
        try {
            parseJSON(photoData);
        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
        }
        if(list.size() == 0) {
            noFound.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            noFound.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            DetailPhotoAdapter adapter = new DetailPhotoAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
        }


        return v;
    }

    private void parseJSON(String data) throws JSONException {
        JSONObject current = new JSONObject(data);
        if(current.has("items")) {
            JSONArray array = current.getJSONArray("items");
            for(int i = 0; i < array.length(); i++) {
                list.add(array.getJSONObject(i).getString("link"));
            }
        }

    }

    @Override
    protected  void loadData() {

    }

    @Override
    protected  View initView() {
        return null;
    }
}
