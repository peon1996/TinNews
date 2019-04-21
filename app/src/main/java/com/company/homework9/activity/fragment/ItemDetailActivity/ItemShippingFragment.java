package com.company.homework9.activity.fragment.ItemDetailActivity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.company.homework9.Item;
import com.company.homework9.R;
import com.company.homework9.activity.fragment.BaseFragment;
import com.wssholmes.stark.circular_score.CircularScoreView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

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

    private View line1;
    private View line2;

    private TextView tName;
    private LinearLayout lName;
    private TextView tFb;
    private LinearLayout lFb;
    private CircularScoreView tPopular;
    private LinearLayout lPopular;
    private ImageView tStar;
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

    private LinearLayout has;
    private TextView no;

    private boolean p1;
    private boolean p2;
    private boolean p3;

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
        p1 = false;
        p2 = false;
        p3 = false;

        no = v.findViewById(R.id.no_info);
        has = v.findViewById(R.id.has_info);

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

        line1 = v.findViewById(R.id.line1);
        line2 = v.findViewById(R.id.line2);


        String data = getArguments().getString("detail");

        try{
            parseJSON(data);
        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
        }
        if(storeName.equals("") && storeURL.equals("") && fbScore.equals("") && popularity.equals("") && fbStar.equals("")) {
            soldBy.setVisibility(View.GONE);
            line1.setVisibility(View.GONE);
            p1 = false;
        } else {
            p1 = true;
            line2.setVisibility(View.VISIBLE);
            soldBy.setVisibility(View.VISIBLE);
            if(storeName.equals("")) {
                lName.setVisibility(View.GONE);
            } else {
                lName.setVisibility(View.VISIBLE);
                tName.setClickable(true);
                tName.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href=" + storeURL + ">" + storeName + "</a>";
                tName.setText(Html.fromHtml(text));
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
                tPopular.setScore((int)Float.parseFloat(popularity));
            }
            if(fbStar.equals("")) {
                lStar.setVisibility(View.GONE);
            } else {
                lStar.setVisibility(View.VISIBLE);
                if(fbStar.equals("Blue")) {
                    tStar.setImageResource(R.drawable.normalstar);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.blue), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("Green")) {
                    tStar.setImageResource(R.drawable.normalstar);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("GreenShooting")) {
                    tStar.setImageResource(R.drawable.shooting);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.green), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("Purple")) {
                    tStar.setImageResource(R.drawable.normalstar);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.purple), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("PurpleShooting")) {
                    tStar.setImageResource(R.drawable.shooting);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.purple), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("Red")) {
                    tStar.setImageResource(R.drawable.normalstar);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("RedShooting")) {
                    tStar.setImageResource(R.drawable.shooting);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("SilverShooting")) {
                    tStar.setImageResource(R.drawable.shooting);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.silver), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("Turquoise")) {
                    tStar.setImageResource(R.drawable.normalstar);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.turquoise), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("TurquoiseShooting")) {
                    tStar.setImageResource(R.drawable.shooting);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.turquoise), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("Yellow")) {
                    tStar.setImageResource(R.drawable.normalstar);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("YellowShooting")) {
                    tStar.setImageResource(R.drawable.shooting);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.yellow), android.graphics.PorterDuff.Mode.SRC_IN);
                } else if(fbStar.equals("None")) {
                    tStar.setImageResource(R.drawable.normalstar);
                    tStar.setColorFilter(ContextCompat.getColor(getContext(), R.color.secondary_purple_color), android.graphics.PorterDuff.Mode.SRC_IN);
                }
            }

        }

        if(shippingCost.equals("") && globalShipping.equals("") && handlingTime.equals("") && condition.equals("")) {
            shippingInfo.setVisibility(View.GONE);
            p2 = false;
        } else {
            shippingInfo.setVisibility(View.VISIBLE);
            p2 = true;
            if(shippingCost.equals("")) {
                lCost.setVisibility(View.GONE);
            } else {
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

                if(handlingTime.equals("")) {
                    lTime.setVisibility(View.GONE);
                } else {
                    lTime.setVisibility(View.VISIBLE);
                    if(handlingTime.equals("0") || handlingTime.equals("1")) {
                        tTime.setText(handlingTime + "day");
                    } else {
                        tTime.setText(handlingTime + "days");
                    }
                }

                if(condition.equals("")) {
                    lCdt.setVisibility(View.GONE);
                } else {
                    lCdt.setVisibility(View.VISIBLE);
                    tCdt.setText(condition);
                }
            }
        }

        if(policy.equals("") && returnsWithin.equals("") && refundMode.equals("") && shippedBy.equals("")) {
            returnPolicy.setVisibility(View.GONE);
            line2.setVisibility(View.GONE);
            p3 = false;
        } else {
            p3 = true;
            returnPolicy.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            if(policy.equals("")) {
                lPolicy.setVisibility(View.GONE);
            } else {
                lPolicy.setVisibility(View.VISIBLE);
                tPolicy.setText(policy);
            }

            if(returnsWithin.equals("")) {
                lWithin.setVisibility(View.GONE);
            } else {
                lWithin.setVisibility(View.VISIBLE);
                tWithin.setText(returnsWithin);
            }

            if(refundMode.equals("")) {
                lMode.setVisibility(View.GONE);
            } else {
                lMode.setVisibility(View.VISIBLE);
                tMode.setText(refundMode);
            }

            if(shippedBy.equals("")) {
                lBy.setVisibility(View.GONE);
            } else {
                lBy.setVisibility(View.VISIBLE);
                tBy.setText(shippedBy);
            }
        }

        if(!p1 && !p2 && !p3) {
            no.setVisibility(View.VISIBLE);
            has.setVisibility(View.GONE);
        } else {
            no.setVisibility(View.GONE);
            has.setVisibility(View.VISIBLE);
        }


        return v;
    }

    private void setUpLink(TextView view, String url, String content) {
        Pattern pattern = Pattern.compile(url);
        Linkify.addLinks(view, pattern, "http://");
        view.setText(Html.fromHtml("<a href='http://" + url + "'>" + content + "</a>"));

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