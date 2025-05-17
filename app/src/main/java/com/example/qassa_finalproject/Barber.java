package com.example.qassa_finalproject;

public class Barber {
    private int id;
    private String name;
    private BarberShop ownShop;

    public Barber(String name, BarberShop ownShop) {
        this.name = name;
        this.ownShop = ownShop;
    }

    public void setName(String name) {this.name = name;}
    public void setOwnShop(BarberShop ownShop) {this.ownShop = ownShop;}

    public String getName() {return name;}
    public BarberShop getOwnShop() {return ownShop;}
}
