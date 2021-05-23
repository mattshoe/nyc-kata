package com.example.nycschool.web

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitWrapper {
    companion object {
        val singleton: Retrofit by lazy {
            Retrofit.Builder()
                    .baseUrl("https://data.cityofnewyork.us/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create(MoshiWrapper.singleton))
                    .build()
        }
    }
}