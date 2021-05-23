package com.example.nycschool.schooldetail.repository

import com.example.nycschool.models.SATSummary
import io.reactivex.Single

interface ISATSummaryRepository {
    fun getSATSummary(id: String): Single<SATSummary>
}