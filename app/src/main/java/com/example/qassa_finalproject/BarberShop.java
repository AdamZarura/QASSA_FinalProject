package com.example.qassa_finalproject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class BarberShop implements Serializable {
    String Bid;
    private String bImage;   // background image
    private String fImage;   // front image
    private int price;
    private String Name;
    private ArrayList<String> images;
    private String sun;
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private int EstimatedQueue;
    // Store booked appointments: key is date (YYYY-MM-DD), value is set of booked times
    private HashMap<String, Set<String>> bookedAppointments;

    public BarberShop() {
        bookedAppointments = new HashMap<>();
    }

    public BarberShop(String Bid, int price, int EstimatedQueue, String Name, String sun, String mon, String tue, String wed, String thu, String fri, String sat) {
        this.Bid = Bid;
        this.price = price;
        this.EstimatedQueue = EstimatedQueue;
        this.Name = Name;
        this.sun = sun;
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thu = thu;
        this.fri = fri;
        this.sat = sat;
        this.bookedAppointments = new HashMap<>();
    }

    // Getters and Setters
    public String getBImage() { return bImage; }
    public void setBImage(String bImage) { this.bImage = bImage; }
    public String getFImage() { return fImage; }
    public void setFImage(String fImage) { this.fImage = fImage; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public ArrayList<String> getImages() { return images; }
    public void setImages(ArrayList<String> images) { this.images = images; }
    public void addImage(String image) { this.images.add(image); }
    public String getBid() { return Bid; }
    public void setBid(String bid) { Bid = bid; }
    public String getName() { return Name; }
    public void setName(String name) { Name = name; }
    public String getSun() { return sun; }
    public void setSun(String sun) { this.sun = sun; }
    public String getMon() { return mon; }
    public void setMon(String mon) { this.mon = mon; }
    public String getTue() { return tue; }
    public void setTue(String tue) { this.tue = tue; }
    public String getWed() { return wed; }
    public void setWed(String wed) { this.wed = wed; }
    public String getThu() { return thu; }
    public void setThu(String thu) { this.thu = thu; }
    public String getFri() { return fri; }
    public void setFri(String fri) { this.fri = fri; }
    public String getSat() { return sat; }
    public void setSat(String sat) { this.sat = sat; }
    public int getEstimatedQueue() { return EstimatedQueue; }
    public void setEstimatedQueue(int estimatedQueue) { EstimatedQueue = estimatedQueue; }

    public void setBookedAppointments(HashMap<String, Set<String>> bookedAppointments) {
        this.bookedAppointments = bookedAppointments;
    }
    public HashMap<String, Set<String>> getBookedAppointments() {
        return bookedAppointments;
    }


    public boolean bookAppointment(String date, String time) {
        // Initialize set for the date if it doesn't exist
        bookedAppointments.putIfAbsent(date, new HashSet<>());
        // Check if time slot is already booked
        if (bookedAppointments.get(date).contains(time)) {
            return false; // Time slot is taken
        }
        // Book the time slot
        bookedAppointments.get(date).add(time);
        return true;
    }

    public String addTime(String theTime, int t) {
        int h = Integer.parseInt(theTime.charAt(0) + "" + theTime.charAt(1));
        int m = Integer.parseInt(theTime.charAt(3) + "" + theTime.charAt(4));

        m += t;
        while (m >= 60) {
            m -= 60;
            h++;
        }

        String newT;
        if (h >= 10) {
            newT = h + (m < 10 ? ":0" : ":") + m;
        } else {
            newT = "0" + h + (m < 10 ? ":0" : ":") + m;
        }
        return newT;
    }

    public String[] GenerateQueue(String dayTime, int EstimatedQueue, String date) {
        String b = "";
        String e = "";
        int i = 0;
        while (dayTime.charAt(i) != '-') {
            b += dayTime.charAt(i);
            i++;
        }
        i++;
        while (i < dayTime.length()) {
            e += dayTime.charAt(i);
            i++;
        }

        if (b.charAt(1) == ':') b = "0" + b;
        if (e.charAt(1) == ':') e = "0" + e;

        int h = Integer.parseInt(e.charAt(0) + "" + e.charAt(1)) - Integer.parseInt(b.charAt(0) + "" + b.charAt(1));
        int m = Integer.parseInt(e.charAt(3) + "" + e.charAt(4)) - Integer.parseInt(b.charAt(3) + "" + b.charAt(4));

        if (m < 0) {
            m += 60;
            h -= 1;
        }

        int allTime = (h * 60) + m; // in minutes
        int slotCount = allTime / EstimatedQueue;
        String[] Q = new String[slotCount];
        Set<String> bookedSlots = bookedAppointments.getOrDefault(date, new HashSet<>());

        for (int w = 0; w < slotCount; w++) {
            String time = addTime(b, EstimatedQueue * w);
            // Only include time slots that are not booked
            if (!bookedSlots.contains(time)) {
                Q[w] = time;
            } else {
                Q[w] = null; // Mark as booked
            }
        }
        return Q;
    }
}