package com.company.homework9.activity.fragment.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.company.homework9.Item;
import com.company.homework9.R;
import com.company.homework9.activity.ListDisplayActivity;
import com.company.homework9.activity.fragment.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends BaseFragment {
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
    private EditText userInputZipCode;

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
        userInputZipCode = v.findViewById(R.id.user_input_zip_code);
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

    private void sendHttpRequest() {
        Thread httpRequest = new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient myClient = new OkHttpClient();
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
                Log.d("url", targetUrl);
                Request req = new Request.Builder().get().url(targetUrl).build();
                Response res = null;
                String data;

                try {
                    res = myClient.newCall(req).execute();
                    data = res.body().string();
                    List<Item> list = parseResponse(data);
//                    String test = "";
//                    for(int i = 0; i < list.size(); i++) {
//                        Item cur = list.get(i);
//                        String str = "";
//                        str += cur.getCondition() + " " + cur.getImageUrl() + " " + cur.getShippingCost()
//                                + " " + cur.getTitle() + " " + cur.getZipCode() + "/n";
//                        test += str;
//                    }
//                    Log.d("list check", test);

                }catch (IOException e) {
                    Log.e("httpIOException", e.toString());
                }catch (JSONException e) {
                    Log.e("JSONException", e.toString());
                }
            }
        });
        httpRequest.start();
    }

    private List<Item> parseResponse(String response) throws JSONException {
        JSONObject responseJSON = new JSONObject(response);
        List<Item> result = new ArrayList<>();
        JSONArray itemList = responseJSON.getJSONArray("findItemsAdvancedResponse")
                .getJSONObject(0).getJSONArray("searchResult").getJSONObject(0).
                getJSONArray("item");
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
            String see;
            if(current.has("viewItemURL")) {
                see = current.getJSONArray("viewItemURL").get(0).toString();
            } else {
                see = "N/A";
            }
            Item item = new Item(imageUrl, title, zipCode, shippingCost, condition, shippingCost, condition, see);
            result.add(item);
        }
        return result;
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
