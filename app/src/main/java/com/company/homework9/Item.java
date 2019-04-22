package com.company.homework9;

import java.io.Serializable;

public class Item implements Serializable {
    private String imageUrl;
    private String title;
    private String zipCode;
    private String shippingCost;
    private String condition;
    private String cost;
    private String id;
    private String see;

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public String getCondition() {
        return condition;
    }

    public String getCost() {
        return cost;
    }

    public String getId() {
        return id;
    }

    public String getSee() {
        return see;
    }

    public Item(String imageUrl, String title, String zipCode, String shippingCost, String condition, String cost, String id, String see) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.zipCode = zipCode;
        this.shippingCost = shippingCost;
        this.condition = condition;
        this.cost = cost;
        this.id = id;
        this.see = see;
    }
}
