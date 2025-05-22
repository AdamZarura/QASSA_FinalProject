package com.example.qassa_finalproject;

public class Barber {
    private String Uid;
    private String name;
    private BarberShop ownShop;

    // Default constructor (required for Firestore)
    public Barber() {
    }

    public Barber(String name,  String id) {
        this.name = name;
        this.Uid = id;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }
    public void setName(String name) {this.name = name;}
    public void setOwnShop(BarberShop ownShop) {this.ownShop = ownShop;}

    public String getUid() {
        return Uid;
    }
    public String getName() {return name;}
    public BarberShop getOwnShop() {return ownShop;}
}
