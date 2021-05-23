package com.example.nycschool.schooldetail.repository;

import com.example.nycschool.models.SATSummary;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SATSummaryService {

    @GET("resource/f9bf-2cp4.json")
    Single<List<SATSummary>> getSummary(
        @Query("dbn") String id
    );

}
