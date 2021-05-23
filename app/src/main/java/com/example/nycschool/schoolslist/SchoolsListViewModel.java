package com.example.nycschool.schoolslist;

import android.net.wifi.aware.PublishConfig;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.nycschool.models.School;
import com.example.nycschool.schoolslist.repository.SchoolsListRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SchoolsListViewModel extends ViewModel {
    /*
        Given more time I'd create a custom view model provider factory to control instantiation,
        and thus dependency injection via constructor. Sticking with member injection for sake
        of time
     */
    public MutableLiveData<List<School>> schoolsLiveData;
    public MutableLiveData<Throwable> schoolsErrorLiveData;
    public SchoolsListRepository repository;

    public SchoolsListViewModel() {
        this.schoolsLiveData = new MutableLiveData<>();
        this.schoolsErrorLiveData = new MutableLiveData<>();
        this.repository = new SchoolsListRepository();
    }

    public void loadSchools() {
        repository.getAllSchools()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new SchoolsListObserver());
    }

    public void observeSchoolsList(@NonNull LifecycleOwner owner, @NonNull Observer<List<School>> observer) {
        schoolsLiveData.observe(owner, observer);
    }

    public void observeSchoolsListError(@NonNull LifecycleOwner owner, @NonNull Observer<Throwable> observer) {
        schoolsErrorLiveData.observe(owner, observer);
    }

    public class SchoolsListObserver extends DisposableSingleObserver<List<School>> {

        @Override
        public void onSuccess(List<School> value) {
            schoolsLiveData.setValue(value);
        }

        @Override
        public void onError(Throwable e) {
            schoolsErrorLiveData.setValue(e);
        }
    }
}