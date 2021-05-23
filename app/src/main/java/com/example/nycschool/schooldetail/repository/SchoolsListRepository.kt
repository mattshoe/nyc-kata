package com.example.nycschool.schooldetail.repository

import com.example.nycschool.models.SATSummary
import com.example.nycschool.models.School
import com.example.nycschool.web.RetrofitWrapper
import io.reactivex.Single

/*
    No particular reason to use kotlin here, just mixing it up. Although it is much easier to do
    manual dependency injection with kotlin
 */
class SATSummaryRepository(
    private val schoolsListService: SATSummaryService = RetrofitWrapper.singleton.create(SATSummaryService::class.java)
): ISATSummaryRepository {
    override fun getSATSummary(id: String): Single<SATSummary> {
        /*
            Ideally there would be a room database we would check for cached data before fetching
            from the web service
         */

        return schoolsListService.getSummary(id).map { result ->
            if (result.count() > 0) {
                result.first()
            } else {
                // RXJava does not play nice with null emissions, so provideing a default instance
                SATSummary.empty
            }
        }
    }
}