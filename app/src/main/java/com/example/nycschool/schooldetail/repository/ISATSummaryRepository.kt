package com.example.nycschool.schooldetail.repository

import com.example.nycschool.models.SATSummary
import com.example.nycschool.models.School
import io.reactivex.Single

interface ISATSummaryRepository {
    fun getSATSummary(id: String): Single<SATSummary>
}