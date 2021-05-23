package com.example.nycschool;

import android.os.Bundle;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.nycschool.common.ISchedulerProvider;
import com.example.nycschool.common.NavArguments;
import com.example.nycschool.models.SATSummary;
import com.example.nycschool.models.School;
import com.example.nycschool.schooldetail.SchoolDetailViewModel;
import com.example.nycschool.schooldetail.repository.ISATSummaryRepository;
import com.example.nycschool.schoolslist.SchoolsListViewModel;
import com.example.nycschool.schoolslist.repository.ISchoolsListRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class SchoolDetailViewModelTest {
    private SchoolDetailViewModel subject;

    private MutableLiveData<SATSummary> mockSummaryLiveData;
    private MutableLiveData<Throwable> mockSummaryErrorLiveData;
    private ISATSummaryRepository mockRepository;
    private ISchedulerProvider mockSchedulerProvider;

    @Before
    public void setup() {
        subject = new SchoolDetailViewModel();

        mockSummaryLiveData = mock(MutableLiveData.class);
        mockSummaryErrorLiveData = mock(MutableLiveData.class);
        mockRepository = mock(ISATSummaryRepository.class);
        mockSchedulerProvider = mock(ISchedulerProvider.class);

        subject.summaryLiveData = mockSummaryLiveData;
        subject.summaryErrorLiveData = mockSummaryErrorLiveData;
        subject.repository = mockRepository;
        subject.schedulerProvider = mockSchedulerProvider;
    }

    /*
        These unit tests are quite cramped, but only for time's sake
     */
    @Test
    public void loadingSummaryUpdatesLiveData() {
        Bundle mockBundle = mock(Bundle.class);
        Single<SATSummary> mockResponse = mock(Single.class);
        Scheduler mockIOScheduler = mock(Scheduler.class);
        Scheduler mockUIScheduler = mock(Scheduler.class);
        // Given more time I'd have used a builder pattern for this.
        School expectedSchool = new School("test", "test", "test", "test", "test", "test", "test");

        when(mockBundle.getParcelable(NavArguments.SCHOOL_DETAIL_BUNDLE_KEY)).thenReturn(expectedSchool);
        when(mockRepository.getSATSummary(any())).thenReturn(mockResponse);
        when(mockResponse.subscribeOn(any())).thenReturn(mockResponse);
        when(mockResponse.observeOn(any())).thenReturn(mockResponse);
        when(mockSchedulerProvider.io()).thenReturn(mockIOScheduler);
        when(mockSchedulerProvider.ui()).thenReturn(mockUIScheduler);

        subject.initialize(mockBundle);
        subject.loadSATSummary();

        // Verify that RX uses the proper schedulers
        verify(mockRepository).getSATSummary(expectedSchool.getId());
        verify(mockResponse).subscribeOn(mockIOScheduler);
        verify(mockResponse).observeOn(mockUIScheduler);

        // Capture repository observer
        ArgumentCaptor<SchoolDetailViewModel.SATSummaryObserver> captor;
        captor = ArgumentCaptor.forClass(SchoolDetailViewModel.SATSummaryObserver.class);
        verify(mockResponse).subscribeWith(captor.capture());

        // Verify the live data updated on success
        // Given more time I'd have used a builder pattern for this.
        SATSummary expectedSummary = new SATSummary("test", "test", "test", "test", "test", "test");
        captor.getValue().onSuccess(expectedSummary);
        verify(mockSummaryLiveData).setValue(expectedSummary);

        // Verify errors are updated
        captor.getValue().onError(null);
        verify(mockSummaryErrorLiveData).setValue(any());
    }

    @Test
    public void observeSchoolsList() {
        LifecycleOwner mockOwner = mock(LifecycleOwner.class);
        Observer<SATSummary> mockObserver = mock(Observer.class);

        subject.observeSATSummary(mockOwner, mockObserver);

        verify(mockSummaryLiveData).observe(mockOwner, mockObserver);
    }

    @Test
    public void observeSchoolsListError() {
        LifecycleOwner mockOwner = mock(LifecycleOwner.class);
        Observer<Throwable> mockObserver = mock(Observer.class);

        subject.observeSATSummaryError(mockOwner, mockObserver);

        verify(mockSummaryErrorLiveData).observe(mockOwner, mockObserver);
    }
}