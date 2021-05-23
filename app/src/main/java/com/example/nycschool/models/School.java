package com.example.nycschool.models;

public class School {
    private String name;
    private String address;
    private String website;

    public School(
        String name,
        String address,
        String website
    ) {
        this.name = name;
        this.address = address;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getWebsite() {
        return website;
    }
}
