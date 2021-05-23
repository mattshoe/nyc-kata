package com.example.nycschool.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/*
    Using kotlin's data class here for its very convenient parcelable implementation
 */
@Parcelize
public data class SATSummary(
    @field:Json(name = "dbn")
    val id: String,

    @field:Json(name = "school_name")
    val name: String,

    @field:Json(name = "num_of_sat_test_takers")
    val participants: String,

    @field:Json(name = "sat_critical_reading_avg_score")
    val readingScore: String,

    @field:Json(name = "sat_math_avg_score")
    val mathScore: String,

    @field:Json(name = "sat_writing_avg_score")
    val writingScore: String
): Parcelable {
    companion object {
        val empty by lazy {
            SATSummary(
                "Not Available",
                "Not Available",
                "Not Available",
                "Not Available",
                "Not Available",
                "Not Available"
            )
        }
    }
}