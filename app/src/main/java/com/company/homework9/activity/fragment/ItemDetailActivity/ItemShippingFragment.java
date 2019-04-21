package com.company.homework9.activity.fragment.ItemDetailActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        String data = getArguments().getString("detail");

        try{
            parseJSON(data);
        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
        }
        String test2 = "mxy";
        Log.d(test2, storeName);
        Log.d(test2, storeURL.equals("") ? "yes" : "false");
        Log.d(test2, fbScore + "\n" + popularity +  "\n" + fbStar +  "\n" + globalShipping +  "\n" + handlingTime +  "\n" + condition +  "\n" + policy +  "\n" + returnsWithin +  "\n" + refundMode +  "\n" + shippedBy);


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