package com.example.nycschool.models;

import com.squareup.moshi.Json;

public class School {
    @Json(name = "school_name")
    public final String name;

    @Json(name = "location")
    public final String address;

    @Json(name = "website")
    public final String website;

    public School(
        String name,
        String address,
        String website
    ) {
        this.name = name;
        this.address = address;
        this.website = website;
    }
}
