package com.company.homework9.activity.fragment.ItemDetailActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.homework9.Item;
import com.company.homework9.R;
import com.company.homework9.activity.fragment.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class ItemShippingFragment extends BaseFragment {
    private String storeName;
    private String storeURL;
    private String fbScore;
    private String popularity;
    private String fbStar;

    private String shippingCost;
    private String globalShipping;
    private String handlingTime;
    private String condition;

    private String policy;
    private String returnsWithin;
    private String refundMode;
    private String shippedBy;

    private LinearLayout soldBy;
    private LinearLayout shippingInfo;
    private LinearLayout returnPolicy;

    private TextView tName;
    private LinearLayout lName;
    private TextView tFb;
    private LinearLayout lFb;
    private TextView tPopular;
    private LinearLayout lPopular;
    private TextView tStar;
    private LinearLayout lStar;
    private TextView tCost;
    private LinearLayout lCost;
    private TextView tGlobal;
    private LinearLayout lGlobal;
    private TextView tTime;
    private LinearLayout lTime;
    private TextView tCdt;
    private LinearLayout lCdt;
    private TextView tPolicy;
    private LinearLayout lPolicy;
    private TextView tWithin;
    private LinearLayout lWithin;
    private TextView tMode;
    private LinearLayout lMode;
    private TextView tBy;
    private LinearLayout lBy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_shipping, container, false);
        storeName = "";
        storeURL = "";
        fbScore = "";
        popularity = "";
        fbStar = "";
        Item item = (Item)getArguments().getSerializable("item");
        shippingCost = item.getShippingCost();
        globalShipping = "";
        handlingTime = "";
        condition = "";
        policy = "";
        returnsWithin = "";
        refundMode = "";
        shippedBy = "";

        soldBy = v.findViewById(R.id.sold_by);
        shippingInfo = v.findViewById(R.id.shipping_info);
        returnPolicy = v.findViewById(R.id.return_policy);

        lName = v.findViewById(R.id.detail_store_name);
        tName = v.findViewById(R.id.store_name_text);
        lFb = v.findViewById(R.id.detail_fb);
        tFb = v.findViewById(R.id.fb_text);
        lPopular = v.findViewById(R.id.detail_popular);
        tPopular = v.findViewById(R.id.popular_text);
        lStar = v.findViewById(R.id.detail_star);
        tStar = v.findViewById(R.id.star_text);
        lCost = v.findViewById(R.id.detail_shipping_cost);
        tCost = v.findViewById(R.id.shipping_cost_text);
        lGlobal = v.findViewById(R.id.detail_global);
        tGlobal = v.findViewById(R.id.global_text);
        lTime = v.findViewById(R.id.detail_time);
        tTime = v.findViewById(R.id.time_text);
        lCdt = v.findViewById(R.id.detail_cdt);
        tCdt = v.findViewById(R.id.cdt_text);
        lPolicy = v.findViewById(R.id.detail_policy);
        tPolicy = v.findViewById(R.id.policy_text);
        lWithin = v.findViewById(R.id.detail_within);
        tWithin = v.findViewById(R.id.within_text);
        lMode = v.findViewById(R.id.detail_mode);
        tMode = v.findViewById(R.id.mode_text);
        lBy = v.findViewById(R.id.detail_by);
        tBy = v.findViewById(R.id.by_text);


        String data = getArguments().getString("detail");

        try{
            parseJSON(data);
        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
        }
        if(storeName.equals("") && storeURL.equals("") && fbScore.equals("") && popularity.equals("") && fbStar.equals("")) {
            soldBy.setVisibility(View.GONE);
        } else {
            soldBy.setVisibility(View.VISIBLE);
            if(storeName.equals("")) {
                lName.setVisibility(View.GONE);
            } else {
                lName.setVisibility(View.VISIBLE);
                tName.setText(storeName);
            }
            if(fbScore.equals("")) {
                lFb.setVisibility(View.GONE);
            } else {
                lFb.setVisibility(View.VISIBLE);
                tFb.setText(fbScore);
            }
            if(popularity.equals("")) {
                lPopular.setVisibility(View.GONE);
            } else {
                lPopular.setVisibility(View.VISIBLE);
                tPopular.setText(popularity);
            }
            if(fbStar.equals("")) {
                lStar.setVisibility(View.GONE);
            } else {
                lStar.setVisibility(View.VISIBLE);
                tStar.setText(fbStar);
            }

        }

        shippingInfo.setVisibility(View.VISIBLE);
        lCost.setVisibility(View.VISIBLE);
        if(Float.parseFloat(shippingCost) == 0.0) {
            tCost.setText("Free Shipping");
        } else {
            tCost.setText("$" + shippingCost);
        }
        if(globalShipping.equals("")) {
            lGlobal.setVisibility(View.GONE);
        } else {
            lGlobal.setVisibility(View.VISIBLE);
            tGlobal.setText(globalShipping.equals("true") ? "Yes" : "No");
        }



        return v;
    }

    private void parseJSON(String data) throws JSONException {
        JSONObject object = new JSONObject(data);
        JSONObject current = object.getJSONObject("Item");
        if(current.has("Storefront")) {
            storeName = current.getJSONObject("Storefront").getString("StoreName");
            storeURL = current.getJSONObject("Storefront").getString("StoreURL");
        }
        if(current.has("Seller")) {
            fbScore = current.getJSONObject("Seller").getString("FeedbackScore");
            popularity = current.getJSONObject("Seller").getString("PositiveFeedbackPercent");
            fbStar = current.getJSONObject("Seller").getString("FeedbackRatingStar");
        }
        globalShipping = current.getString("GlobalShipping");
        handlingTime = current.getString("HandlingTime");
        condition = current.getString("ConditionDescription");

        if(current.has("ReturnPolicy")) {
            JSONObject obj = current.getJSONObject("ReturnPolicy");
            policy = obj.getString("ReturnsAccepted");
            returnsWithin = obj.getString("ReturnsWithin");
            refundMode = obj.getString("Refund");
            shippedBy = obj.getString("ShippingCostPaidBy");
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