package com.example.qassa_finalproject;

public class User {
    private int id;
    private String name;
    private int age;
    private String imageUr;


    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public User(String name, int age, String imageUr){
        this.name = name;
        this.age = age;
        this.imageUr = imageUr;
    }

    public void setName(String name) {this.name = name;}
    public void setAge(int age) {this.age = age;}
    public void setImageUr(String imageUr) {this.imageUr = imageUr;}

    public String getName() {return name;}
    public int getAge() {return age;}
    public String getImageUr() {return imageUr;}
}
