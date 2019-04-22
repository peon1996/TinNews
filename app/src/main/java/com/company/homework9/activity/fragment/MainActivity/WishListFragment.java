package com.company.homework9.activity.fragment.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.homework9.Item;
import com.company.homework9.R;
import com.company.homework9.activity.ItemDetailActivity;
import com.company.homework9.activity.ListDisplayActivity;
import com.company.homework9.activity.adapter.WishListAdapter;
import com.company.homework9.activity.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WishListFragment extends BaseFragment implements WishListAdapter.ItemClickListener{
    public static final Map<String, Item> wishListMap = new HashMap<>();
    private WishListAdapter adapter;
    private RecyclerView recyclerView;
    private TextView itemNumber;
    private TextView itemValue;
    private TextView noItem;
    @Override
    protected void loadData() {

    }

    @Override
    protected View initView() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wish_list, container, false);
        recyclerView = v.findViewById(R.id.wish_list_recycler_view);
        itemNumber = v.findViewById(R.id.wish_list_number);
        itemValue = v.findViewById(R.id.wish_list_cost);
        noItem = v.findViewById(R.id.no_item);
        List<Item> list = new ArrayList<>(wishListMap.values());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new WishListAdapter(getActivity(), list);
        adapter.setMclickListener(this);
        recyclerView.setAdapter(adapter);

        String number = "WishList total(" + list.size() + " items):";
        itemNumber.setText(number);

        float cost = 0;
        for(int i = 0; i < list.size(); i++) {
            cost += Float.parseFloat(list.get(i).getCost());
        }
        itemValue.setText("$" + cost);
        if(list.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            noItem.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noItem.setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public void onItemClick(View v, int position, String id, String title, Item item) {
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}
