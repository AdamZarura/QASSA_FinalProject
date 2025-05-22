package com.example.qassa_finalproject;

public class User {
    private String Uid;
    private String name;
    private int age;
    private String imageUr;

    // Default constructor (required for Firestore)
    public User() {
    }

    public User(String name, int age, String Uid) {
        this.name = name;
        this.age = age;
        this.Uid = Uid;
        this.imageUr = null;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getImageUr() {
        return imageUr;
    }

    public void setImageUr(String imageUr) {
        this.imageUr = imageUr;
    }
}
