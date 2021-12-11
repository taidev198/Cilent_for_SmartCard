package com.example.client.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Department implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("quanlity")
    private int quanlity;
    private String shortName;
    private int numberLate = 0;

    public int getNumberLate() {
        return numberLate;
    }

    public void setNumberLate(int numberLate) {
        this.numberLate = numberLate;
    }

    public int getMid() {
        return id;
    }

    public void setMid(int mid) {
        this.id = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getmQuanity() {
        return quanlity;
    }

    public void setmQuanity(int mQuanity) {
        this.quanlity = mQuanity;
    }

    public String getShortName() {
        String[] a = name.split(" ");
        String str = "";
        for (String s : a) {
            str = str + s.charAt(0);
        }
        return str.toUpperCase();
    }

    @Override
    public String toString() {
        return name;
    }
}
