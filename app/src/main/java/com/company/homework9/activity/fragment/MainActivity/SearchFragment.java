package com.company.homework9.activity.fragment.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.company.homework9.AutoCompleteAdapter;
import com.company.homework9.Item;
import com.company.homework9.R;
import com.company.homework9.ZipCodeCall;
import com.company.homework9.activity.ListDisplayActivity;
import com.company.homework9.activity.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends BaseFragment {
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoCompleteAdapter autoCompleteAdapter;
    public final static String[] OPTIONS = {"All", "Art", "Baby", "Books", "Clothing, Shoes & Accessories",
    "Computers, Tablets & Networking", "Health & Beauty", "Music", "Video Games & Consoles"};
    private EditText keywordInput;
    private TextView keywordValidation;
    private TextView zipCodeValidation;
    private Spinner category;

    private CheckBox isNew;
    private CheckBox isUsed;
    private CheckBox isUnspecified;
    private CheckBox isLocalPickUp;
    private CheckBox isFreeShipping;

    private CheckBox enableNearBySearch;
    private LinearLayout optionalPart;

    private RadioGroup radioGroup;
    private RadioButton userZipCodeRadio;
    private EditText milesInput;
    private AppCompatAutoCompleteTextView userInputZipCode;

    private Button searchButton;
    private Button clearButton;

    @Override
    protected View initView() {
        return null;
    }

    @Override
    protected void loadData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        keywordInput = v.findViewById(R.id.KeywordInput);

        keywordValidation = v.findViewById(R.id.keyword_invalid);
        zipCodeValidation = v.findViewById(R.id.zip_code_invalid);
        category = v.findViewById(R.id.category_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),android.R.layout.simple_spinner_item, OPTIONS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        isNew = v.findViewById(R.id.btn_new);
        isUsed = v.findViewById(R.id.btn_used);
        isUnspecified = v.findViewById(R.id.btn_unspecified);

        isLocalPickUp = v.findViewById(R.id.local_btn);
        isFreeShipping = v.findViewById(R.id.free_shipping_btn);

        enableNearBySearch = v.findViewById(R.id.enable_nearby_box);
        optionalPart = v.findViewById(R.id.optional_content);
        if(!enableNearBySearch.isChecked()) {
            optionalPart.setVisibility(View.GONE);
        } else {
            optionalPart.setVisibility(View.VISIBLE);
        }

        autoCompleteAdapter = new AutoCompleteAdapter(getActivity(),
                android.R.layout.simple_dropdown_item_1line);
        radioGroup = v.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroup.findViewById(checkedId);
                int index = radioGroup.indexOfChild(radioButton);
                switch (index){
                    case 0:
                        userInputZipCode.setText("");
                        userInputZipCode.setEnabled(false);
                        break;
                    case 1:
                        userInputZipCode.setEnabled(true);
                        break;
                }
            }
        });
        enableNearBySearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(enableNearBySearch.isChecked()) {
                    optionalPart.setVisibility(View.VISIBLE);
                } else {
                    optionalPart.setVisibility(View.GONE);
                }
            }
        });

        milesInput = v.findViewById(R.id.miles_input);
        userInputZipCode = v.findViewById(R.id.auto_complete_edit_text);
        userInputZipCode.setEnabled(false);
        userZipCodeRadio = v.findViewById(R.id.from_user_input);

        searchButton = v.findViewById(R.id.search_btn);
        clearButton = v.findViewById(R.id.clear_btn);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordInput.setText("");
                keywordValidation.setVisibility(View.GONE);
                zipCodeValidation.setVisibility(View.GONE);
                category.setSelection(0);
                isNew.setChecked(false);
                isUsed.setChecked(false);
                isUnspecified.setChecked(false);
                isLocalPickUp.setChecked(false);
                isFreeShipping.setChecked(false);
                milesInput.setText("");
                radioGroup.check(R.id.from_current);
                userInputZipCode.setText("");
                enableNearBySearch.setChecked(false);
                optionalPart.setVisibility(View.GONE);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean canSubmit = true;
                String keyword = keywordInput.getText().toString();
                if(keyword.equals("")) {
                    canSubmit = false;
                    keywordValidation.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"Please fix all fields with errors",Toast.LENGTH_SHORT).show();
                } else {
                    keywordValidation.setVisibility(View.GONE);
                }
                if(enableNearBySearch.isChecked() && userZipCodeRadio.isChecked() && userInputZipCode.getText().toString().equals("")) {
                    zipCodeValidation.setVisibility(View.VISIBLE);
                    canSubmit = false;
                    Toast.makeText(getActivity(),"Please fix all fields with errors",Toast.LENGTH_SHORT).show();
                } else {
                    zipCodeValidation.setVisibility(View.GONE);
                }

                if(canSubmit) {
                    String url = getTargetUrl();
                    Intent intent = new Intent(getActivity(), ListDisplayActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("keyword", keywordInput.getText().toString());
                    startActivity(intent);
                }
            }
        });

        userInputZipCode.setThreshold(2);
        userInputZipCode.setAdapter(autoCompleteAdapter);
        userInputZipCode.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                    }
                });

        userInputZipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(userInputZipCode.getText())) {
                        makeApiCall(userInputZipCode.getText().toString());
                    }
                }
                return false;
            }
        });

        return v;
    }
    private String getTargetUrl() {
        String keyword = keywordInput.getText().toString();
        String categorySelected = category.getSelectedItem().toString();
        String categoryId = "default";

        if(categorySelected.equals("Art")) {
            categoryId = "550";
        } else if(categorySelected.equals("Baby")) {
            categoryId = "2984";
        } else if(categorySelected.equals("Books")) {
            categoryId = "267";
        } else if(categorySelected.equals("Clothing, Shoes & Accessories")) {
            categoryId = "11450";
        } else if(categorySelected.equals("Computers, Tablets & Networking")) {
            categoryId = "58058";
        } else if(categorySelected.equals("Health & Beauty")) {
            categoryId = "26395";
        } else if(categorySelected.equals("Music")) {
            categoryId = "11233";
        } else if(categorySelected.equals("Video Games & Consoles")) {
            categoryId = "2984";
        }
        boolean ifNew = isNew.isChecked();
        boolean ifUsed = isUsed.isChecked();
        boolean ifUnspecified = isUnspecified.isChecked();
        boolean ifLocal = isLocalPickUp.isChecked();
        boolean ifFree = isFreeShipping.isChecked();
        String distance = milesInput.getText().toString();
        if(distance.equals("")) {
            distance = "10";
        }
        String zip = "90007";
        if(!userInputZipCode.getText().toString().equals("")) {
            zip = userInputZipCode.getText().toString();
        }
        String targetUrl = "http://searchproducts.us-east-2.elasticbeanstalk.com/search?";
        targetUrl = targetUrl + "distance=" + distance + "&isFreeShipping=" + ifFree;
        targetUrl += "&isLocalPickUp=" + ifLocal;
        targetUrl += "&isUnspecified=" + ifUnspecified;
        targetUrl += "&isUsed=" + ifUsed;
        targetUrl += "&isNew=" + ifNew;
        targetUrl += "&category=" + categoryId;
        targetUrl += "&keyword=" + keyword;
        targetUrl += "&zipcode=" + zip;
        return targetUrl;
    }

    private void makeApiCall(String text) {
        ZipCodeCall.make(getActivity(), text, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                List<String> stringList = new ArrayList<>();
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray array = responseObject.getJSONArray("postalCodes");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject row = array.getJSONObject(i);
                        stringList.add(row.getString("postalCode"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                autoCompleteAdapter.setData(stringList);
                autoCompleteAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();
    }
}
