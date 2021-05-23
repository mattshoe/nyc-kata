package com.example.nycschool.schoolslist.repository

import com.example.nycschool.models.School
import io.reactivex.Single

interface ISchoolsListRepository {
    fun getAllSchools(): Single<List<School>>
}