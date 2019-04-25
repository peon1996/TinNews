package com.company.homework9.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.company.homework9.Item;
import com.company.homework9.ObservableBoolean;
import com.company.homework9.R;
import com.company.homework9.activity.adapter.ListDisplayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ListDisplayActivity extends AppCompatActivity implements ListDisplayAdapter.ItemClickListener{
    private LinearLayout progressBar;
    private LinearLayout showList;
    private String keyword;
    private String url;
    private TextView listNumber;
    private TextView inputKeyword;
    private RecyclerView mRecyclerView;
    private ListDisplayAdapter adapter;
    private TextView noResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_display2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noResult = findViewById(R.id.search_no_result);
        progressBar = findViewById(R.id.loading);
        showList = findViewById(R.id.show_list);
        progressBar.setVisibility(View.VISIBLE);
        listNumber = findViewById(R.id.item_number);
        inputKeyword = findViewById(R.id.item_name);

        keyword = (String)getIntent().getExtras().getSerializable("keyword");
        url = (String)getIntent().getExtras().getSerializable("url");
        Log.d("requesturl", url);
        sendHttpRequest();
    }

    private void sendHttpRequest() {
        Thread httpRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient myClient = new OkHttpClient();
                Request req = new Request.Builder().get().url(url).build();
                Response res = null;
                String data;

                try {
                    res = myClient.newCall(req).execute();
                    data = res.body().string();
                    Log.d("responseData", data);
                    List mylist = parseResponse(data);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadResults(mylist);
                        }
                    });

                }catch (IOException e) {
                    Log.e("httpIOException", e.toString());
                }catch (JSONException e) {
                    Log.e("JSONException", e.toString());
                }
            }
        });
        httpRequest.start();
    }

    @Override
    public void onItemClick(View view, int position, String id, String title, Item item) {
        Intent intent = new Intent(ListDisplayActivity.this, ItemDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("item", item);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadResults(List list) {
        progressBar.setVisibility(View.GONE);
        if(list.size() == 0) {
            noResult.setVisibility(View.VISIBLE);
            showList.setVisibility(View.GONE);
        } else {
            noResult.setVisibility(View.GONE);
            showList.setVisibility(View.VISIBLE);
            listNumber.setText(list.size() + "");
            inputKeyword.setText(keyword);

            mRecyclerView = findViewById(R.id.recycler_view);
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            adapter = new ListDisplayAdapter(this, list);
            adapter.setMclickListener(this);
            mRecyclerView.setAdapter(adapter);
        }

    }

    private List<Item> parseResponse(String response) throws JSONException {
        JSONObject responseJSON = new JSONObject(response);
        List<Item> result = new ArrayList<>();
        JSONObject js = responseJSON.getJSONArray("findItemsAdvancedResponse")
                .getJSONObject(0);
        if(js.has("searchResult")) {
            JSONObject res = js.getJSONArray("searchResult").getJSONObject(0);
            if(res.has("item")) {
                JSONArray itemList = res.getJSONArray("item");
                for(int i = 0; i < itemList.length(); i++) {
                    JSONObject current = itemList.getJSONObject(i);
                    String imageUrl;
                    if(current.has("galleryURL")) {
                        imageUrl = current.getJSONArray("galleryURL").get(0).toString();
                    } else {
                        imageUrl = "";
                    }
                    String title;
                    if(current.has("title")) {
                        title = current.getJSONArray("title").get(0).toString();
                    } else {
                        title = "N/A";
                    }
                    String zipCode;
                    if(current.has("postalCode")) {
                        zipCode = current.getJSONArray("postalCode").get(0).toString();
                    } else {
                        zipCode = "N/A";
                    }
                    String cost;
                    cost = current.getJSONArray("sellingStatus").getJSONObject(0).getJSONArray("currentPrice")
                            .getJSONObject(0).getString("__value__");
                    String shippingCost;
                    shippingCost = current.getJSONArray("shippingInfo").getJSONObject(0).
                            getJSONArray("shippingServiceCost").getJSONObject(0).getString("__value__");
                    String condition;
                    if(current.has("condition")) {
                        if(current.getJSONArray("condition").getJSONObject(0).has("conditionDisplayName")) {
                            condition = current.getJSONArray("condition").getJSONObject(0).getJSONArray("conditionDisplayName").get(0).toString();
                        } else {
                            condition = "N/A";
                        }
                    } else {
                        condition = "N/A";
                    }
                    String id;
                    id = current.getJSONArray("itemId").get(0).toString();
                    String see;
                    if(current.has("viewItemURL")) {
                        see = current.getJSONArray("viewItemURL").get(0).toString();
                    } else {
                        see = "N/A";
                    }
                    Item item = new Item(imageUrl, title, zipCode, shippingCost, condition, cost, id, see);
                    result.add(item);
                }
            }
        }
        return result;
    }
}
