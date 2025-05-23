package com.example.qassa_finalproject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BarberShop {
    String Bid;
    private String bImage;   //back ground image
    private String fImage;   //front image
    private int price;
    private String Name;
    //private String[] DialyTime;
    private ArrayList<String> images;

    private String sun;
    private String mon;
    private String tue;
    private String wed;
    private String thu;
    private String fri;
    private String sat;
    private int EstimatedQueue;

    public BarberShop(){
    }

   /* public BarberShop(int Bid,int price, String[] DialyTime, ArrayList<String> images){
        this.Bid=Bid;
        this.price = price;
        this.DialyTime = DialyTime;
        this.images = images;
    }
    */

    public BarberShop(String Bid,int price,int EstimatedQueue,String Name,String sun,String mon,String tue,String wed,String thu,String fri,String sat){
       this.Bid=Bid;
       this.price = price;
       this.EstimatedQueue=EstimatedQueue;
       this.Name= Name;

       this.sun=sun;
       this.mon=mon;
       this.tue=tue;
       this.wed=wed;
       this.thu=thu;
       this.fri=fri;
       this.sat=sat;

        GenerateQueue(sun,EstimatedQueue);
    }

    public void setBImage(String bImage) {this.bImage = bImage;}
    public void setFImage(String fImage) {this.fImage = fImage;}
    public void setPrice(int price) {this.price = price;}
 //   public void setDialyTime(String[] DialyTime) {this.DialyTime = DialyTime;}
    public void setImages(ArrayList<String> images) {this.images = images;}
    public void addImage(String image){this.images.add(image);}

    public String getBImage() {return bImage;}
    public String getFImage() {return fImage;}
    public int getPrice() {return price;}
 //   public String[] getDialyTime() {return DialyTime;}
    public ArrayList<String> getImages() {return images;}



    public String getBid() {
        return Bid;
    }
    public void setBid(String bid) {
        Bid = bid;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;}

    public String getSun() {
        return sun;
    }
    public void setSun(String sun) {
        this.sun = sun;
    }

    public String getMon() {
        return mon;
    }
    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getTue() {
        return tue;
    }
    public void setTue(String tue) {
        this.tue = tue;
    }

    public String getWed() {
        return wed;
    }
    public void setWed(String wed) {
        this.wed = wed;
    }

    public String getThu() {
        return thu;
    }
    public void setThu(String thu) {
        this.thu = thu;
    }

    public String getFri() {
        return fri;
    }
    public void setFri(String fri) {
        this.fri = fri;
    }

    public String getSat() {
        return sat;
    }
    public void setSat(String sat) {
        this.sat = sat;
    }




    public String addTime(String theTime, int t){//t only minutes

        int h;
        int m;


        h = Integer.parseInt(theTime.charAt(0) + "" + theTime.charAt(1));
        m = Integer.parseInt(theTime.charAt(3) + "" + theTime.charAt(4));

        m += t;

        while (m >= 60)
        {
            m -= 60;
            h++;
        }

        String newT;
        if (h >= 10)
        {
            if(m<10)
                newT = h + ":0" + m;
            else
                newT = h + ":" + m;
        }
        else
        {
            if (m < 10)
                newT = "0" +h + ":0" + m;
            else
                newT = "0" +h + ":" + m;
        }

        return newT;
    }

    public String[] GenerateQueue(String dayTime,int EstinatedQueue){



        String b="";
        String e="";

        int i = 0;
        while ( dayTime.charAt(i) != '-')
        {
            b += dayTime.charAt(i);
            i++;
        }
        i++;
        while (i<dayTime.length())
        {
            e += dayTime.charAt(i);
            i++;
        }

        if (b.charAt(1) == ':')
            b = "0" + b;
        if (e.charAt(1) == ':')
            e = "0" + e;


        //نحسب الفرق بين الساعات
        int h ;
        int m ;


        h= Integer.parseInt(e.charAt(0)+""+e.charAt(1)) - Integer.parseInt(b.charAt(0)+""+b.charAt(1));
        m = Integer.parseInt(e.charAt(3) + "" + e.charAt(4)) - Integer.parseInt(b.charAt(3) + "" + b.charAt(4));

        if (m < 0)
        {
            m += 60;
            h -= 1;
        }



        int allTime = (h * 60) + m; //in minutes

        String[] Q = new String[allTime/EstinatedQueue];

        for(int w=0; w<Q.length; w++)
        {
            Q[w] = addTime(b, EstinatedQueue * w);
        }


        return Q;

    }


    public static String getTodayDayName() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        return sdf.format(date);
    }


}
