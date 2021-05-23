package com.example.nycschool.schooldetail.repository

import com.example.nycschool.common.Repository
import com.example.nycschool.models.SATSummary
import com.example.nycschool.web.RetrofitFactory
import io.reactivex.Single

/*
    No particular reason to use kotlin here, just mixing it up. Although it is much easier to do
    manual dependency injection with kotlin via default constructor params
 */
class SATSummaryRepository(
    retrofitFactory: RetrofitFactory = RetrofitFactory(),
): Repository(retrofitFactory), ISATSummaryRepository {
    override fun getSATSummary(id: String): Single<SATSummary> {
        /*
            Ideally there would be a room database we would check for cached data before fetching
            from the web service
         */
        val retrofit = retrofitFactory.getInstance()
        val service = retrofit.create(SATSummaryService::class.java)

        return service.getSummary(id).map { result ->
            // Service returns an array with 0 or 1 elements
            if (result.count() > 0) {
                result.first()
            } else {
                /*
                    RXJava does not play nice with null emissions, so providing a default instance
                    in the case that the api returns an empty list (which is very likely)
                 */
                SATSummary.empty
            }
        }
    }
}