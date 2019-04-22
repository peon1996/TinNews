package com.company.homework9.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.Toast;

import com.company.homework9.R;
import com.company.homework9.activity.fragment.MainActivity.MainViewPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    public static String localZipCode = "";
    private static ViewPager viewPager;
    private TabLayout mTab;

    protected LocationManager locationManager;
    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        getSupportActionBar().setElevation(0);

        sendHttpRequest();
    }

    private void sendHttpRequest() {
        Thread httpRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://ip-api.com/json";
                OkHttpClient myClient = new OkHttpClient();
                Request req = new Request.Builder().get().url(url).build();
                Response res = null;
                String data;
                try {
                    res = myClient.newCall(req).execute();
                    data = res.body().string();
                    parseJSON(data);
                }catch (IOException e) {
                    Log.e("httpIOException", e.toString());
                }catch (JSONException e) {
                    Log.e("JSONException", e.toString());
                }
            }
        });
        httpRequest.start();
    }

    private void parseJSON(String data) throws JSONException{
        JSONObject js = new JSONObject(data);
        if(js.has("zip")) {
            localZipCode = js.getString("zip");
        }
    }

    private void initData() {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        mTab.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1) {
                    viewPager.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    public static ViewPager getViewPager() {
        return viewPager;
    }

    private void initView() {
        mTab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);
    }
}
