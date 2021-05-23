package com.example.nycschool.web

import com.squareup.moshi.Moshi

class MoshiWrapper {
    companion object {
        val singleton by lazy {
            Moshi.Builder().build()
        }
    }
}