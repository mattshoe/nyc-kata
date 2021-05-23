package com.example.nycschool;


import com.example.nycschool.models.SATSummary;
import com.example.nycschool.models.School;
import com.example.nycschool.schooldetail.repository.SATSummaryRepository;
import com.example.nycschool.schooldetail.repository.SATSummaryService;
import com.example.nycschool.schoolslist.repository.SchoolsListRepository;
import com.example.nycschool.schoolslist.repository.SchoolsListService;
import com.example.nycschool.web.RetrofitFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SchoolsListRepositoryTest {
    private SchoolsListRepository subject;

    private RetrofitFactory mockRetrofitFactory;
    private Retrofit mockRetrofit;
    private SchoolsListService mockService;
    private Single<List<School>> mockApiResponse;

    @Before
    public void setup() {
        mockRetrofitFactory = mock(RetrofitFactory.class);
        mockRetrofit = mock(Retrofit.class);
        mockService = mock(SchoolsListService.class);
        mockApiResponse = mock(Single.class);

        when(mockRetrofitFactory.getInstance()).thenReturn(mockRetrofit);
        when(mockRetrofit.create(any())).thenReturn(mockService);
        when(mockService.getAllSchools()).thenReturn(mockApiResponse);

        subject = new SchoolsListRepository(mockRetrofitFactory);
    }

    @Test
    public void apiReturnsNonEmptyResponse_repositoryReturnsFirstEntry() {
        String expectedId = "testId";
        List<School> expectedResponse = new ArrayList<>();
        expectedResponse.add(new School("test", "test", "test", "test", "test", "test", "test"));

        when(mockApiResponse.map(any())).thenReturn(mock(Single.class));

        subject.getAllSchools();

        verify(mockRetrofitFactory).getInstance();
        verify(mockRetrofit).create(SchoolsListService.class);
        verify(mockService).getAllSchools();
    }
}
