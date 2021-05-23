package com.example.nycschool.web;

import okhttp3.OkHttpClient;

public class OkHttpWrapper {
    public static OkHttpWrapper singleton = new OkHttpWrapper();

    private OkHttpClient client;

    public OkHttpWrapper() {
        client = new OkHttpClient.Builder().build();
    }

    public OkHttpClient getClient() {
        return client;
    }
}
