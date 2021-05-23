package com.example.nycschool;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.nycschool.common.ISchedulerProvider;
import com.example.nycschool.models.School;
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
public class SchoolsListViewModelTest {
    private SchoolsListViewModel subject;


    private MutableLiveData<List<School>> mockSchoolsLiveData;
    public MutableLiveData<Throwable> mockSchoolsErrorLiveData;
    public ISchoolsListRepository mockRepository;
    public ISchedulerProvider mockSchedulerProvider;

    @Before
    public void setup() {
        subject = new SchoolsListViewModel();

        mockSchoolsLiveData = mock(MutableLiveData.class);
        mockSchoolsErrorLiveData = mock(MutableLiveData.class);
        mockRepository = mock(ISchoolsListRepository.class);
        mockSchedulerProvider = mock(ISchedulerProvider.class);

        subject.schoolsLiveData = mockSchoolsLiveData;
        subject.schoolsErrorLiveData = mockSchoolsErrorLiveData;
        subject.repository = mockRepository;
        subject.schedulerProvider = mockSchedulerProvider;
    }

    /*
        These unit tests are quite cramped, but only for time's sake
     */
    @Test
    public void loadingSchoolsUpdatesLiveData() {
        Single<List<School>> mockResponse = mock(Single.class);
        Scheduler mockIOScheduler = mock(Scheduler.class);
        Scheduler mockUIScheduler = mock(Scheduler.class);

        when(mockRepository.getAllSchools()).thenReturn(mockResponse);
        when(mockResponse.subscribeOn(any())).thenReturn(mockResponse);
        when(mockResponse.observeOn(any())).thenReturn(mockResponse);
        when(mockSchedulerProvider.io()).thenReturn(mockIOScheduler);
        when(mockSchedulerProvider.ui()).thenReturn(mockUIScheduler);

        subject.loadSchools();

        // Verify that RX uses the proper schedulers
        verify(mockRepository).getAllSchools();
        verify(mockResponse).subscribeOn(mockIOScheduler);
        verify(mockResponse).observeOn(mockUIScheduler);

        // Capture repository observer
        ArgumentCaptor<SchoolsListViewModel.SchoolsListObserver> captor;
        captor = ArgumentCaptor.forClass(SchoolsListViewModel.SchoolsListObserver.class);
        verify(mockResponse).subscribeWith(captor.capture());

        // Verify the repository result is saved and live data updated
        List<School> expectedSchools = new ArrayList<>();
        captor.getValue().onSuccess(expectedSchools);
        verify(mockSchoolsLiveData).setValue(expectedSchools);
        assertEquals(expectedSchools, subject.getSchools());

        // Verify errors are updated
        captor.getValue().onError(null);
        verify(mockSchoolsErrorLiveData).setValue(any());
    }

    @Test
    public void observeSchoolsList() {
        LifecycleOwner mockOwner = mock(LifecycleOwner.class);
        Observer<List<School>> mockObserver = mock(Observer.class);

        subject.observeSchoolsList(mockOwner, mockObserver);

        verify(mockSchoolsLiveData).observe(mockOwner, mockObserver);
    }

    @Test
    public void observeSchoolsListError() {
        LifecycleOwner mockOwner = mock(LifecycleOwner.class);
        Observer<Throwable> mockObserver = mock(Observer.class);

        subject.observeSchoolsListError(mockOwner, mockObserver);

        verify(mockSchoolsErrorLiveData).observe(mockOwner, mockObserver);
    }
}