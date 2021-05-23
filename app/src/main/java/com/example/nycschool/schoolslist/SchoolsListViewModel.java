package com.example.nycschool.schoolslist;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.nycschool.common.BaseViewModel;
import com.example.nycschool.common.ISchedulerProvider;
import com.example.nycschool.common.SchedulerProvider;
import com.example.nycschool.models.School;
import com.example.nycschool.schoolslist.repository.ISchoolsListRepository;
import com.example.nycschool.schoolslist.repository.SchoolsListRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SchoolsListViewModel extends BaseViewModel {
    /*
        Given more time I'd create a custom view model provider factory to control instantiation,
        and thus dependency injection via constructor. Sticking with member injection for sake
        of time
     */
    public MutableLiveData<List<School>> schoolsLiveData;
    public MutableLiveData<Throwable> schoolsErrorLiveData;
    public ISchoolsListRepository repository;
    public ISchedulerProvider schedulerProvider;

    private List<School> schools;

    public SchoolsListViewModel() {
        schoolsLiveData = new MutableLiveData<>();
        schoolsErrorLiveData = new MutableLiveData<>();
        repository = new SchoolsListRepository();
        schedulerProvider = new SchedulerProvider();
    }

    public void loadSchools() {
        repository.getAllSchools()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribeWith(new SchoolsListObserver());
    }

    public List<School> getSchools() {
        return schools;
    }

    public void observeSchoolsList(
        @NonNull LifecycleOwner owner,
        @NonNull Observer<List<School>> observer
    ) {
        schoolsLiveData.observe(owner, observer);
    }

    public void observeSchoolsListError(
        @NonNull LifecycleOwner owner,
        @NonNull Observer<Throwable> observer
    ) {
        schoolsErrorLiveData.observe(owner, observer);
    }

    public class SchoolsListObserver extends DisposableSingleObserver<List<School>> {

        @Override
        public void onSuccess(List<School> value) {
            schools = value;
            schoolsLiveData.setValue(value);
        }

        @Override
        public void onError(Throwable e) {
            schoolsErrorLiveData.setValue(e);
        }
    }
}