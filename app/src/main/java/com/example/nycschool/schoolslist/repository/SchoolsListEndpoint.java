package com.example.nycschool.schoolslist.repository;

import com.example.nycschool.models.School;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

interface SchoolsListEndpoint {
    @GET("v2/organizations/{organization_id}/teams/")
    Single<List<School>> getAllSchools();
}
