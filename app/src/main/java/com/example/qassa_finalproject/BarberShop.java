package com.example.qassa_finalproject;

import java.util.ArrayList;

public class BarberShop {
    int id;
    private String bImage;   //back ground image
    private String fImage;   //front image
    private int price;
    private String[] DialyTime;
    private ArrayList<String> images;

    public BarberShop(String bImage, String fImage, int price, String[] DialyTime, ArrayList<String> images){
        this.bImage = bImage;
        this.fImage = fImage;
        this.price = price;
        this.DialyTime = DialyTime;
        this.images = images;
    }

    public void setBImage(String bImage) {this.bImage = bImage;}
    public void setFImage(String fImage) {this.fImage = fImage;}
    public void setPrice(int price) {this.price = price;}
    public void setDialyTime(String[] DialyTime) {this.DialyTime = DialyTime;}
    public void setImages(ArrayList<String> images) {this.images = images;}
    public void addImage(String image){this.images.add(image);}

    public String getBImage() {return bImage;}
    public String getFImage() {return fImage;}
    public int getPrice() {return price;}
    public String[] getDialyTime() {return DialyTime;}
    public ArrayList<String> getImages() {return images;}
}
