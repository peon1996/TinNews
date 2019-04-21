package com.company.homework9;

public class SimilarItem {
    private String imageURL;
    private String webURL;
    private String title;
    private String shipping;
    private String days;
    private String price;

    public String getImageURL() {
        return imageURL;
    }

    public String getDays() {
        return days;
    }

    public String getPrice() {
        return price;
    }

    public String getShipping() {
        return shipping;
    }

    public String getTitle() {
        return title;
    }

    public String getWebURL() {
        return webURL;
    }

    public SimilarItem(String imageURL, String webURL, String title, String shipping, String days, String price) {
        this.imageURL = imageURL;
        this.webURL = webURL;
        this.title = title;
        this.shipping = shipping;
        this.days = days;
        this.price = price;
    }
}
