package com.example.nycschool;


import com.example.nycschool.models.SATSummary;
import com.example.nycschool.schooldetail.repository.SATSummaryRepository;
import com.example.nycschool.schooldetail.repository.SATSummaryService;
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
public class SATSummaryRepositoryTest {
    private SATSummaryRepository subject;

    private RetrofitFactory mockRetrofitFactory;
    private Retrofit mockRetrofit;
    private SATSummaryService mockService;
    private Single<List<SATSummary>> mockApiResponse;

    @Before
    public void setup() {
        mockRetrofitFactory = mock(RetrofitFactory.class);
        mockRetrofit = mock(Retrofit.class);
        mockService = mock(SATSummaryService.class);
        mockApiResponse = mock(Single.class);

        when(mockRetrofitFactory.getInstance()).thenReturn(mockRetrofit);
        when(mockRetrofit.create(any())).thenReturn(mockService);
        when(mockService.getSummary(any())).thenReturn(mockApiResponse);

        subject = new SATSummaryRepository(mockRetrofitFactory);
    }

    @Test
    public void apiReturnsNonEmptyResponse_repositoryReturnsFirstEntry() {
        String expectedId = "testId";
        List<SATSummary> expectedResponse = new ArrayList<>();
        expectedResponse.add(new SATSummary("test", "test", "test", "test", "test", "test"));

        when(mockApiResponse.map(any())).thenReturn(mock(Single.class));

        subject.getSATSummary(expectedId);

        verify(mockRetrofitFactory).getInstance();
        verify(mockRetrofit).create(SATSummaryService.class);
        verify(mockService).getSummary(expectedId);

        ArgumentCaptor<Function<List<SATSummary>, SATSummary>> captor;
        captor = ArgumentCaptor.forClass(Function.class);
        verify(mockApiResponse).map(captor.capture());

        // Assert the api response is mapped to a single value
        try {
            SATSummary actualResponse = captor.getValue().apply(expectedResponse);
            assertEquals(expectedResponse.get(0), actualResponse);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void apiReturnsEmptyResponse_repositoryReturnsDefaultValue() {
        String expectedId = "testId";
        List<SATSummary> expectedResponse = new ArrayList<>();

        when(mockApiResponse.map(any())).thenReturn(mock(Single.class));

        subject.getSATSummary(expectedId);

        verify(mockRetrofitFactory).getInstance();
        verify(mockRetrofit).create(SATSummaryService.class);
        verify(mockService).getSummary(expectedId);

        ArgumentCaptor<Function<List<SATSummary>, SATSummary>> captor;
        captor = ArgumentCaptor.forClass(Function.class);
        verify(mockApiResponse).map(captor.capture());

        // Assert the api response is mapped to a single value
        try {
            SATSummary actualResponse = captor.getValue().apply(expectedResponse);
            assertEquals(SATSummary.Companion.getEmpty(), actualResponse);
        } catch (Exception e) {
            fail();
        }
    }
}
