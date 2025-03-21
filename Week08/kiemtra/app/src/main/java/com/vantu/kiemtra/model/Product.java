package com.example.lamthukiemtra.model;

import com.google.gson.annotations.SerializedName;

//Huynh Quoc Thang - 22110423
public class Product {
    @SerializedName("name")
    private String name;

    @SerializedName("imageUrl")
    private String imgPath;

    public Product(String name, String imgPath) {
        this.name = name;
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public String getImgPath() {
        return imgPath;
    }
}
