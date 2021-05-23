package com.example.nycschool.schoolslist.repository;

import com.example.nycschool.models.School;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface SchoolsListService {
    @GET("resource/s3k6-pzi2.json")
    Single<List<School>> getAllSchools();
}
