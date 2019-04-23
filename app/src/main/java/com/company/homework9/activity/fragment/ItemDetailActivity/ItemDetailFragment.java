package com.company.homework9.activity.fragment.ItemDetailActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.homework9.Item;
import com.company.homework9.R;
import com.company.homework9.activity.fragment.BaseFragment;
import com.squareup.picasso.Picasso;

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
    private String subtitle;
    private List<String> specs;

    private LinearLayout gallery;
    private TextView mTitle;
    private TextView mPrice;
    private TextView mShipping;
    private LinearLayout lSubtitle;
    private LinearLayout lPrice;
    private LinearLayout lBrand;
    private LinearLayout specPart;
    private TextView tSubtitle;
    private TextView tPrice;
    private TextView tBrand;
    private TextView mSpec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pics = new ArrayList<>();
        specs = new ArrayList<>();
        brand = "";
        subtitle = "";
        View v = inflater.inflate(R.layout.fragment_item_detail, container, false);

        gallery = v.findViewById(R.id.image_gallery);
        mTitle = v.findViewById(R.id.detail_title);
        mPrice = v.findViewById(R.id.detail_cost);
        mShipping = v.findViewById(R.id.detail_shipping);

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

        for(int i = 0; i < pics.size(); i++) {
            View view = inflater.inflate(R.layout.gallery_item, gallery, false);
            ImageView img = view.findViewById(R.id.gallery_image);
            Picasso.get().load(pics.get(i)).into(img);
            gallery.addView(view);
        }

        mTitle.setText(title);
        mPrice.setText("$" + price);
        String shippingToPut;
        if(Float.parseFloat(shipping) == 0.0) {
            shippingToPut = "With Free Shipping";
        } else {
            shippingToPut = "With $" + shipping + " Shipping";
        }
        mShipping.setText(shippingToPut);

        lSubtitle = v.findViewById(R.id.detail_subtitle);
        tSubtitle = v.findViewById(R.id.subtitle_text);
        lPrice = v.findViewById(R.id.detail_price);
        tPrice = v.findViewById(R.id.price_text);
        lBrand = v.findViewById(R.id.detail_brand);
        tBrand = v.findViewById(R.id.brand_text);

        if(subtitle.equals("")) {
            lSubtitle.setVisibility(View.GONE);
        } else {
            lSubtitle.setVisibility(View.VISIBLE);
            tSubtitle.setText(subtitle);
        }
        tPrice.setText("$" + price);
        if(brand.equals("")) {
            lBrand.setVisibility(View.GONE);
        } else {
            lBrand.setVisibility(View.VISIBLE);
            tBrand.setText(brand);
        }
        specPart = v.findViewById(R.id.spec_part);
        if(specs.size() == 0) {
            specPart.setVisibility(View.GONE);
        } else {
            specPart.setVisibility(View.VISIBLE);
            mSpec = v.findViewById(R.id.detail_spec);
            String specToput = "";
            for(int i = 0; i < specs.size(); i++) {
                specToput += "• " + specs.get(i).substring(0, 1).toUpperCase() + specs.get(i).substring(1) + "\n";
            }
            mSpec.setText(specToput);
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
        subtitle = current.getString("Subtitle");

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

