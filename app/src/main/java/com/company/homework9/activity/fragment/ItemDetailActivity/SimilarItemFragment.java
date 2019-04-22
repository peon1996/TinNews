package com.company.homework9.activity.fragment.ItemDetailActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.company.homework9.R;
import com.company.homework9.SimilarItem;
import com.company.homework9.activity.adapter.SimilarItemAdapter;
import com.company.homework9.activity.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimilarItemFragment extends BaseFragment implements SimilarItemAdapter.ItemClickListener{
    private List<SimilarItem> list;
    private Spinner spinnerPrice;
    private Spinner spinnerOrder;
    private List<SimilarItem> defaultList;
    private RecyclerView recyclerView;
    private TextView noSimilar;
    private SimilarItemAdapter adapter;
    private String curChoice;
    private boolean isAscending;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_similar_items, container, false);

        spinnerPrice = v.findViewById(R.id.spinner_price);
        spinnerOrder = v.findViewById(R.id.spinner_order);
        recyclerView = v.findViewById(R.id.similar_recycler);
        noSimilar = v.findViewById(R.id.no_similar);
        list = new ArrayList<>();
        defaultList = new ArrayList<>();

        String[] value1 = {"Default", "Name", "Price", "Days"};
        String[] value2 = {"Ascending", "Descending"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, value1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, value2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrice.setAdapter(adapter1);
        spinnerOrder.setAdapter(adapter2);
        String data = getArguments().getString("similar");
        try {
            parseJSON(data);
        } catch (JSONException e) {
            Log.e("JSONException", e.toString());
        }
        for(int i = 0; i < list.size(); i++) {
            defaultList.add(list.get(i));
        }

        if(list.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            noSimilar.setVisibility(View.VISIBLE);
            spinnerPrice.setEnabled(false);
            spinnerOrder.setEnabled(false);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noSimilar.setVisibility(View.VISIBLE);
            spinnerOrder.setEnabled(false);
            spinnerPrice.setEnabled(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new SimilarItemAdapter(getActivity(), list);
            adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);
            isAscending = true;
            curChoice = "Default";

            spinnerPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            list = new ArrayList<>(defaultList);
                            adapter = new SimilarItemAdapter(getActivity(), list);
                            recyclerView.setAdapter(adapter);
                            spinnerOrder.setSelection(0);
                            spinnerOrder.setEnabled(false);
                            curChoice = "Default";
                            break;
                        case 1:
                            Collections.sort(list, new Comparator<SimilarItem>() {
                                @Override
                                public int compare(SimilarItem o1, SimilarItem o2) {
                                    return isAscending ? o1.getTitle().compareTo(o2.getTitle()) : o2.getTitle().compareTo(o1.getTitle());
                                }
                            });
                            adapter = new SimilarItemAdapter(getActivity(), list);
                            recyclerView.setAdapter(adapter);
                            curChoice = "Name";
                            spinnerOrder.setEnabled(true);
                            break;
                        case 2:
                            Collections.sort(list, new Comparator<SimilarItem>() {
                                @Override
                                public int compare(SimilarItem o1, SimilarItem o2) {
                                    return isAscending ? (int)(Float.parseFloat(o1.getPrice()) - Float.parseFloat(o2.getPrice())) : (int)(Float.parseFloat(o2.getPrice()) - Float.parseFloat(o1.getPrice()));
                                }
                            });
                            adapter = new SimilarItemAdapter(getActivity(), list);
                            recyclerView.setAdapter(adapter);
                            spinnerOrder.setEnabled(true);
                            curChoice = "Price";
                            break;
                        case 3:
                            Collections.sort(list, new Comparator<SimilarItem>() {
                                @Override
                                public int compare(SimilarItem o1, SimilarItem o2) {
                                    String str1 = o1.getDays().substring(o1.getDays().indexOf("P") + 1, o1.getDays().indexOf("D"));
                                    String str2 = o2.getDays().substring(o2.getDays().indexOf("P") + 1, o2.getDays().indexOf("D"));
                                    return isAscending ? Integer.parseInt(str1) - Integer.parseInt(str2) : Integer.parseInt(str2) - Integer.parseInt(str1);
                                }
                            });
                            adapter = new SimilarItemAdapter(getActivity(), list);
                            recyclerView.setAdapter(adapter);
                            spinnerOrder.setEnabled(true);
                            curChoice = "Days";
                            break;
                    }
                }
            });

            spinnerOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            if(curChoice.equals("Name")) {
                                Collections.sort(list, new Comparator<SimilarItem>() {
                                    @Override
                                    public int compare(SimilarItem o1, SimilarItem o2) {
                                        return o1.getTitle().compareTo(o2.getTitle());
                                    }
                                });
                            } else if(curChoice.equals("Price")) {
                                Collections.sort(list, new Comparator<SimilarItem>() {
                                    @Override
                                    public int compare(SimilarItem o1, SimilarItem o2) {
                                        return (int)(Float.parseFloat(o1.getPrice()) - Float.parseFloat(o2.getPrice()));
                                    }
                                });
                            } else if(curChoice.equals("Days")) {
                                Collections.sort(list, new Comparator<SimilarItem>() {
                                    @Override
                                    public int compare(SimilarItem o1, SimilarItem o2) {
                                        String str1 = o1.getDays().substring(o1.getDays().indexOf("P") + 1, o1.getDays().indexOf("D"));
                                        String str2 = o2.getDays().substring(o2.getDays().indexOf("P") + 1, o2.getDays().indexOf("D"));
                                        return Integer.parseInt(str1) - Integer.parseInt(str2);
                                    }
                                });
                            } else {

                            }
                            adapter = new SimilarItemAdapter(getActivity(), list);
                            recyclerView.setAdapter(adapter);
                            isAscending = true;
                            break;
                        case 1:
                            if(curChoice.equals("Name")) {
                                Collections.sort(list, new Comparator<SimilarItem>() {
                                    @Override
                                    public int compare(SimilarItem o1, SimilarItem o2) {
                                        return o2.getTitle().compareTo(o1.getTitle());
                                    }
                                });
                            } else if(curChoice.equals("Price")) {
                                Collections.sort(list, new Comparator<SimilarItem>() {
                                    @Override
                                    public int compare(SimilarItem o1, SimilarItem o2) {
                                        return (int)(Float.parseFloat(o2.getPrice()) - Float.parseFloat(o1.getPrice()));
                                    }
                                });
                            } else if(curChoice.equals("Days")) {
                                Collections.sort(list, new Comparator<SimilarItem>() {
                                    @Override
                                    public int compare(SimilarItem o1, SimilarItem o2) {
                                        String str1 = o1.getDays().substring(o1.getDays().indexOf("P") + 1, o1.getDays().indexOf("D"));
                                        String str2 = o2.getDays().substring(o2.getDays().indexOf("P") + 1, o2.getDays().indexOf("D"));
                                        return Integer.parseInt(str2) - Integer.parseInt(str1);
                                    }
                                });
                            } else {

                            }
                            adapter = new SimilarItemAdapter(getActivity(), list);
                            recyclerView.setAdapter(adapter);
                            isAscending = false;
                            break;
                    }
                }
            });











        }




        return v;
    }

    private void parseJSON(String data) throws JSONException {
        JSONObject json = new JSONObject(data);
        JSONObject response = json.getJSONObject("getSimilarItemsResponse");
        if(response.has("itemRecommendations")) {
            JSONObject obj = response.getJSONObject("itemRecommendations");
            if(obj.has("item")) {
                JSONArray arr = obj.getJSONArray("item");
                for(int i = 0; i < arr.length(); i++) {
                    JSONObject current = arr.getJSONObject(i);
                    String title;
                    if(current.has("title")) {
                        title = current.getString("title");
                    } else {
                        title = "N/A";
                    }

                    String webURL;
                    if(current.has("viewItemURL")) {
                        webURL = current.getString("viewItemURL");
                    } else {
                        webURL = "";
                    }

                    String imageURL;
                    if(current.has("imageURL")) {
                        imageURL = current.getString("imageURL");
                    } else {
                        imageURL = "";
                    }

                    String days;
                    if(current.has("timeLeft")) {
                        days = current.getString("timeLeft");
                    } else {
                        days = "";
                    }
                    String price;
                    if(current.has("buyItNowPrice")) {
                        price = current.getJSONObject("buyItNowPrice").getString("__value__");
                    } else {
                        price = "";
                    }
                    String shipping;
                    if(current.has("shippingCost")) {
                        shipping = current.getJSONObject("shippingCost").getString("__value__");
                    } else {
                        shipping = "";
                    }

                    SimilarItem item = new SimilarItem(imageURL, webURL, title, shipping, days, price);
                    list.add(item);

                }
            }
        }
    }

    @Override
    public void onItemClick(View view, int position, String url) {
        Uri uri = Uri.parse(list.get(position).getWebURL());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    protected  void loadData() {

    }

    @Override
    protected  View initView() {
        return null;
    }
}
