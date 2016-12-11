package com.example.nicolas.shootemup;

/**
 * Created by Nicolas on 11/12/2016.
 */

public class UpgradeItem {

    private int id;
    private String title;
    private String price;

    public UpgradeItem(int id, String title, String price) {
        super();
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    // getters and setters...
}
