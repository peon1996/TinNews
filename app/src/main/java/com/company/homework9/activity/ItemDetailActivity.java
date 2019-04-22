package com.company.homework9.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.homework9.Item;
import com.company.homework9.R;
import com.company.homework9.activity.adapter.DetailPhotoAdapter;
import com.company.homework9.activity.adapter.ItemDetailPagerAdapter;
import com.company.homework9.activity.fragment.MainActivity.WishListFragment;


import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ItemDetailActivity extends AppCompatActivity{
    private TabLayout tabs;
    private ViewPager viewPager;
    private LinearLayout progressBar;
    String jsonData;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        jsonData = "";
        tabs = findViewById(R.id.item_detail_tab);
        viewPager = findViewById(R.id.item_detail_viewpager);
        progressBar = findViewById(R.id.item_detail_loading);
        title = (String)this.getIntent().getExtras().getSerializable("title");
        Item current = (Item)this.getIntent().getExtras().getSerializable("item");
        setTitle(title);

        String toastName;
        if(title.length() >= 60) {
            toastName = title.substring(0, 60) + "...";
        } else {
            toastName = title;
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if(!WishListFragment.wishListMap.containsKey(current.getId())) {
            fab.setImageResource(R.drawable.cart_plus);
        } else {
            fab.setImageResource(R.drawable.cart_remove);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(WishListFragment.wishListMap.containsKey(current.getId())) {
                    WishListFragment.wishListMap.remove(current.getId());
                    fab.setImageResource(R.drawable.cart_plus);
                    Toast.makeText(getApplicationContext(), toastName + " was removed from wish list",Toast.LENGTH_SHORT).show();
                    MainActivity.getViewPager().getAdapter().notifyDataSetChanged();
                } else {
                    WishListFragment.wishListMap.put(current.getId(), current);
                    fab.setImageResource(R.drawable.cart_remove);
                    Toast.makeText(getApplicationContext(), toastName +" was added to wish list",Toast.LENGTH_SHORT).show();
                    MainActivity.getViewPager().getAdapter().notifyDataSetChanged();
                }
            }
        });
        setUpTabs();


        sendHttpRequest();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.facebook:
                String targetUrl = "https://www.facebook.com/dialog/share?app_id=1134804663373816&display=popup&href=";
                Item current = (Item)this.getIntent().getExtras().getSerializable("item");
                String title = current.getTitle();
                String see = current.getSee();
                String cost = current.getCost();
                String quote = "Buy " + title + " at " +  cost +  " from LINK below";
                targetUrl += see;
                targetUrl += "&redirect_uri=";
                targetUrl += see;
                targetUrl += "&quote=";
                targetUrl += quote;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(targetUrl));
                startActivity(i);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void sendHttpRequest() {
        Thread httpRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://searchproducts.us-east-2.elasticbeanstalk.com/getDetail?id=";
                    String id = (String)getIntent().getExtras().getSerializable("id");
                    url += id;
                    Log.d("showurl", url);
                    OkHttpClient myClient = new OkHttpClient();
                    Request req = new Request.Builder().get().url(url).build();
                    Response res = null;
                    res = myClient.newCall(req).execute();
                    jsonData = res.body().string();
                    Log.d("seedetail", jsonData);

                    url = "http://searchproducts.us-east-2.elasticbeanstalk.com/getSimilar?id=";
                    url += id;
                    Request req2 = new Request.Builder().get().url(url).build();
                    Response res2 = null;
                    res2 = myClient.newCall(req2).execute();
                    String similarData = res2.body().string();
                    Log.d("seesimilar", url);

                    url = "http://searchproducts.us-east-2.elasticbeanstalk.com/getPhoto?id=";
                    url += title;
                    Request req3 = new Request.Builder().get().url(url).build();
                    Response res3 = null;
                    res3 = myClient.newCall(req3).execute();
                    String photos = res3.body().string();
                    Log.d("seephoto", photos);



                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);
                            initDataAfterLoading(jsonData, similarData, photos);
                        }
                    });

                }catch (IOException e) {
                    Log.e("httpIOException", e.toString());
                }
            }
        });
        httpRequest.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.facebook_menu, menu);
        Drawable icon = menu.getItem(0).getIcon();
        icon.setColorFilter(ContextCompat.getColor(this, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
        return true;
    }

    private void initDataAfterLoading(String detail, String similar, String photos) {
        Bundle bundle = new Bundle();
        Item item = (Item)this.getIntent().getExtras().getSerializable("item");
        bundle.putSerializable("item", item);
        bundle.putString("detail", detail);
        bundle.putString("similar", similar);
        bundle.putString("photos", photos);
        ItemDetailPagerAdapter adapter = new ItemDetailPagerAdapter(getSupportFragmentManager(), this, bundle);
        viewPager.setAdapter(adapter);
    }

    private void setUpTabs() {
        LayoutInflater lay1 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater lay2 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater lay3 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater lay4 = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View tab1 = lay1.inflate(R.layout.nav_tab, null);
        View tab2 = lay2.inflate(R.layout.nav_tab, null);
        View tab3 = lay3.inflate(R.layout.nav_tab, null);
        View tab4 = lay4.inflate(R.layout.nav_tab, null);

        ImageView tab_icon1 = tab1.findViewById(R.id.nav_icon);
        tab_icon1.setImageResource(R.drawable.information_variant);
        tab_icon1.setColorFilter(ContextCompat.getColor(this, R.color.btn_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
        TextView tab_text1 = tab1.findViewById(R.id.nav_label);
        tab_text1.setText("PRODUCT");

        ImageView tab_icon2 = tab2.findViewById(R.id.nav_icon);
        tab_icon2.setImageResource(R.drawable.truck_delivery);
        tab_icon2.setColorFilter(ContextCompat.getColor(this, R.color.btn_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
        TextView tab_text2 = tab2.findViewById(R.id.nav_label);
        tab_text2.setText("SHIPPING");

        ImageView tab_icon3 = tab3.findViewById(R.id.nav_icon);
        tab_icon3.setImageResource(R.drawable.google);
        tab_icon3.setColorFilter(ContextCompat.getColor(this, R.color.btn_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
        TextView tab_text3 = tab3.findViewById(R.id.nav_label);
        tab_text3.setText("PHOTOS");

        ImageView tab_icon4 = tab4.findViewById(R.id.nav_icon);
        tab_icon4.setImageResource(R.drawable.equal);
        tab_icon4.setColorFilter(ContextCompat.getColor(this, R.color.btn_text_color), android.graphics.PorterDuff.Mode.SRC_IN);
        TextView tab_text4 = tab4.findViewById(R.id.nav_label);
        tab_text4.setText("SIMILAR");

        tabs.addTab(tabs.newTab().setCustomView(tab1));
        tabs.addTab(tabs.newTab().setCustomView(tab2));
        tabs.addTab(tabs.newTab().setCustomView(tab3));
        tabs.addTab(tabs.newTab().setCustomView(tab4));

        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
