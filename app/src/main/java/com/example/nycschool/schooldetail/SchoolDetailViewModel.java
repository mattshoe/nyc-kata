package com.example.nycschool.schooldetail;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.example.nycschool.common.BaseViewModel;
import com.example.nycschool.common.ISchedulerProvider;
import com.example.nycschool.common.NavArguments;
import com.example.nycschool.common.SchedulerProvider;
import com.example.nycschool.models.SATSummary;
import com.example.nycschool.models.School;
import com.example.nycschool.schooldetail.repository.ISATSummaryRepository;
import com.example.nycschool.schooldetail.repository.SATSummaryRepository;
import io.reactivex.observers.DisposableSingleObserver;

public class SchoolDetailViewModel extends BaseViewModel {

    private School school;

    public MutableLiveData<SATSummary> summaryLiveData;
    public MutableLiveData<Throwable> summaryErrorLiveData;
    public ISATSummaryRepository repository;
    public ISchedulerProvider schedulerProvider;

    public SchoolDetailViewModel() {
        summaryLiveData = new MutableLiveData<>();
        summaryErrorLiveData = new MutableLiveData<>();
        repository = new SATSummaryRepository();
        schedulerProvider = new SchedulerProvider();
    }
    
    @Override
    public void initialize(Bundle args) {
        school = args.getParcelable(NavArguments.SCHOOL_DETAIL_BUNDLE_KEY);
    }

    public void loadSATSummary() {
        repository.getSATSummary(school.getId())
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribeWith(new SATSummaryObserver());
    }

    public void observeSATSummary(
        @NonNull LifecycleOwner owner,
        @NonNull Observer<SATSummary> observer
    ) {
        summaryLiveData.observe(owner, observer);
    }

    public void observeSATSummaryError(
        @NonNull LifecycleOwner owner,
        @NonNull Observer<Throwable> observer
    ) {
        summaryErrorLiveData.observe(owner, observer);
    }

    public String getSchoolName() {
        return school.getName();
    }

    public String getSchoolWebsite() {
        return school.getWebsite();
    }

    public String getSchoolDescription() {
        return school.getDescription();
    }

    public String getSchoolPhoneNumber() {
        return school.getPhoneNumber();
    }

    public String getSchoolEmail() {
        return school.getEmail();
    }

    public class SATSummaryObserver extends DisposableSingleObserver<SATSummary> {

        @Override
        public void onSuccess(SATSummary value) {
            summaryLiveData.setValue(value);
        }

        @Override
        public void onError(Throwable e) {
            summaryErrorLiveData.setValue(e);
        }
    }

}