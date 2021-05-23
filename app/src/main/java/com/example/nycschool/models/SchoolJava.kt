package com.example.nycschool.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/*
    Using kotlin's data class here for its very convenient parcelable implementation
 */
@Parcelize
data class School(
    @field:Json(name = "school_name")
    val name: String,

    @field:Json(name = "location")
    val address: String,

    @field:Json(name = "website")
    val website: String
): Parcelable