package com.example.nycschool.schoolslist.repository

import com.example.nycschool.models.School
import com.example.nycschool.web.RetrofitWrapper
import io.reactivex.Single

class SchoolsListRepository(
    private val schoolsListService: SchoolsListService = RetrofitWrapper.singleton.create(SchoolsListService::class.java)
) {
    fun getAllSchools(): Single<List<School>> {
        /*
            Ideally there would be a room database we would check for cached data before fetching
            from the web service
         */
        return schoolsListService.allSchools
    }
}