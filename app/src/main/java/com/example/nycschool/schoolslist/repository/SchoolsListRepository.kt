package com.example.nycschool.schoolslist.repository

import com.example.nycschool.common.Repository
import com.example.nycschool.models.School
import com.example.nycschool.web.RetrofitFactory
import io.reactivex.Single

/*
    No particular reason to use kotlin here, just mixing it up. Although it is much easier to do
    manual dependency injection with kotlin
 */
class SchoolsListRepository(
    retrofitFactory: RetrofitFactory = RetrofitFactory(),
): Repository(retrofitFactory), ISchoolsListRepository {
    override fun getAllSchools(): Single<List<School>> {
        /*
            Ideally there would be a room database we would check for cached data before fetching
            from the web service
         */
        val retrofit = retrofitFactory.getInstance()
        val service = retrofit.create(SchoolsListService::class.java)

        return service.allSchools
    }
}