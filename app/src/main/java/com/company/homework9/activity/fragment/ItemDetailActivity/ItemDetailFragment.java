package com.company.homework9.activity.fragment.ItemDetailActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.company.homework9.Item;
import com.company.homework9.R;
import com.company.homework9.activity.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailFragment extends BaseFragment {
    private List<String> pics;
    private String title;
    private String price;
    private String shipping;
    private String brand;
    private List<String> specs;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pics = new ArrayList<>();
        specs = new ArrayList<>();
        brand = "";
        View v = inflater.inflate(R.layout.fragment_item_detail, container, false);
        String data = getArguments().getString("detail");
        Item item = (Item)getArguments().getSerializable("item");
        title = item.getTitle();
        price = item.getCost();
        shipping = item.getShippingCost();
        try {
            parseJson(data);
        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
        }

        return v;
    }

    private void parseJson(String data) throws JSONException {
        JSONObject object = new JSONObject(data);
        JSONObject current = object.getJSONObject("Item");
        JSONArray pictures = current.getJSONArray("PictureURL");
        for(int i = 0; i < pictures.length(); i++) {
            pics.add(pictures.get(i).toString());
        }
        JSONArray array = null;
        if(current.has("ItemSpecifics")) {
            array = current.getJSONObject("ItemSpecifics").getJSONArray("NameValueList");
        }
        if(array != null) {
            for(int i = 0; i < array.length(); i++) {
                specs.add(array.getJSONObject(i).getJSONArray("Value").get(0).toString());
                if(array.getJSONObject(i).getString("Name").equals("Brand")) {
                    brand = array.getJSONObject(i).getJSONArray("Value").get(0).toString();
                }
            }
        }
    }

    private void test() {
        String test = "testmxy";
        for(int i = 0; i < pics.size(); i++) {
            Log.d(test, pics.get(i));
        }
        Log.d(test, price);
        Log.d(test, title);
        Log.d(test, shipping);
        Log.d(test, brand);
        for(int i = 0; i < specs.size(); i++) {
            Log.d(test, specs.get(i));
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
